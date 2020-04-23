package com.tibi.geodesy.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import com.tibi.geodesy.R
import com.tibi.geodesy.database.getDatabase
import com.tibi.geodesy.databinding.FragmentSurveyBinding
import com.tibi.geodesy.viewModelFactories.SurveyViewModelFactory
import com.tibi.geodesy.viewmodels.SurveyViewModel

class SurveyFragment : Fragment(),
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var binding: FragmentSurveyBinding

    private lateinit var bt: BluetoothSPP

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_survey, container, false
        )


        val application = requireNotNull(this.activity).application
        bt = BluetoothSPP(application)
        val arguments = SurveyFragmentArgs.fromBundle(requireArguments())
        val dataSource = getDatabase(application, arguments.projectName).outlineDao
        val viewModelFactory =
            SurveyViewModelFactory(
                dataSource,
                application
            )
        val surveyViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(SurveyViewModel::class.java)
        binding.lifecycleOwner = this

        surveyViewModel.addQuickStation()

        surveyViewModel.pointObjectCoordinates.observe(viewLifecycleOwner, Observer {
            it?.let { binding.myCanvasView.updatePoints(it) }
        })

        surveyViewModel.linearObjectCoordinates.observe(viewLifecycleOwner, Observer {
            it?.let { binding.myCanvasView.updateLines(it) }
        })

        binding.zoomInButton.setOnClickListener {
            binding.myCanvasView.zoomIn()
        }

        binding.zoomOutButton.setOnClickListener {
            binding.myCanvasView.zoomOut()
        }

        binding.centerButton.setOnClickListener {
            surveyViewModel.addSomeObjects()

            binding.myCanvasView.initDrawing()
        }

        binding.bluetoothButton.setOnClickListener {
            bluetoothConnection()
        }

        mDetector = GestureDetectorCompat(context, this)
        mDetector.setOnDoubleTapListener(this)

        binding.myCanvasView.setOnTouchListener { _, event ->
            binding.myCanvasView.motionTouchEventX = event.x
            binding.myCanvasView.motionTouchEventY = event.y
            mDetector.onTouchEvent(event)
        }

        return binding.root
    }

    private fun bluetoothConnection() {
        if (!bt.isBluetoothAvailable) {
            Toast.makeText(context, "Bluetooth is not available", Toast.LENGTH_SHORT).show()
            return
        }
        if (!bt.isBluetoothEnabled) {
            Toast.makeText(context, "Bluetooth is not enabled", Toast.LENGTH_SHORT).show()
            return
        }
        bt.setupService()
        bt.startService(BluetoothState.DEVICE_OTHER)

        val intent = Intent(context, DeviceList::class.java)
        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
        bt.setOnDataReceivedListener { data, message ->
            Log.i("BluetoothTest", "data: $data; message: $message")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                bt.connect(data);
                Toast.makeText(context, "Device connected", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bt.stopService()
    }

    override fun onDown(event: MotionEvent): Boolean {
        binding.myCanvasView.touchStart()
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        //Log.d(DEBUG_TAG, "onFling: $event1 $event2")
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        //Log.d(DEBUG_TAG, "onLongPress: $event")
    }

    override fun onScroll(
        event1: MotionEvent,
        event2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {

        binding.myCanvasView.touchMove()

        return true
    }

    override fun onShowPress(event: MotionEvent) {
        //Log.d(DEBUG_TAG, "onShowPress: $event")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
//        Log.d(DEBUG_TAG, "onSingleTapUp: $event")
        return true
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
//        Log.d(DEBUG_TAG, "onDoubleTap: $event")
        binding.myCanvasView.zoomIn()
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        //Log.d(DEBUG_TAG, "onDoubleTapEvent: $event")
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        binding.myCanvasView.select()
        //Log.d(DEBUG_TAG, "onSingleTapConfirmed: $event")
        return true
    }



}