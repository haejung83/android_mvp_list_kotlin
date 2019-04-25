package com.haejung.template.drones


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.haejung.template.R
import com.haejung.template.data.Drone
import com.haejung.template.util.showSnackBar
import com.orhanobut.logger.Logger

class DronesFragment : Fragment(), DronesContract.View {
    override lateinit var presenter: DronesContract.Presenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var textEmptyResult: TextView
    private val dronesAdapter = DronesAdapter()

    override var isActive: Boolean = false
        get() = isAdded

    override fun setLoadingIndicator(active: Boolean) {
        Logger.d("setLoadingIndicator: $active")
    }

    override fun showDrones(drones: List<Drone>) {
        Logger.d("showDrones: ${drones.size}")
        dronesAdapter.items = drones
        if (recyclerView.visibility == View.GONE) {
            recyclerView.visibility = View.VISIBLE
            textEmptyResult.visibility = View.GONE
        }
    }

    override fun showDroneDetailsUI(droneId: String) {
        Logger.d("showDroneDetailsUI: $droneId")
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
        if (recyclerView.visibility != View.GONE) {
            recyclerView.visibility = View.GONE
            textEmptyResult.visibility = View.VISIBLE
        }
        textEmptyResult.text = msg
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drones, container, false).apply {
            recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
                adapter = dronesAdapter
                layoutManager = LinearLayoutManager(context)
            }
            textEmptyResult = findViewById<TextView>(R.id.textEmtpyResult);
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    companion object {
        fun newInstance() = DronesFragment()
    }

}
