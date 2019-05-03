package com.haejung.template.details

import android.util.Log
import com.haejung.template.data.source.DroneRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsPresenter(
    private val droneName: String,
    private val dronesRepository: DroneRepository,
    private val detailsView: DetailsContract.View
) : DetailsContract.Presenter {

    init {
        detailsView.presenter = this
    }

    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun subscribe() {
        detailsView.setLoadingIndicator(true)

        disposable.add(
            dronesRepository
                .getDrone(droneName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "subscribe")
                    if (!detailsView.isActive || !it.isPresent)
                        return@subscribe

                    detailsView.setLoadingIndicator(false)
                    detailsView.showDroneDetails(it.get())
                }, {
                    Log.e(TAG, "subscribe: $it")
                    if (!detailsView.isActive)
                        return@subscribe

                    detailsView.setLoadingIndicator(false)
                    detailsView.showError()
                })
        )
    }

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun result(requestCode: Int, resultCode: Int) {
        TODO("Not implemented yet")
    }

    companion object {
        private val TAG: String = DetailsPresenter::class.java.simpleName
    }
}