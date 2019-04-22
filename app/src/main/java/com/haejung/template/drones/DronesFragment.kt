package com.haejung.template.drones


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.haejung.template.R
import com.haejung.template.data.Drone

class DronesFragment : Fragment(), DronesContract.View {
    override lateinit var presenter: DronesContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded

    override fun setLoadingIndicator(active: Boolean) {

    }

    override fun showDrones(drones: List<Drone>) {

    }

    override fun showDroneDetailsUI(droneId: String) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drones, container, false)
    }


}
