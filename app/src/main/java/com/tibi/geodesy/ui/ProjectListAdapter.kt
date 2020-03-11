package com.tibi.geodesy.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tibi.geodesy.database.Project
import com.tibi.geodesy.databinding.TextItemViewBinding

class ProjectListAdapter(val clickListener: ProjectListener) : ListAdapter<Project, ProjectListAdapter.ViewHolder>(ProjectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(private val binding: TextItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Project, clickListener: ProjectListener) {
            binding.project = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TextItemViewBinding.inflate(layoutInflater, parent,false)
                return ViewHolder(binding)
            }
        }
    }
}

class ProjectDiffCallback : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem == newItem
    }

}

class ProjectListener(val clickListener: (name: String) -> Unit){
    fun onClick(project: Project) = clickListener(project.name)
}

