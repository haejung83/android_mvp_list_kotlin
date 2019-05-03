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

    override var isActive: Boolean = false
        get() = isAdded

    private var droneItemListener: DroneItemListener = object : DroneItemListener {
        override fun onDroneClick(clickedDrone: Drone) {
            presenter.openDroneDetails(clickedDrone)
        }
    }

    private val dronesAdapter = DronesAdapter(droneItemListener)

    override fun setLoadingIndicator(active: Boolean) {
        Logger.d("setLoadingIndicator: $active")
    }

    override fun showDrones(drones: List<Drone>) {
        Logger.d("showDrones: ${drones.size}")
        dronesAdapter.items = drones
        if (recycler_view.visibility == View.GONE) {
            recycler_view.visibility = View.VISIBLE
            textResultExtraMessage.visibility = View.GONE
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
        view?.showSnackBar(getString(R.string.msg_no_drones), Snackbar.LENGTH_SHORT)
        showEmptyResult()
    }

    override fun showError() {
        view?.showSnackBar(getString(R.string.msg_error), Snackbar.LENGTH_SHORT)
        showEmptyResult()
    }

    private fun showEmptyResult() {
        if (recycler_view.visibility != View.GONE) {
            recycler_view.visibility = View.GONE
            textResultExtraMessage.visibility = View.VISIBLE
        }
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
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    interface DroneItemListener {
        fun onDroneClick(clickedDrone: Drone)
    }

    companion object {
        fun newInstance() = DronesFragment()
    }

}
