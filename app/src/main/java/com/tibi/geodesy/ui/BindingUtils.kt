package com.tibi.geodesy.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tibi.geodesy.database.Project

@BindingAdapter("projectName")
fun TextView.setProjectName(item: Project?) {
    item?.let {
        text = item.name
    }
}