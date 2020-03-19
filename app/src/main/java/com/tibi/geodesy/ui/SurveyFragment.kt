package com.tibi.geodesy.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_survey, container, false
        )


        val application = requireNotNull(this.activity).application
        val arguments = SurveyFragmentArgs.fromBundle(arguments!!)
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
            it?.let {

            }
        })

        binding.zoomInButton.setOnClickListener {
            binding.myCanvasView.zoomIn()
        }

        binding.zoomOutButton.setOnClickListener {
            binding.myCanvasView.zoomOut()
        }

        mDetector = GestureDetectorCompat(context, this)
        mDetector.setOnDoubleTapListener(this)

        binding.myCanvasView.setOnTouchListener { v, event ->
            binding.myCanvasView.motionTouchEventX = event.x
            binding.myCanvasView.motionTouchEventY = event.y
            mDetector.onTouchEvent(event)
        }

        return binding.root
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

        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        //Log.d(DEBUG_TAG, "onDoubleTapEvent: $event")
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        binding.myCanvasView.changeColor()
        //Log.d(DEBUG_TAG, "onSingleTapConfirmed: $event")
        return true
    }

}