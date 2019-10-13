package com.example.misfarmacias.Farmacias

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.misfarmacias.dataBase.FarmaciasDatas
import com.example.misfarmacias.databinding.ItemFarmaciaTurnoBinding

class FarmaciasAdapter:ListAdapter<FarmaciasDatas,FarmaciasAdapter.ViewHolder>(FarmaciasDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

      return  ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        Log.d("viewModel",item.toString())
          holder.bind(item)
    }


    class ViewHolder private constructor(val binding: ItemFarmaciaTurnoBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item:FarmaciasDatas) {
        binding.far=item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemFarmaciaTurnoBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

class FarmaciasDiffCallback : DiffUtil.ItemCallback<FarmaciasDatas>() {

    override fun areItemsTheSame(oldItem: FarmaciasDatas, newItem: FarmaciasDatas): Boolean {
        Log.d("viewModel",oldItem.toString())
        Log.d("viewModelitemsameold",oldItem.toString())
        Log.d("viewModelitemsamenew",newItem.toString())
        return oldItem.Key== newItem.Key
    }


    override fun areContentsTheSame(oldItem: FarmaciasDatas, newItem: FarmaciasDatas): Boolean {
        Log.d("viewModel",oldItem.toString())
        Log.d("viewModelitemold",oldItem.toString())
        Log.d("viewModelitemsamenew",newItem.toString())
        return oldItem == newItem
    }


}

}