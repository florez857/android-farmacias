package com.example.misfarmacias.Farmacias

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.misfarmacias.dataBase.FarmaciaDatabase
import com.example.misfarmacias.dataBase.FarmaciasDatas
import kotlinx.coroutines.*

class FarmaciasViewModel(application: Application):AndroidViewModel(application) {

    private var viewModelJob= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)
    lateinit var allFarmacias: LiveData<List<FarmaciasDatas>>
    lateinit var queryFarmacias:LiveData<List<FarmaciasDatas>>
    val farmaciaDao= FarmaciaDatabase.getDatabase(application,uiScope).farmaciaDao()


    init {

        allFarmacias=farmaciaDao.getFarmacias()
        Log.d("viewModel",allFarmacias.toString())
        uiScope.launch {
            withContext(Dispatchers.IO){

            }

        }

    }

    fun getAllQuery(query:String){
            queryFarmacias= farmaciaDao.getAllquery(query)

    }


    fun getCursor(query:String): Cursor {
        return farmaciaDao.getAllqueryCursor(query)

    }

}