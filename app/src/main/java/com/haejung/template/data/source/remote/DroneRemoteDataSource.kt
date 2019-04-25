package com.haejung.template.data.source.remote

import android.util.Log
import com.haejung.template.data.Drone
import com.haejung.template.data.source.DronesDataSource
import com.haejung.template.util.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DroneRemoteDataSource private constructor(
    private val appExecutors: AppExecutors
) : DronesDataSource {
    private val droneAPI: DroneAPI = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://10.0.2.2:8080")
        .build()
        .create(DroneAPI::class.java)

    override fun getDrones(callback: DronesDataSource.LoadDronesCallback) {
        droneAPI.requestListDrones().enqueue(object : Callback<List<Drone>> {
            override fun onFailure(call: Call<List<Drone>>, t: Throwable) {
                Log.d("getDrones", "onFailure ${t.message}")
                callback.onDataNotAvailable()
            }

            override fun onResponse(call: Call<List<Drone>>, response: Response<List<Drone>>) {
                Log.d("getDrones", "onResponse ${response.body()}")
                response.body()?.let { list ->
                    val listAddedHostAddress = list.map {
                        it.copy(image = "http://10.0.2.2:8080/drones/image/${it.name}")
                    }
                    callback.onDronesLoaded(listAddedHostAddress)
                }
            }
        })
    }

    override fun getDrone(id: String, callback: DronesDataSource.GetDroneCallback) {
        droneAPI.requestDrone(id).enqueue(object : Callback<Drone> {
            override fun onFailure(call: Call<Drone>, t: Throwable) {
                Log.d("getDrone", "onFailure ${t.message}")
                callback.onDataNotAvailable()
            }

            override fun onResponse(call: Call<Drone>, response: Response<Drone>) {
                Log.d("getDrone", "onResponse ${response.body()}")
                if (response.isSuccessful && response.body() != null)
                    callback.onDroneLoaded(response.body()!!)
            }
        })

    }

    override fun saveDrone(drone: Drone) {
    }

    override fun refreshDrones() {
    }

    override fun deleteAllDrones() {
    }

    override fun deleteDrone(droneId: String) {
    }

    companion object {
        private var instance: DroneRemoteDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors): DroneRemoteDataSource {
            return instance ?: synchronized(this) {
                instance ?: DroneRemoteDataSource(appExecutors).apply {
                    instance = this
                }
            }
        }
    }

}