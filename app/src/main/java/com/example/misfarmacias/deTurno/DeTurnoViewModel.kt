package com.example.misfarmacias.deTurno

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeTurnoViewModel:ViewModel() {

    val turnoLiveData:DeTurnoLiveData



    init {
        turnoLiveData=DeTurnoLiveData()
    }
}