package com.example.misfarmacias.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


    @Entity(tableName = "farmaciaDataTabla")
    data class FarmaciasDatas constructor(
        @PrimaryKey
        var Key: String,
        val Lat: String,
        val Long: String,
        val Nombre: String,
        val Telefono: String,
        val Direccion: String,
        val Imagen:String,
        val Fecha:String
        )
   {


        constructor():this("","","","","","","","")
    }

