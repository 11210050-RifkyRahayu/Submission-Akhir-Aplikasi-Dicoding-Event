package com.example.dicodingevent.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.R
import com.example.dicodingevent.database.FavoriteEvent

class FavoriteAdapter(
    private val onClick: (FavoriteEvent) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val favorites = mutableListOf<FavoriteEvent>()

    fun setData(newFavorites: List<FavoriteEvent>) {
        favorites.clear()
        favorites.addAll(newFavorites)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_eventactive, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position], onClick)
    }

    override fun getItemCount() = favorites.size

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.name_event_active)
        private val image: ImageView = itemView.findViewById(R.id.img_event_active)

        fun bind(event: FavoriteEvent, onClick: (FavoriteEvent) -> Unit) {
            title.text = event.name
            Glide.with(itemView.context).load(event.mediaCover).into(image)
            itemView.setOnClickListener { onClick(event) }
        }
    }
}
