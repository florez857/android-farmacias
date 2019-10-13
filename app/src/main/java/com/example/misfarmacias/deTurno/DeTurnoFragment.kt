package com.example.misfarmacias.deTurno


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.misfarmacias.databinding.FragmentDeTurnoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DeTurnoFragment : Fragment() {

   lateinit var viewModel:DeTurnoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val binding : FragmentDeTurnoBinding =FragmentDeTurnoBinding.inflate(inflater,container,false)
       //configuro el ciclo de vida quet tendra asociado el binding para observar las variables relacionadas



        var adapterdeTurno=adapterDeTurno()
        binding.listaDeTurno.adapter=adapterdeTurno

        viewModel=ViewModelProviders.of(this).get(DeTurnoViewModel::class.java)

        binding.viewmodel=viewModel
        viewModel.turnoLiveData.observe(this, Observer {

            Log.d("Datos",it.size.toString())
            Log.d("Datos",it.toString())
            it?.let {

                (binding.listaDeTurno.adapter as adapterDeTurno).submitList(it)
                adapterdeTurno.notifyDataSetChanged()
            }

            //adapterdeTurno.notifyDataSetChanged()
        })

        binding.setLifecycleOwner(this)
        return binding.root
    }


    override fun onStop() {
        super.onStop()
       // deturno_frame.visibility=View.INVISIBLE
    }
}
