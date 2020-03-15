package com.tibi.geodesy.viewModelFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tibi.geodesy.database.OutlineDao
import com.tibi.geodesy.viewmodels.SurveyViewModel
import java.lang.IllegalArgumentException

class SurveyViewModelFactory (
    private val dataSource: OutlineDao,
    private val application: Application
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
            return SurveyViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}