package com.haejung.template.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.haejung.template.R
import com.haejung.template.data.Drone
import com.haejung.template.util.showSnackBar
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_details.*

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailsFragment : Fragment(), DetailsContract.View {

    override lateinit var presenter: DetailsContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded

    override fun setLoadingIndicator(active: Boolean) {
        Logger.d("setLoadingIndicator: $active")
    }

    override fun showDroneDetails(drone: Drone) {
        Logger.d("showDroneDetails: ${drone.name}")
        with(drone) {
            textValueName.text = name
            textValueType.text = type
            textValueFC.text = fc
            textValueSize.text = size.toString()
            view?.let { Glide.with(it).load(image).into(imageDrone) }
        }
    }

    override fun showNoDrones() {
        view?.showSnackBar("No Drones", Snackbar.LENGTH_SHORT)
    }

    override fun showError() {
        view?.showSnackBar("Error", Snackbar.LENGTH_SHORT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    companion object {
        fun newInstance() = DetailsFragment()
    }

}
