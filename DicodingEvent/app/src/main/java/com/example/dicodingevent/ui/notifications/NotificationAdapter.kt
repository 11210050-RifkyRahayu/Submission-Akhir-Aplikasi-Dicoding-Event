package com.example.dicodingevent.ui.notifications

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

class NotificationAdapter: ListAdapter<ListEventsItem, NotificationAdapter.MyViewHolder>(DIFF_CALLBACK){
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
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
        fun bind(event: ListEventsItem){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.MyViewHolder {
        val binding = ItemEventactiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }
}