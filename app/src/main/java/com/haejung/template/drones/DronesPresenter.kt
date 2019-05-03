package com.haejung.template.drones

import android.util.Log
import com.haejung.template.data.Drone
import com.haejung.template.data.source.DroneRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DronesPresenter(
    private val dronesRepository: DroneRepository,
    private val dronesView: DronesContract.View
) : DronesContract.Presenter {

    init {
        dronesView.presenter = this
    }

    private var isFirstLoad = true
    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun subscribe() {
        if (isFirstLoad) {
            disposable.add(dronesRepository
                .refreshDrones()
                .subscribe {
                    isFirstLoad = false
                    loadDrones()
                }
            )
            return
        } else
            loadDrones()
    }

    private fun loadDrones() {
        dronesView.setLoadingIndicator(true)
        // Get drones from DroneRepository And then set data to view
        disposable.add(
            dronesRepository
                .getDrones()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ drones ->
                    Log.d(TAG, "subscribe: ${drones.size}")
                    if (!dronesView.isActive)
                        return@subscribe

                    dronesView.setLoadingIndicator(false)
                    if (drones.isNotEmpty())
                        dronesView.showDrones(drones)
                    else
                        dronesView.showNoDrones()
                }, {
                    Log.e(TAG, "subscribe: $it")
                    if (!dronesView.isActive)
                        return@subscribe

                    dronesView.setLoadingIndicator(false)
                    dronesView.showError()
                })
        )
    }

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun result(requestCode: Int, resultCode: Int) {
        TODO("Not implemented yet")
    }

    override fun openDroneDetails(requestedDrone: Drone) {
        dronesView.showDroneDetailsUI(requestedDrone.name)
    }

    companion object {
        private val TAG: String = DronesPresenter::class.java.simpleName
    }

}