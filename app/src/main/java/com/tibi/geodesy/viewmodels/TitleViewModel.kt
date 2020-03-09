package com.tibi.geodesy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tibi.geodesy.database.OutlineDao

class TitleViewModel(
    val database: OutlineDao,
    application: Application
) : AndroidViewModel(application) {

}