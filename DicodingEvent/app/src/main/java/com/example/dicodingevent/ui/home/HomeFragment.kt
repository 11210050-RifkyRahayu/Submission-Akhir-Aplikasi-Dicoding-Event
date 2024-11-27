package com.example.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentHomeBinding
import com.example.dicodingevent.ui.setting.SettingPreferences
import com.example.dicodingevent.ui.setting.SettingViewModel
import com.example.dicodingevent.ui.setting.ViewModelFactory
import com.example.dicodingevent.ui.setting.dataStore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    // Access to the binding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Setting up preferences and ViewModel for theme settings
        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        // Observe theme setting
        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            settingViewModel.saveThemeSetting(isChecked)
        }

        // Inflate the layout
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up ViewModel and Adapter for the active events
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        adapter = HomeAdapter { event ->
            val eventId = event.id // Placeholder for event selection
            // You can use the eventId for navigation or further actions
        }

        // Set up RecyclerView
        binding.rvActive.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Observe events and loading state
        homeViewModel.event.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        // Load active events
        homeViewModel.loadActiveEvents()

        return root
    }

    // Show or hide loading indicator based on the isLoading state
    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Nullify the binding to avoid memory leaks
    }
}
