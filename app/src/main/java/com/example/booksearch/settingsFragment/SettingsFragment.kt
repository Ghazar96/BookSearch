package com.example.booksearch.settingsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearch.R
import com.example.booksearch.SharedViewModel
import com.example.booksearch.isDefault
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel


class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by stateViewModel()

    private val sharedViewModel by sharedViewModel<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolBar = view.findViewById<Toolbar>(R.id.toolBar)
        toolBar.setNavigationIcon(R.drawable.ic_back_arrow)
        toolBar.setNavigationOnClickListener {
            sharedViewModel.showOrHideBadge(false)
            sharedViewModel.backToMainPage()
        }

        val list = viewModel.getSettingsList()
        val listView = view.findViewById<RecyclerView>(R.id.listView)
        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        listView?.layoutManager = llm
        listView.adapter = SettingsAdapter(list.settings) {
            viewModel.updateSelectedSettings(it)
            (listView?.adapter as? SettingsAdapter)?.updateSelectedItem(it)
            sharedViewModel.showOrHideBadge(!it.type.isDefault())
            sharedViewModel.backToMainPage()
        }
    }
}