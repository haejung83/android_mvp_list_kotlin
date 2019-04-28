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
        dronesView.setLoadingIndicator(true)
        // Get drones from DroneRepository And then set data to view
        dronesRepository.getDrones(object : DronesDataSource.LoadDronesCallback {
            override fun onDronesLoaded(drones: List<Drone>) {
                if (!dronesView.isActive)
                    return

                dronesView.setLoadingIndicator(false)
                if (drones.isNotEmpty())
                    dronesView.showDrones(drones)
                else
                    dronesView.showNoDrones()
            }

            override fun onDataNotAvailable() {
                if (!dronesView.isActive)
                    return

                dronesView.setLoadingIndicator(false)
                dronesView.showError()
            }
        })
    }

    override fun result(requestCode: Int, resultCode: Int) {
        TODO("Not implemented yet")
    }

    override fun openDroneDetails(requestedDrone: Drone) {
        dronesView.showDroneDetailsUI(requestedDrone.name)
    }

}