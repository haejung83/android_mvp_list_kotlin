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
        dronesRepository.getDrone(droneName, object : DronesDataSource.GetDroneCallback {
            override fun onDroneLoaded(drone: Drone) {
                detailsView.showDroneDetails(drone)
            }

            override fun onDataNotAvailable() {
                detailsView.showError()
            }

        })
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }

}