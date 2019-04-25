package com.haejung.template.drones


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.haejung.template.R
import com.haejung.template.data.Drone
import com.haejung.template.util.showSnackBar

class DronesFragment : Fragment(), DronesContract.View {
    override lateinit var presenter: DronesContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded

    override fun setLoadingIndicator(active: Boolean) {
        Log.d("DronesFragment", "setLoadingIndicator")
    }

    override fun showDrones(drones: List<Drone>) {
        Log.d("DronesFragment", "showDrones")
        dronesAdapter.items = drones
    }

    override fun showDroneDetailsUI(droneId: String) {
        Log.d("DronesFragment", "showDroneDetailsUI")
    }

    override fun showNoDrones() {
        view?.showSnackBar("No Drones", Snackbar.LENGTH_SHORT)
    }

    override fun showError() {
        view?.showSnackBar("Error", Snackbar.LENGTH_SHORT)
    }

    private lateinit var recyclerView: RecyclerView
    private val dronesAdapter = DronesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_drones, container, false)

        with(root) {
            recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
                adapter = dronesAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    companion object {
        fun newInstance() = DronesFragment()
    }

}
