package com.example.misfarmacias.deTurno

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.misfarmacias.dataBase.FarmaciasDatas
import com.example.misfarmacias.databinding.ItemLayoutDeturnoBinding

class adapterDeTurno : ListAdapter<FarmaciasDatas, adapterDeTurno.ViewHolder>(FarmaciDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return  ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        Log.d("viewModeladapter",item.toString())
        holder.bind(item)
    }


    class ViewHolder private constructor(val binding: ItemLayoutDeturnoBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: FarmaciasDatas) {
            binding.turno=item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLayoutDeturnoBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class FarmaciDiffCallback : DiffUtil.ItemCallback<FarmaciasDatas>() {

        override fun areItemsTheSame(oldItem: FarmaciasDatas, newItem: FarmaciasDatas): Boolean {
            Log.d("viewModelitemsame",oldItem.toString())
            Log.d("viewModelitemsame",newItem.toString())
            return oldItem.Key== newItem.Key
        }


        override fun areContentsTheSame(oldItem: FarmaciasDatas, newItem: FarmaciasDatas): Boolean {
            Log.d("viewModelcontent",oldItem.toString())
            Log.d("viewModelcontent",newItem.toString())
            return oldItem == newItem
        }


    }

}