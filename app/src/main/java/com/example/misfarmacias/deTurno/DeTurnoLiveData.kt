package com.example.misfarmacias.deTurno

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.misfarmacias.dataBase.FarmaciasDatas
import com.google.firebase.firestore.*

/**
 *  esta clase me permite obtener una lista observable , dado que extiende de livedata<list<Data>>
 *  internamente obtenemos los datos que se consultan de una base de datos Firestore, la ventaja
 * es que dentro de esta clase se notifica cuando se actualiza la lista de esta clase , lo cual
 * notificara al observador de este dato, otra ventaja es que internamente se usa los metodos
 * active() e inactive() , dentro de estos metos adjuntaremos los oyentes a nuestra base de datos firestore
 * de manera que se escuchen los cambios en la base solo cuando el ciclo de vida este en un estado activo y cuando
 * se destruye el fragment o activity se deja de escuchar los cambios dado que escuchar cambios mientras el ciclo de vida
 * no este activo puede producir perdida de memoria , ademas , para las rotaciones o cambios de configuracion se espera
 *
 * **/

class DeTurnoLiveData : LiveData<List<FarmaciasDatas>>() {


    val db = FirebaseFirestore.getInstance()
    val LOG_TAG = "FirebaseQueryLiveData"
    val listastado: MutableList<FarmaciasDatas>
    val query: Query = db.collection("farmacia_de_turno")
    val listener: MyEventListener = MyEventListener()
    lateinit var registration: ListenerRegistration
    internal var listenerRemovePending = false
    val handler = Handler();

    val removeListener: Runnable = Runnable {
        registration.remove()
        listenerRemovePending = false
    }

    init {
        listastado = mutableListOf()
    }

    override fun onInactive() {
        super.onInactive()
        Log.d(LOG_TAG, "inactivo")

        handler.postDelayed(removeListener, 2000)
        listenerRemovePending = true


    }

    override fun onActive() {
        super.onActive()

        Log.d(LOG_TAG, "activo")
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener)
        } else {
            registration = query.addSnapshotListener(listener)
        }
        listenerRemovePending = false
    }


    inner class MyEventListener : EventListener<QuerySnapshot> {

        override fun onEvent(documentSnapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {

            if (e != null) {
                Log.d(LOG_TAG, e.message)
                return
            }
            listastado.clear()
            Log.d(LOG_TAG, "cantidad de datos em la lista de noticias vieja :" + listastado.size)
            Log.d(
                LOG_TAG,
                "cantidad de documentos cambiados leidos :" + documentSnapshots!!.documentChanges.size.toString()
            )
            /*  for(DocumentChange dc:documentSnapshots.getDocumentChanges()){

                                    switch (dc.getType()){
                                        case ADDED:
                                            Noticia noticia=dc.getDocument().toObject(Noticia.class);
                                            noticia.setId(dc.getDocument().getId());

                                            Listanoticias.add(Listanoticias.size(),noticia);
                                           // Listanoticias.add(noticia);

                                        case REMOVED:


                                        case MODIFIED:

                                    }
                          }*/

            for (dc in documentSnapshots.documents) {
                val farmacia = dc.toObject<FarmaciasDatas>(FarmaciasDatas::class.java!!)
                // farmacia?.key=dc.id
                farmacia?.let { listastado.add(farmacia) }
            }

            // List<Noticia> noticias = documentSnapshots.toObjects(Noticia.class);

            Log.d(LOG_TAG, "Lista noticias fina " + listastado.size)
            postValue(listastado)
            Log.d("live", "Cargando datos.......")

        }
    }
}