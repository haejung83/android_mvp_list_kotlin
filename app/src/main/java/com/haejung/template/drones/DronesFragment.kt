package com.haejung.template.drones


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.haejung.template.R
import com.haejung.template.data.Drone
import com.haejung.template.details.DetailsActivity
import com.haejung.template.util.showSnackBar
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_drones.*

class DronesFragment : Fragment(), DronesContract.View {
    override lateinit var presenter: DronesContract.Presenter

    private var droneItemListener: DroneItemListener = object : DroneItemListener {
        override fun onDroneClick(clickedDrone: Drone) {
            presenter.openDroneDetails(clickedDrone)
        }
    }

    private val dronesAdapter = DronesAdapter(droneItemListener)

    override var isActive: Boolean = false
        get() = isAdded

    override fun setLoadingIndicator(active: Boolean) {
        Logger.d("setLoadingIndicator: $active")
    }

    override fun showDrones(drones: List<Drone>) {
        Logger.d("showDrones: ${drones.size}")
        dronesAdapter.items = drones
        if (recycler_view.visibility == View.GONE) {
            recycler_view.visibility = View.VISIBLE
            textEmtpyResult.visibility = View.GONE
        }
    }

    override fun showDroneDetailsUI(droneName: String) {
        Logger.d("showDroneDetailsUI: $droneName")
        val intent = Intent(context, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_DRONE_NAME, droneName)
        }
        startActivity(intent)
    }

    override fun showNoDrones() {
        view?.showSnackBar("No Drones", Snackbar.LENGTH_SHORT)
        showEmptyResult("No Drones")
    }

    override fun showError() {
        view?.showSnackBar("Error", Snackbar.LENGTH_SHORT)
        showEmptyResult("Error")
    }

    private fun showEmptyResult(msg: String) {
        if (recycler_view.visibility != View.GONE) {
            recycler_view.visibility = View.GONE
            textEmtpyResult.visibility = View.VISIBLE
        }
        textEmtpyResult.text = msg
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drones, container, false).apply {
            findViewById<RecyclerView>(R.id.recycler_view).apply {
                adapter = dronesAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    interface DroneItemListener {
        fun onDroneClick(clickedDrone: Drone)
    }

    companion object {
        fun newInstance() = DronesFragment()
    }

}
