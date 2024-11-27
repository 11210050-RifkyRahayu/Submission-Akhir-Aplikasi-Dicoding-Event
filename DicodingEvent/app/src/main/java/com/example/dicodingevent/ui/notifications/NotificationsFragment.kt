package com.example.dicodingevent.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var Not: NotificationsViewModel
    private val binding get() = _binding!!

    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Not = ViewModelProvider(requireActivity()).get(NotificationsViewModel::class.java)
        adapter = NotificationAdapter()
        binding.rvActivee.adapter = adapter
        binding.rvActivee.layoutManager = LinearLayoutManager(requireContext())

        Not.event.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        Not.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        Not.loadActiveEvents()
        return root
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressbarr.visibility=if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}