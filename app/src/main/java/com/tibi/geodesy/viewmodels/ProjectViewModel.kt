package com.tibi.geodesy.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tibi.geodesy.database.OutlineDao
import com.tibi.geodesy.database.Project
import com.tibi.geodesy.database.ProjectDao
import kotlinx.coroutines.*

class ProjectViewModel(
    val database: ProjectDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val projects = database.getAllProjects()

    private var _showProjectSnackbar = MutableLiveData<Boolean>()
    val showProjectSnackbar: LiveData<Boolean>
        get() = _showProjectSnackbar

    fun doneShowProjectSnackbar() {
        _showProjectSnackbar.value = false
    }

    fun addNewProject(name: String) {
        uiScope.launch {
            _showProjectSnackbar.value = checkProjectExists(name)
            if (name.isNotEmpty()) {
                val newProject = Project(name)
                insertNewProject(newProject)
            }
        }
    }

    private suspend fun checkProjectExists(name: String) =
        withContext(Dispatchers.IO){
            return@withContext database.getProject(name) != null
        }

    private suspend fun insertNewProject(project: Project) {
        withContext(Dispatchers.IO){
            database.insertProject(project)
        }
    }

    private val _navigateToSurvey = MutableLiveData<String>()
    val navigateToSurvey
        get() = _navigateToSurvey

    fun onProjectClicked(name: String) {
        _navigateToSurvey.value = name
    }

    fun onSurveyNavigated() {
        _navigateToSurvey.value = null
    }
}