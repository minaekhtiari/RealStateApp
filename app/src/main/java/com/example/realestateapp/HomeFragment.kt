package com.example.realestateapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.realestateapp.adapter.HouseAdapter
import com.example.realestateapp.cons.Cons.PREFERENCE_L0NG
import com.example.realestateapp.cons.Cons.PREFERENCE_LAT
import com.example.realestateapp.databinding.FragmentHomeBinding
import com.example.realestateapp.viewModel.MainViewModel


class HomeFragment : Fragment() {

    private val sharedLat = PREFERENCE_LAT
    private val sharedLong = PREFERENCE_L0NG
    private val sharedPreferences by lazy {
        requireActivity().applicationContext
            .getSharedPreferences(
                "_sharedPreferences",
                Context.MODE_PRIVATE
            )
    }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: HouseAdapter
   // private latitude var dataStore: DataStore<Preferences>
    private var lat: Int? = null
    private var lon: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       // return inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.recyclerViewHouses.setHasFixedSize(true)
        binding.recyclerViewHouses.layoutManager = LinearLayoutManager(activity)


        //  dataStore = requireActivity().createDataStore(name = "settings" )

        lat = sharedPreferences.getInt(sharedLat, 52)
        lon = sharedPreferences.getInt(sharedLong, 4)



        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        viewModel.house.observe(viewLifecycleOwner, Observer {
            Log.d("DATA", it.get(0).city + "")


            //Sorted List by Price
            adapter = HouseAdapter(it.sortedBy { it.price }
                    as MutableList<HousePojo>, listener,
               lat!!,
               lon!!)
          binding.recyclerViewHouses.adapter = adapter

        })


        binding.citySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText?.lowercase())
                // adapter.registerAdapterDataObserver(emptyObserver)
                //var size=adapter.houseFilterList.size

                if(adapter.houseFilterList.size==0){
                    binding.recyclerViewHouses.visibility=View.GONE
                    binding.imgViewEmpty.visibility=View.VISIBLE

                }else{
                    binding.recyclerViewHouses.visibility=View.VISIBLE
                    binding.imgViewEmpty.visibility=View.GONE
                }

                return false
            }

        })



    }

    private val listener = HouseAdapter.OnClickListener {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
        )
    }
    // adapter.onItemClick={
//            findNavController().navigate(
//                HomeFragmentDirections.actionHomeFragmentToDetailFragment()
//            )
    //  }

    //
//    private suspend fun read(key: String): Double {
//        val dataStoreKey = preferencesKey<Double>(key)
//        val preferences = dataStore.data.first()
//        return preferences[dataStoreKey]!!
//    }
//fun Context.readDouble(key: String): Flow<Double> {
//    return dataStore.data.map { pref ->
//        pref[doublePreferencesKey(key)] ?: Double
//    }
//}


    val emptyObserver: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
         //   val size=  adapter.houseFilterList.size



            // Not called
        }
    }

}



