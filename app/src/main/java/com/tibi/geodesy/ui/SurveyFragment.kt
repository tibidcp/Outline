package com.tibi.geodesy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tibi.geodesy.R
import com.tibi.geodesy.database.getDatabase
import com.tibi.geodesy.databinding.FragmentSurveyBinding
import com.tibi.geodesy.viewmodels.SurveyViewModel
import com.tibi.geodesy.viewmodels.SurveyViewModelFactory

class SurveyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSurveyBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_survey, container, false
        )
        val application = requireNotNull(this.activity).application
        val arguments = SurveyFragmentArgs.fromBundle(arguments!!)
        val dataSource = getDatabase(application).outlineDao
        val viewModelFactory =
            SurveyViewModelFactory(arguments.projectName, dataSource, application)
        val surveyViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(SurveyViewModel::class.java)
        binding.lifecycleOwner = this


        return binding.root
    }
}