package com.tibi.geodesy.ui

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tibi.geodesy.R
import com.tibi.geodesy.database.getDatabase
import com.tibi.geodesy.databinding.FragmentSurveyBinding
import com.tibi.geodesy.viewmodels.SurveyViewModel
import com.tibi.geodesy.viewModelFactories.SurveyViewModelFactory
import kotlin.math.round

class SurveyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSurveyBinding = DataBindingUtil.inflate(
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

        binding.zoomInButton.setOnClickListener {
            binding.myCanvasView.zoomIn()
        }

        binding.zoomOutButton.setOnClickListener {
            binding.myCanvasView.zoomOut()
        }

        return binding.root
    }
}