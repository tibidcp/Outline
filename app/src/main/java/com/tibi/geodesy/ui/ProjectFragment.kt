package com.tibi.geodesy.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.tibi.geodesy.R
import com.tibi.geodesy.database.getDatabase
import com.tibi.geodesy.databinding.FragmentProjectBinding
import com.tibi.geodesy.utils.ProjectListAdapter
import com.tibi.geodesy.utils.ProjectListener
import com.tibi.geodesy.viewmodels.ProjectViewModel
import com.tibi.geodesy.viewModelFactories.ProjectViewModelFactory

class ProjectFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentProjectBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_project, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).outlineDao
        val projectViewModelFactory =
            ProjectViewModelFactory(
                dataSource,
                application
            )
        val projectViewModel =
            ViewModelProvider(this, projectViewModelFactory).get(ProjectViewModel::class.java)
        binding.projectViewModel = projectViewModel
        binding.lifecycleOwner = this

        val adapter =
            ProjectListAdapter(ProjectListener {
                projectViewModel.onProjectClicked(it)
            })
        binding.projectList.adapter = adapter
        projectViewModel.navigateToSurvey.observe(viewLifecycleOwner, Observer { projectName ->
            projectName?.let {
                this.findNavController().navigate(
                    ProjectFragmentDirections
                        .actionProjectFragmentToSurveyFragment(projectName))
                projectViewModel.onSurveyNavigated()
            }
        })

        projectViewModel.projects.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.reversed())
            }
        })

        projectViewModel.showProjectSnackbar.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.project_exists),
                    Snackbar.LENGTH_SHORT
                ).show()
                projectViewModel.doneShowProjectSnackbar()
            }
        })

        binding.addButton.setOnClickListener {
            val projectString = binding.projectEditText.text.toString()
            projectViewModel.addNewProject(projectString)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }
}
