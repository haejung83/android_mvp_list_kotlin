package com.haejung.template.drones

import com.haejung.template.data.Drone
import com.haejung.template.data.source.DroneRepository
import com.haejung.template.data.source.DronesDataSource

class DronesPresenter(
    private val dronesRepository: DroneRepository,
    private val dronesView: DronesContract.View
) : DronesContract.Presenter {

    init {
        dronesView.presenter = this
    }

    override fun start() {
        // Get drones from DroneRepository
        // And then set data to view
        dronesRepository.getDrones(object : DronesDataSource.LoadDronesCallback {
            override fun onDronesLoaded(drones: List<Drone>) {
                dronesView.showDrones(drones)
            }

            override fun onDataNotAvailable() {
                dronesView.showError()
            }

        })
    }

    override fun result(requestCode: Int, resultCode: Int) {
    }

    override fun loadDrones(force: Boolean) {
    }

    override fun openDroneDetails(requestedDrone: Drone) {
        dronesView.showDroneDetailsUI(requestedDrone.name)
    }

}