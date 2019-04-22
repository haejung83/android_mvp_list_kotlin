package com.haejung.template.data.source.local

import com.haejung.template.data.Drone
import com.haejung.template.data.source.DronesDataSource
import com.haejung.template.util.AppExecutors

class DroneLocalDataSource private constructor(
    val appExecutor: AppExecutors,
    val droneDao: DroneDao
) : DronesDataSource {

    override fun getDrones(callback: DronesDataSource.LoadDronesCallback) {
        appExecutor.diskIO.execute {
            val drones : List<Drone> = droneDao.findAll()
            appExecutor.mainThread.execute {
                if(drones.isEmpty())
                    callback.onDataNotAvailable()
                else
                    callback.onDronesLoaded(drones)
            }
        }
    }

    override fun getDrone(id: String, callback: DronesDataSource.GetDroneCallback) {
        appExecutor.diskIO.execute {
            val drone : Drone? = droneDao.findById(id)
            appExecutor.mainThread.execute {
                if(drone != null) {
                    callback.onDroneLoaded(drone)
                }
                else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun saveDrone(drone: Drone) {
        appExecutor.diskIO.execute {
            droneDao.save(drone)
        }
    }

    override fun refreshDrones() {
        // Nothing to do
    }

    override fun deleteAllDrones() {
        appExecutor.diskIO.execute {
            droneDao.deleteAll()
        }
    }

    override fun deleteDrone(droneId: String) {
        appExecutor.diskIO.execute {
            droneDao.deleteById(droneId)
        }
    }

    companion object {
        private var instance : DroneLocalDataSource? = null
        private val lock = Any()

        @JvmStatic
        fun getInstance(appExecutor: AppExecutors, droneDao: DroneDao) : DroneLocalDataSource {
            if(instance == null) {
                synchronized(lock) {
                    instance = DroneLocalDataSource(appExecutor, droneDao)
                }
            }
            return instance!!
        }
    }

}