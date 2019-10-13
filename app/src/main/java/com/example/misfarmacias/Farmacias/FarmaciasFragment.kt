package com.example.misfarmacias.Farmacias


import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.misfarmacias.databinding.FragmentFarmaciasListadoBinding
import kotlinx.android.synthetic.main.fragment_farmacias.*
import kotlinx.coroutines.*


class FarmaciasFragment : Fragment(),SearchView.OnQueryTextListener {


    lateinit var viewModel:FarmaciasViewModel
    lateinit var searchView:SearchView
    val  adapter: FarmaciasAdapter=FarmaciasAdapter()


    private var viewModelJob= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)


        val binding:FragmentFarmaciasListadoBinding=FragmentFarmaciasListadoBinding.inflate(inflater,container,false)
        binding.setLifecycleOwner(this)

        // val adapter=FarmaciasAdapter()

        binding.listaFarmaciasTurno.adapter=adapter
        viewModel= ViewModelProviders.of(this).get(FarmaciasViewModel::class.java)

        viewModel.allFarmacias.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
                Log.i("viewModel",it.toString())
            }
        })
        return binding.root
    }







    fun ocultar(){



        this.requireActivity().onBackPressed()

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {



        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate( com.example.misfarmacias.R.menu.menu_farmacias,menu)
           searchView=menu.findItem(com.example.misfarmacias.R.id.app_bar_search)?.actionView as SearchView

//       val cerrar= searchView.findViewById<Button>(androidx.appcompat.R.id.search_close_btn)
//
//        Log.d("cerrar",cerrar.toString())
//               cerrar.setOnClickListener {
//                   Log.d("cerrar","clickkkk")
//               }





           searchView.isSubmitButtonEnabled=true
        searchView.setOnCloseListener(object:OnCloseListener{
            override fun onClose(): Boolean {
                Log.d("cerrar","cerrar")
                return true
            }


        } )


           searchView.setOnQueryTextListener(this)
            searchView.isFocusableInTouchMode=true
        //searchView.clearFocus()

        var searManager:SearchManager=context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        //searchView.setSearchableInfo(searManager.getSearchableInfo(ComponentName)


        hideSoftKeyboard(searchView)


       // view?.isFocusableInTouchMode=true
       // view?.requestFocus()
        searchView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                   Log.d("tecla",keyCode.toString())
                Toast.makeText(activity, "tecla presionada"+event.action.toString(), Toast.LENGTH_SHORT).show()
                if (event.action === KeyEvent.ACTION_DOWN) {

                    Toast.makeText(activity, "tecla presionada"+event.action.toString(), Toast.LENGTH_SHORT).show()

                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Toast.makeText(activity, "Back Pressed", Toast.LENGTH_SHORT).show()
                        ocultar()
                        return true
                    }else {   Toast.makeText(activity, "oculta teclado", Toast.LENGTH_SHORT).show()



                    }
                }
                return false
            }
        })





    }





    fun hideSoftKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String): Boolean {


       // getSugerenciasDb(query)
       // viewModel.getCursor(query)
        viewModel.getAllQuery(query!!)
        viewModel.queryFarmacias.observe(this, Observer {
            it.let {
                adapter.submitList(it)
            }
        })
        return true
    }

    private fun getSugerenciasDb(query: String) {

       var  contexto=context
       // lateinit  var cursor:Cursor
       lateinit var  cursor:Cursor

        uiScope.launch {


            withContext(Dispatchers.IO){
                 cursor=getCursor(query)


            }
            crear(cursor)
            Log.d("query", cursor.count.toString())
            Log.d("query", cursor.count.toString())
            Log.d("query", cursor.count.toString())


            var adapter=SugerenciasAdapter(context,cursor, false)
            Log.d("query",adapter.count.toString())
            Log.d("query", cursor.count.toString())
            searchView.suggestionsAdapter=adapter
            Log.d("queryAdaptador",adapter.count.toString())


            Log.d("queryAdapter",searchView.suggestionsAdapter.toString())
//            withContext(Dispatchers.IO){
//               }


//            searchView.suggestionsAdapter=
//                contexto?.let { SugerenciasAdapter(it,getCursor(query),false) }
               //  getCursor(query)
        }



      //  var adapter=SugerenciasAdapter(context,cursor, false)



       // crear(cursor)

    }



    suspend fun getCursor(query:String):Cursor {



           return viewModel.getCursor(query)


    }

    suspend fun crear(cursor:Cursor){

         searchView.suggestionsAdapter=
             SugerenciasAdapter(context,cursor, false)
         Log.d("queryAdapter",searchView.suggestionsAdapter.count.toString())
     }



        override fun onQueryTextChange(newText: String): Boolean {

//            getSugerenciasDb(newText)
//            //searchView.clearFocus();
//            searchView.requestFocus()

            Log.d("query",newText)
            viewModel.getAllQuery(newText!!)
            viewModel.queryFarmacias.observe(this, Observer {
                it.let {
                    adapter.submitList(it)
                }
            })
            return true
        }


    override fun onStop() {
       lista_farmacias?.adapter=null
        super.onStop()

    }

}
