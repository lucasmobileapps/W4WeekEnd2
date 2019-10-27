package com.example.w4weekend

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.w4weekend.database.FavoriteDatabase
import com.example.w4weekend.database.FavoriteEntity
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var myDAO: FavoriteDatabase


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        myDAO = Room.databaseBuilder(
            requireContext(),
            FavoriteDatabase::class.java,
            "favorite.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        val root = inflater.inflate(R.layout.fragment_map, container, false)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //val fragment= root.fin   .findFragmentById(R.id.map)
        //val mapFragment = fragment as SupportMapFragment

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.mapType = GoogleMap.MAP_TYPE_NORMAL

        val latitude = 33.912670
        val longitude = -84.570353
        val homeLatLng = LatLng(latitude, longitude)
        val zoomLevel = 15f

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))

        setMapLongClick(map)
        setPoiClick(map)
        enableMyLocation()

        val overlaySize = 100f
        val androidOverlay = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromResource(R.drawable.ic_home))
            .position(homeLatLng, overlaySize)
        map.addGroundOverlay(androidOverlay)

        val favoriteList = myDAO.favoriteDao().getAllPersons()
        Log.d("LOG_X", favoriteList.toString())
        val latLng = "lat/lng: (${favoriteList[0].},${})"
        /*
        for (i in favoriteList.indices){
            map.addMarker(
                MarkerOptions()
                    .title("Dropped Pin")
                    .snippet(favoriteList[i].toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
            )

         */

        }
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()){
            map.setMyLocationEnabled(true)
        }
        else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                enableMyLocation()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this.context!!,
            Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

        override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    private fun setMapLongClick(map:GoogleMap){
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long:%2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            val newPerson = FavoriteEntity(latLng.latitude, latLng.longitude)
            myDAO.favoriteDao().insertNewFavorite(newPerson)

            Log.d("LOG_X", myDAO.favoriteDao().getAllPersons().toString())

            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )
            Log.d("LOG_X", latLng.toString())

        }
    }

    private fun setPoiClick(map: GoogleMap){
        map.setOnPoiClickListener{ pointOfInterest ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long:%2$.5f",
                pointOfInterest.latLng.latitude,
                pointOfInterest.latLng.longitude
            )
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(pointOfInterest.latLng)
                    .title(pointOfInterest.name)
                    .snippet(snippet)
            )
            poiMarker.showInfoWindow()
        }
    }




}
