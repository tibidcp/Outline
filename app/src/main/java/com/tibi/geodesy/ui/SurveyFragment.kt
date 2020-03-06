package com.tibi.geodesy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tibi.geodesy.R
import com.tibi.geodesy.databinding.FragmentSurveyBinding

class SurveyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSurveyBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_survey, container, false
        )
        return binding.root
    }
}