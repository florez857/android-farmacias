package com.example.misfarmacias.Farmacias

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cursoradapter.widget.CursorAdapter
import com.example.misfarmacias.R

class SugerenciasAdapter(context: Context?, cursor:Cursor, bandera:Boolean ): CursorAdapter(context,cursor,bandera ) {


    val layoutInflater:LayoutInflater= LayoutInflater.from(context)

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
    val view=layoutInflater.inflate(R.layout.item_farmacia_turno,parent,false)
        return view
    }


    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {


       val nombre:String? =cursor?.getString(cursor.getColumnIndexOrThrow("Nombre"))
       val direccion: String? =cursor?.getString(cursor.getColumnIndexOrThrow("Direccion"))
       val telefono:String? = cursor?.getString(cursor.getColumnIndexOrThrow("Telefono"))

        view?.findViewById<TextView>(R.id.nombre_farmacia)?.text=nombre
        view?.findViewById<TextView>(R.id.direccion_farmacia)?.text=direccion
        view?.findViewById<TextView>(R.id.telefono_farmacia)?.text=telefono

        view?.setOnClickListener(View.OnClickListener {
           Log.d("id",it.findViewById<TextView>(R.id.nombre_farmacia).text.toString())})

    }
}