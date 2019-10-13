package com.example.misfarmacias.Ubicacion

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.misfarmacias.R
import com.example.misfarmacias.Ubicacion.CustomSuggestionsAdapter.ListenerAdapterSuggestion
import com.example.misfarmacias.dataBase.FarmaciasDatas
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.MaterialSearchBar.OnSearchActionListener
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import kotlinx.android.synthetic.main.fragment_ubicacion.view.*
import kotlinx.android.synthetic.main.window_snippet.view.*


/**
 * A simple [Fragment] subclass.
 *
 */
class UbicacionFragment : Fragment(),OnMapReadyCallback, OnSearchActionListener,
    ListenerAdapterSuggestion {

    override fun onclickItem(position: Int) {
        super.onclickItem(position)

      //  searchBar.clearFocus()
        searchBar.disableSearch()

        Log.d("click",position.toString())
        posicionar(position)


    }

    private fun posicionar(position: Int) {

        var miLat = LatLng(listaFarmacias.get(position).Lat.toDouble(), listaFarmacias.get(position).Long.toDouble())

        var marcador: Marker = mMap!!.addMarker(MarkerOptions().position(miLat))

        var info: InfoData = InfoData(listaFarmacias.get(position).Nombre, listaFarmacias.get(position).Direccion,listaFarmacias.get(position).Telefono)
        marcador.tag = info

        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(miLat,16f),2000,null)
        marcador.showInfoWindow()
       // searchBar.disableSearch()

    }

    override fun onButtonClicked(buttonCode: Int) {

         Log.d("boton", "navegacion")

        when(buttonCode) {
            MaterialSearchBar.BUTTON_NAVIGATION -> Log.d("boton", "navegacion")

            MaterialSearchBar.BUTTON_BACK -> searchBar.clearFocus()
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        Log.d("boton search state", enabled.toString())
    }

    override fun onSearchConfirmed(text: CharSequence?) {

        searchBar.disableSearch()
        //adapterSuggestion.suggestions=listaFarmacias

        Log.d("boton search confirmed", text.toString())


        Log.d("boton", text.toString())
        if(text.toString()==""){
            searchBar.requestFocus()
            adapterSuggestion.suggestions=listaFarmacias
        }
    }


//    override fun onSearchAction(currentQuery: String?) {
//
//
//        if (currentQuery != "") {
//            //listaFiltro=viewModel.allFarmacias.value?.filter{ it.Direccion==currentQuery}
//            Log.d("listadoCadena", currentQuery)
//            Log.d("listadoFarmacias", listaFarmacias.toString())
//            var listaFiltrada: List<String> = listaFarmacias.filter { it == currentQuery }
//            Log.d("listadoFiltro", listaFiltrada.toString())
//            if (listaFiltrada.size != 0) {
//                ubicacionAdapter.submitList(listaFiltrada)
//                drawe.openDrawer(GravityCompat.START)
//            } else {
//
//                Toast.makeText(this.context, "error lista vacia", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


    private lateinit var viewModel: UbicacionViewModel
    val REQUEST_LOCATION_PERMISSION: Int = 1
    var permisoUbicacion: Boolean = false
    lateinit var drawe: DrawerLayout
    val ubicacionAdapter: UbicacionAdapter = UbicacionAdapter()
    lateinit var listaFarmacias: MutableList<FarmaciasDatas>
    lateinit var recyclerView: RecyclerView
    lateinit var fuseLocationClient: FusedLocationProviderClient
    lateinit var miUbicacion: Location
    lateinit var searchBar: MaterialSearchBar
    lateinit var inflater: LayoutInflater
    lateinit var adapterSuggestion: SuggestionsAdapter<FarmaciasDatas, CustomSuggestionsAdapter.SuggestionHolder>
    lateinit var mapFragment: SupportMapFragment
    var mMap: GoogleMap? = null
    val TAG="search bar"


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        inflater=context?.getSystemService(Service.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        adapterSuggestion=CustomSuggestionsAdapter(inflater,this)
        viewModel = ViewModelProviders.of(this).get(UbicacionViewModel::class.java)
        Log.d("Estado de vida","Create")
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("Estado de vida","onCreateView")

        (activity as AppCompatActivity).supportActionBar?.hide()

        var view = inflater.inflate(R.layout.fragment_ubicacion, container, false)
        enableMyLocation()
           val listener:OnMapReadyCallback=this;

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        object: Handler(){}.postDelayed(object:Runnable{
            override fun run() {

                mapFragment.getMapAsync(listener)
            }
        },500)


        searchBar = view.searchBar
        searchBar.visibility=View.INVISIBLE
        searchBar.setOnSearchActionListener(this)
        searchBar.findViewById<ImageView>(R.id.mt_clear)
            .setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
               Log.d("boton","clear")
                //searchBar.clearFocus()
                searchBar.disableSearch()

            }


        })
        searchBar.addTextChangeListener(object: TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

                Log.d(TAG,"busqueda after ")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d(TAG, "busqueda before")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapterSuggestion.filter.filter(searchBar.text)
                Log.d(TAG,p0.toString())
            }
        })



//      searchBar.setOnClickListener(fun(it: View) {
//          Log.d("boton",it.findViewById<>())
//
//          when (it.id){
////
////              MaterialSearchBar.BUTTON_NAVIGATION -> Log.d("boton","navegacion")
////
////              MaterialSearchBar.BUTTON_BACK -> Log.d("boton","back")
//
//          }
//
//      })
        return view
    }





    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("Estado de vida","onMapReady")

        mMap = googleMap
        var posi: LatLng = LatLng(0.0, 0.0)
        lateinit var marker:Marker
        // enableMyLocation()

        /**si tengo los permisos de ubicacion habilito el boton de posicion */
        confUiMap()

        mMap!!.setInfoWindowAdapter(CustoInfo(this.layoutInflater))
        /** coloco un observador sobre el listado de farmacias , de manera que cuando este listo para cargar se cargan los datos en el mapa */
        viewModel.allFarmacias.observe(this, Observer { list ->

            Log.d("datos", list.toString())

            listaFarmacias = mutableListOf<FarmaciasDatas>()


            /** recorro la lista con las cordenadas para cargar los markers en el mapa
             * creo el objeto info que se mostrara en la ventana info
             */
            for (farmaci in list) {

                listaFarmacias.add(farmaci)
                //cargamos los datos para la ventana de informacion
                val info: InfoData = InfoData(farmaci.Nombre, farmaci.Direccion, farmaci.Telefono)
                //cargamos la longitud y latitud
                 posi = LatLng(farmaci.Lat.toDouble(), farmaci.Long.toDouble())

                 marker=mMap!!.addMarker(MarkerOptions().position(posi))
                 marker.tag = info
                Log.d("info", info.toString())
               // marker.showInfoWindow()
            }

            /**cargo los datos en la lista de sugerencias*/
            adapterSuggestion.suggestions=listaFarmacias
            searchBar.setCustomSuggestionAdapter(adapterSuggestion)
            /**------------------------------------------*/

            /**configuro el cliente para escuchar los cambios de mi posicion */
            fuseLocationClient =
                LocationServices.getFusedLocationProviderClient(this.requireContext())

            /** solicito mi ocalizacion , cuando obtenga mim posicion , podre calcular la farmacia mas cercana y ademas
             * podre colocar mi posicion en el mapa , en caso de que no pueda obtener mi posicion directamente
             * dibujo el mapa con los puntos antes cargados */
            fuseLocationClient.lastLocation.addOnSuccessListener(this.requireActivity()) { location ->


                var posicion1: Location = Location("miPosicion")
                var posicion2: Location = Location("farmaciaPosicion")
                var distancia: Float = 0.0f
                var distancia2=0.0f

                if (location != null) {
                    var info2:InfoData?=null

                    miUbicacion = location
                    val miLatLon = LatLng(miUbicacion.latitude, miUbicacion.longitude)
                    Log.d("posicion", miLatLon.toString())

                    distancia = obtenerDistanciInicial(list, posicion2, distancia)

                    for (farmaci in list) {
                        //cargamos los datos para la ventana de informacion
                        posi = LatLng(farmaci.Lat.toDouble(), farmaci.Long.toDouble())
                        val info: InfoData = InfoData(farmaci.Nombre, farmaci.Direccion, farmaci.Telefono)

                        posicion1.latitude = posi.latitude
                        posicion1.longitude = posi.longitude

                        distancia2 = posicion1.distanceTo(miUbicacion)
                        Log.d("distancia2", distancia2.toString())
                        Log.d("distancia", distancia.toString())

                        if (distancia > distancia2) {
                            distancia = distancia2
                            info2 = info
                            posicion2.longitude = posicion1.longitude
                            posicion2.latitude = posicion1.latitude
                            Log.d("distancia", distancia.toString())

                }



                        // marker.showInfoWindow()
                    }
                    Log.d("distancia" ,distancia.toString())

                    var LatL=LatLng(posicion2.latitude,posicion2.longitude)

                    var info: InfoData = InfoData("Mi Ubicación", " ", " ")
                    var marc=  mMap!!.addMarker(MarkerOptions().position(LatL))
                    marc.tag=info2
                    marc.showInfoWindow()
                    mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatL, 16f))

                }else{

                    mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(posi, 15.0f))
                    marker.showInfoWindow()
                    Log.d("posicion","nullo")

                }
            }


            searchBar.visibility=View.VISIBLE

        })
    }

    private fun obtenerDistanciInicial(
        list: List<FarmaciasDatas>,
        posicion2: Location,
        distancia: Float
    ): Float {
        var distancia1 = distancia
        list.get(0).Long
        posicion2.latitude = list.get(0).Lat.toFloat().toDouble()
        posicion2.longitude = list.get(0).Long.toFloat().toDouble()

        distancia1 = posicion2.distanceTo(miUbicacion)
        return distancia1
    }

    private fun confUiMap() {
        if (permisoUbicacion) {
            mMap!!.uiSettings.isMyLocationButtonEnabled = true
            mMap!!.isMyLocationEnabled = true
            mMap!!.setPadding(0,135,0,0)
        }
        // val layer = KmlLayer(mMap, R.raw.farmacias, context)
        mMap!!.uiSettings.isZoomControlsEnabled = true
        mMap!!.uiSettings.isMapToolbarEnabled = true
    }

    override fun onResume() {
        mapFragment.onResume()
        Log.d("Estado de vida","onResume")
        super.onResume()
    }


    override fun onDestroy() {
        mapFragment.onDestroy()
        super.onDestroy()
        Log.d("Estado de vida","onDestroy")
        (activity as AppCompatActivity).supportActionBar?.show()


    }


    override fun onLowMemory() {
        mapFragment.onLowMemory()
        Log.d("Estado de vida","onLow")
        super.onLowMemory()
    }

    override fun onStop() {
        mapFragment.onStop()
        searchBar.clearFocus()
        searchBar.visibility=View.INVISIBLE
        searchBar.disableSearch()
        searchBar.clearSuggestions()
        super.onStop()



        Log.d("Estado de vida","onStop")
    }


    override fun onPause() {
        mapFragment.onDestroyView()
        mapFragment.onPause()


        Log.d("Estado de vida","onPause")
        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {

            REQUEST_LOCATION_PERMISSION -> if (grantResults.size > 0 && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            }

        }
    }

    fun enableMyLocation() {
        if (this.context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED) {
            //mMap.uiSettings.isMyLocationButtonEnabled=true
            //mMap.isMyLocationEnabled=true
            // mMap.clear()

            permisoUbicacion = true

        } else {
            ActivityCompat.requestPermissions(
                this.activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
            )
        }


    }
}


class CustoInfo(val inflater: LayoutInflater) : GoogleMap.InfoWindowAdapter {


    override fun getInfoContents(p0: Marker?): View {


        var InfoVista = inflater.inflate(R.layout.window_snippet, null)
        var InfoWindow: InfoData? = p0?.tag as InfoData?

        InfoVista.nombre.text = InfoWindow?.Nombre
        InfoVista.direccion.text = InfoWindow?.Direccion
        InfoVista.telefono.text = InfoWindow?.Telefono

        return InfoVista
    }


    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}


data class InfoData(
    val Nombre: String,
    val Direccion: String,
    val Telefono: String
)
