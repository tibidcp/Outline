package com.tibi.geodesy.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.tibi.geodesy.R
import com.tibi.geodesy.database.Project
import com.tibi.geodesy.database.getDatabase
import com.tibi.geodesy.databinding.FragmentTitleBinding
import com.tibi.geodesy.viewmodels.TitleViewModel
import com.tibi.geodesy.viewmodels.TitleViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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



//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        val db = context?.let { getDatabase(it) }
        val dao = db?.outlineDao
        val scope = CoroutineScope(Dispatchers.IO)
        binding.addButton.setOnClickListener {
            val projectString = binding.projectEditText.text.toString()
            if (projectString.isNotEmpty()) {
                scope.launch{
                    dao?.insertProject(Project(projectString, "Nikon"))
                }
            }
        }
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        val adapter = ProjectListAdapter()
        binding.projectList.adapter = adapter

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
