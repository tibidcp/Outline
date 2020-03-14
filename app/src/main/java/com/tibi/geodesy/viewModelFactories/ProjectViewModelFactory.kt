package com.tibi.geodesy.viewModelFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tibi.geodesy.database.OutlineDao
import com.tibi.geodesy.viewmodels.ProjectViewModel
import java.lang.IllegalArgumentException

class ProjectViewModelFactory(
    private val dataSource: OutlineDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            return ProjectViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}