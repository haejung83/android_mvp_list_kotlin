package com.haejung.template.details

import com.haejung.template.data.Drone
import com.haejung.template.data.source.DroneRepository
import com.haejung.template.data.source.DronesDataSource

class DetailsPresenter(
    private val droneName: String,
    private val dronesRepository: DroneRepository,
    private val detailsView: DetailsContract.View
) : DetailsContract.Presenter {

    init {
        detailsView.presenter = this
    }

    override fun start() {
        detailsView.setLoadingIndicator(true)

        dronesRepository.getDrone(droneName, object : DronesDataSource.GetDroneCallback {
            override fun onDroneLoaded(drone: Drone) {
                if (!detailsView.isActive)
                    return

                detailsView.setLoadingIndicator(false)
                detailsView.showDroneDetails(drone)
            }

            override fun onDataNotAvailable() {
                if (!detailsView.isActive)
                    return

                detailsView.setLoadingIndicator(false)
                detailsView.showError()
            }

        })
    }

    override fun result(requestCode: Int, resultCode: Int) {
        TODO("Not implemented yet")
    }

}