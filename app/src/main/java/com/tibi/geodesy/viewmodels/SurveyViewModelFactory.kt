package com.tibi.geodesy.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tibi.geodesy.database.OutlineDao
import java.lang.IllegalArgumentException

class SurveyViewModelFactory (
    private val projectName: String,
    private val dataSource: OutlineDao,
    private val application: Application
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
            return SurveyViewModel(projectName, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}