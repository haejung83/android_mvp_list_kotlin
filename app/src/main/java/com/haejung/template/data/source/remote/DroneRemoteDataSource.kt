package com.haejung.template.data.source.remote

import android.os.Handler
import com.haejung.template.data.Drone
import com.haejung.template.data.source.DronesDataSource
import com.haejung.template.util.AppExecutors

class DroneRemoteDataSource private constructor(
    val appExecutors: AppExecutors
) : DronesDataSource {

    private var DRONE_DATA = LinkedHashMap<String, Drone>(5)

    init {
        addFakeDrones()
    }

    private fun addFakeDrones() {
        for (i in 0 until 2) {
            val drone = Drone(
                "quad",
                5 + i,
                if (i % 2 == 0) "kiss" else "beta",
                1300 + (i * 100)
            )
            DRONE_DATA.put(drone.id, drone)
        }
    }

    override fun getDrones(callback: DronesDataSource.LoadDronesCallback) {
        val drones = DRONE_DATA.values.toList()
        Handler().postDelayed({
            callback.onDronesLoaded(drones)
        }, FAKE_LATENCY_IN_MILLS)
    }

    override fun getDrone(id: String, callback: DronesDataSource.GetDroneCallback) {
        val drone: Drone? = DRONE_DATA[id]

        with(Handler()) {
            if (drone != null)
                postDelayed({ callback.onDroneLoaded(drone) }, FAKE_LATENCY_IN_MILLS)
            else
                postDelayed({ callback.onDataNotAvailable() }, FAKE_LATENCY_IN_MILLS)
        }
    }

    override fun saveDrone(drone: Drone) {
        DRONE_DATA.put(drone.id, drone)
    }

    override fun refreshDrones() {

    }

    override fun deleteAllDrones() {
        DRONE_DATA.clear()
    }

    override fun deleteDrone(droneId: String) {
        DRONE_DATA.remove(droneId)
    }

    companion object {
        private const val FAKE_LATENCY_IN_MILLS = 5000L
        private var instance : DroneRemoteDataSource? = null
        private val lock = Any()

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors) : DroneRemoteDataSource {
            return synchronized(lock) {
                instance ?: DroneRemoteDataSource(appExecutors).apply {
                    instance = this
                }
            }
        }
    }

}