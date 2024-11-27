package com.example.dicodingevent.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.DetailActivity
import com.example.dicodingevent.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup ViewModel
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        // Setup RecyclerView and Adapter
        adapter = FavoriteAdapter { event ->
            // Navigate to DetailActivity when item is clicked
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EXTRA_EVENT_ID", event.id)
            startActivity(intent)
        }

        binding.rvActive.layoutManager = LinearLayoutManager(requireContext())
        binding.rvActive.adapter = adapter

        // Observe favorite data
        favoriteViewModel.allFavorites.observe(viewLifecycleOwner) { favorites ->
            adapter.setData(favorites)
        }
    }
}
