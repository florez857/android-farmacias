package com.example.misfarmacias.Ubicacion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.misfarmacias.R

class UbicacionAdapter:ListAdapter<String,UbicacionAdapter.ViewHolder>(UbicacionLisatdoDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var  view = layoutInflater.inflate(R.layout.item_farmacia_turno_ubicacion,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
        //   itemView.item.text=item
//            binding.far=item
//            binding.executePendingBindings()
        }

        }
    }


    class UbicacionLisatdoDiff:DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {

            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
           return oldItem==newItem
        }


    }
