package uz.shokirov.map

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    var marker: Marker? = null
    val listPolyline = ArrayList<LatLng>()
    val listPolygone = ArrayList<LatLng>()
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(35.8, 0.13)
        marker = mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        marker?.setIcon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_mylocation))
        CameraUpdateFactory.newLatLngZoom(sydney, 2f)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        /* mMap.setOnMapClickListener {
             marker?.position = it
             val camera = CameraUpdateFactory.newLatLngZoom(it, 5f)
             mMap.animateCamera(camera)
         }*/

        /*  val polyline = mMap.addPolyline(PolylineOptions().clickable(true)
                  .addAll(listPolyline)
                  .color(Color.GREEN)
                  )
          mMap.setOnMapClickListener {
              listPolyline.add(it)
              polyline.points = listPolyline
          }*/
        /*listPolygone.add(LatLng(35.8,0.13))
        val polygon = mMap.addPolygon(PolygonOptions()
                .addAll(listPolygone)
                .clickable(true).fillColor(Color.GREEN))

        mMap.setOnMapClickListener {
            listPolygone.add(it)
            polygon.points = listPolygone
        }
        mMap.setOnPolygonClickListener {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }*/

        mMap.setOnMapClickListener {
            Toast.makeText(this, getAddressFromLatLng(this, LatLng(it.latitude,it.longitude)), Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("MissingPermission")
    fun getAddressFromLatLng(context: Context?, latLng: LatLng): String? {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(context, Locale.getDefault())
        return try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            addresses[0].getAddressLine(0)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }




}