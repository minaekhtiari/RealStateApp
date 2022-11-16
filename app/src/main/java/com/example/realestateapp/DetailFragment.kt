package com.example.realestateapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.realestateapp.cons.Cons
import com.example.realestateapp.cons.Cons.IMAGE_URL
import com.example.realestateapp.databinding.FragmentDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso


class DetailFragment : Fragment(), OnMapReadyCallback {
    private val sharedLatitude = Cons.PREFERENCE_LAT
    private val sharedLongitude = Cons.PREFERENCE_L0NG
    private val sharedPreferences by lazy {
        requireActivity().applicationContext
            .getSharedPreferences(
                "_sharedPreferences",
                Context.MODE_PRIVATE
            )
    }
    private lateinit var mMap: GoogleMap
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var latitude: Int? = null
    private var longitude: Int? = null

    private val args: DetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //  return inflater.inflate(R.layout.fragment_detail, container, false)
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.GONE
        val house = args.houseData
        Log.d("DetailFragment", house.price.toString())
        latitude = sharedPreferences.getInt(sharedLatitude, 52)
        longitude = sharedPreferences.getInt(sharedLongitude, 4)
        binding.textLocation.text = Commands.distance(
            house.latitude, house.longitude,
            latitude!!,
            longitude!!
        ).toString()
        binding.textBed.text = house.bedrooms.toString()
        binding.textBath.text = house.bathrooms.toString()
        binding.textPrice.text = ("$" + "%,d".format(house.price))


        binding.textViewDescription.text = house.description.toString()
        binding.textLayer.text = house.id.toString()
        binding.imageViewBack.setOnClickListener {
            activity?.let {
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
            }
        }
        Picasso.get().load(IMAGE_URL + house.image)
            .into(binding.imageViewBanner)


        //MapView
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latLng = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Marker in The Netherlands")
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

    }


}