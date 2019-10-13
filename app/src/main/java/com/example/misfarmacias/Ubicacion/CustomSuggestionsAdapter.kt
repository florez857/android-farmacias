package com.example.misfarmacias.Ubicacion


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.misfarmacias.R
import com.example.misfarmacias.dataBase.FarmaciasDatas
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import kotlinx.android.synthetic.main.item_farmacia_turno.view.*


class CustomSuggestionsAdapter(inflater: LayoutInflater,var listener:ListenerAdapterSuggestion):SuggestionsAdapter<FarmaciasDatas,CustomSuggestionsAdapter.SuggestionHolder>(inflater) {

    interface ListenerAdapterSuggestion{

        fun onclickItem(position: Int){}
    }



    override fun getSingleViewHeight(): Int {
        return 80
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_farmacia_turno_ubicacion, parent, false)
        return SuggestionHolder(view, this.listener)
    }

    override fun onBindSuggestionHolder(suggestion:FarmaciasDatas, holder: SuggestionHolder, position: Int) {


        holder.bind(suggestion)
    }


    override fun getFilter(): Filter {
        return object : Filter() {
             override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                val term = constraint.toString()
                if (term.isEmpty())
                    suggestions = suggestions_clone
                else {
                    suggestions = ArrayList()
                    for (item in suggestions_clone)
                        if (item.Nombre.toLowerCase().contains(term.toLowerCase()))
                            suggestions.add(item)
                }
                results.values = suggestions
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                suggestions = results.values as MutableList<FarmaciasDatas>
                notifyDataSetChanged()
            }
        }
    }

    class SuggestionHolder(itemView: View,listener:ListenerAdapterSuggestion) : RecyclerView.ViewHolder(itemView),View.OnClickListener {




        var nombre: TextView=itemView.nombre_farmacia
        var direccion: TextView=itemView.direccion_farmacia
        val listener=listener

init {

    itemView.setOnClickListener (this)
        Log.d("dato","clik view")

}
        override fun onClick(p0: View?) {

            listener.onclickItem(adapterPosition)
        }

        fun bind(item:FarmaciasDatas){
            nombre.text=item.Nombre
            direccion.text=item.Direccion

        }


    }

}