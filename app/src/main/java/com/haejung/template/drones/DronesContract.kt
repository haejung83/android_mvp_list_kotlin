package com.haejung.template.drones

import com.haejung.template.base.BasePresenter
import com.haejung.template.base.BaseView
import com.haejung.template.data.Drone

interface DronesContract {

    interface View : BaseView<Presenter> {

        var isActive : Boolean

        fun setLoadingIndicator(active: Boolean)

        fun showDrones(drones: List<Drone>)

        fun showDroneDetailsUI(droneId: String)

        fun showNoDrones()

        fun showError()

    }

    interface Presenter : BasePresenter {

        fun result(requestCode: Int, resultCode: Int)

        fun loadDrones(force: Boolean)

//        fun addNewDrone()

//        fun openDroneDetails(requestedDrone: Drone)

    }

}