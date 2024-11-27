package com.example.dicodingevent.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.DetailActivity
import com.example.dicodingevent.data.ListEventsItem
import com.example.dicodingevent.databinding.ItemEventactiveBinding

class HomeAdapter(private val onItemClickListener: (ListEventsItem) -> Unit) :
    ListAdapter<ListEventsItem, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MyViewHolder(val binding: ItemEventactiveBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem, onItemClickListener: (ListEventsItem) -> Unit) {
            binding.nameEventActive.text = event.name
            Glide.with(binding.imgEventActive.context)
                .load(event.imageLogo)
                .into(binding.imgEventActive)

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("EXTRA_EVENT_ID", event.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventactiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onItemClickListener)
    }
}
