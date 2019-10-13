package com.example.misfarmacias.Ubicacion

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.misfarmacias.dataBase.FarmaciaDatabase
import com.example.misfarmacias.dataBase.FarmaciasDatas
import kotlinx.coroutines.*

class UbicacionViewModel(application:Application): AndroidViewModel(application) {

    private var viewModelJob= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)
    lateinit var allFarmacias:LiveData<List<FarmaciasDatas>>


    init {
        Log.d("Estado de vida","onViewModel")
        val farmaciaDao=FarmaciaDatabase.getDatabase(application,uiScope).farmaciaDao()
        allFarmacias=farmaciaDao.getFarmacias()
        uiScope.launch {
            withContext(Dispatchers.IO){
            }

        }

    }

}