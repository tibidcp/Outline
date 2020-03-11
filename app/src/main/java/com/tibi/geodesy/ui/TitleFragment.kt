package com.tibi.geodesy.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
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
import com.tibi.geodesy.databinding.FragmentTitleBinding
import com.tibi.geodesy.viewmodels.TitleViewModel
import com.tibi.geodesy.viewmodels.TitleViewModelFactory

class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = getDatabase(application).outlineDao
        val titleViewModelFactory = TitleViewModelFactory(dataSource, application)
        val titleViewModel =
            ViewModelProvider(this, titleViewModelFactory).get(TitleViewModel::class.java)
        binding.titleViewModel = titleViewModel
        binding.lifecycleOwner = this

        val adapter = ProjectListAdapter(ProjectListener {
            titleViewModel.onProjectClicked(it)
        })
        binding.projectList.adapter = adapter
        titleViewModel.navigateToSurvey.observe(viewLifecycleOwner, Observer { projectName ->
            projectName?.let {
                this.findNavController().navigate(
                    TitleFragmentDirections
                        .actionTitleFragmentToSurveyFragment(projectName))
                titleViewModel.onSurveyNavigated()
            }
        })

        titleViewModel.projects.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.reversed())
            }
        })

        titleViewModel.showProjectSnackbar.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.project_exists),
                    Snackbar.LENGTH_SHORT
                ).show()
                titleViewModel.doneShowProjectSnackbar()
            }
        })

        binding.addButton.setOnClickListener {
            val projectString = binding.projectEditText.text.toString()
            titleViewModel.addNewProject(projectString)
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
