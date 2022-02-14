package com.example.userstask.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.userstask.R
import com.example.userstask.data.entity.UserData
import com.example.userstask.ui.MainViewModel
import com.example.userstask.ui.PageType
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UserDetailsFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var mapFragment: SupportMapFragment? = null

    private val callback = OnMapReadyCallback { googleMap ->
        val lat: Double? = mainViewModel.userData?.location?.coordinates?.latitude?.toDouble()
        val lon: Double? = mainViewModel.userData?.location?.coordinates?.longitude?.toDouble()
        if (lat == null || lon == null) {
            return@OnMapReadyCallback
        }
        val userLocation = LatLng(lat, lon)
        googleMap.addMarker(MarkerOptions().position(userLocation))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_details, container, false)

        initViewModel()
        initPage(view)
        initSaveButton(view)
        initExitButton(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private fun initSaveButton(view: View) {
        val saveRemoveBtn = view.findViewById<Button>(R.id.save_remove_btn)
        if (mainViewModel.pageType == PageType.LOAD_FROM_API) {
            saveRemoveBtn.setText("Save User")
        } else {
            saveRemoveBtn.setText("Remove User")
        }
        saveRemoveBtn.setOnClickListener {
            if (mainViewModel.pageType == PageType.LOAD_FROM_API) {
                mainViewModel.saveUserData(mainViewModel.userData)

            } else {
                mainViewModel.removeUser(mainViewModel.userData)
            }
            Navigation.findNavController(view).navigate(R.id.navigate_to_usersFragment)
        }
    }

    private fun initExitButton(view: View) {
        view.findViewById<ImageView>(R.id.exit_btn).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.navigate_to_usersFragment)
        }
    }

    private fun initPage(view: View) {
        val user =  mainViewModel.userData
        val pic = view.findViewById<ImageView>(R.id.user_image)
        Glide.with(context!!).load(user?.picture?.large).circleCrop().into(pic)
        view.findViewById<TextView>(R.id.title).text = user?.name?.title
        view.findViewById<TextView>(R.id.first_name).text = user?.name?.first
        view.findViewById<TextView>(R.id.last_name).text = user?.name?.last
        view.findViewById<TextView>(R.id.gender).text = user?.gender
        view.findViewById<TextView>(R.id.city).text = user?.location?.city
        view.findViewById<TextView>(R.id.state).text = user?.location?.state
        view.findViewById<TextView>(R.id.country).text = user?.location?.country
        view.findViewById<TextView>(R.id.date).text = user?.dob?.date
        view.findViewById<TextView>(R.id.age).text = user?.dob?.age.toString()
        view.findViewById<TextView>(R.id.phone).text = user?.phone
        view.findViewById<TextView>(R.id.cell).text = user?.cell
    }
}