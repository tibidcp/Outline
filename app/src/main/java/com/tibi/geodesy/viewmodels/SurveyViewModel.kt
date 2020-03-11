package com.tibi.geodesy.viewmodels

import androidx.lifecycle.ViewModel
import com.tibi.geodesy.database.OutlineDao
import kotlinx.coroutines.Job

class SurveyViewModel(
    private val projectName: String,
    dataSource: OutlineDao) : ViewModel() {

}