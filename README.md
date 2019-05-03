# [List] Android MVP Pattern
> 간단한 MVP 패턴을 사용한 List 화면 Android 예제 입니다.

* RxJava Version
  * [Branch](https://github.com/haejung83/android_mvp_list_kotlin/tree/mvp_rxjava)


* 사용기술

  * Android MVP Pattern
  * Kotlin

  

* Data Server

  * 간단한 데이터 서버로부터 RESTful API를 사용하여 표시할 정보를 획득 합니다.

  * ```shell
    $ git clone https://github.com/haejung83/server_drone_kotlin.git
    $ cd server_drone_kotlin
    $ ./gradlew bootRun
    ```

  

* 화면

  * 에러 또는 결과 없음 (서버가 동작 하지 않거나 네트워크가 불안정한 경우)

  ![00_error](https://user-images.githubusercontent.com/6600546/56936212-16b45f80-6b31-11e9-91e3-3f4d4fe7004a.png)

  * 리스트 표시 (서버에서 데이터를 수신 받아 리스트에 데이터를 표시)

  ![01_list](https://user-images.githubusercontent.com/6600546/56936215-1b791380-6b31-11e9-9e6e-47df9cfa2f49.png)

  * 상세 내용 표시 (리스트에서 선택한 아이템의 상세 화면 표시)

  ![02_detail](https://user-images.githubusercontent.com/6600546/56936217-1fa53100-6b31-11e9-9f82-1dc14618e33b.png)



* MVP

  * Model

    * 데이터 소스 인터페이스 (Repository, Local/Remote Data Source의 인터페이스)

      ```kotlin
      interface DronesDataSource {
          interface LoadDronesCallback {
              fun onDronesLoaded(drones: List<Drone>)
              fun onDataNotAvailable()
          }
          
          interface GetDroneCallback {
              fun onDroneLoaded(drone: Drone)
              fun onDataNotAvailable()
          }
          
          fun getDrones(callback: LoadDronesCallback)
          fun getDrone(name: String, callback: GetDroneCallback)
          fun saveDrone(drone: Drone)
          fun refreshDrones()
          fun deleteAllDrones()
          fun deleteDrone(name: String)
      }
      ```

    * 엔티티 (DTO와 Entity를 겸용으로 쓰는 데이터 객체)

      ```kotlin
      data class Drone @JvmOverloads constructor(
          @ColumnInfo(name = "name") var name: String = "",
          @ColumnInfo(name = "type") var type: String = "",
          @ColumnInfo(name = "prop_size") var size: Int = 0,
          @ColumnInfo(name = "fc") var fc: String = "",
          @ColumnInfo(name = "image") var image: String = "",
          @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
      )
      ```

      

  * View/Presenter

    * Base

      * BaseView

        ```kotlin
        // Super simple view
        interface BaseView<T> {
            var presenter: T
        }
        ```

      * BasePresenter

        ```kotlin
        // Super simple presenter
        interface BasePresenter {
            fun start()
        }
        ```

    * Drones (List)

      ```kotlin
      interface DronesContract {
          interface View : BaseView<Presenter> {
              var isActive: Boolean
              fun setLoadingIndicator(active: Boolean)
              fun showDrones(drones: List<Drone>)
              fun showDroneDetailsUI(droneName: String)
              fun showNoDrones()
              fun showError()
          }
      
          interface Presenter : BasePresenter {
              fun result(requestCode: Int, resultCode: Int)
              fun openDroneDetails(requestedDrone: Drone)
          }
      }
      ```

    * Details

      ```kotlin
      interface DetailsContract {
          interface View : BaseView<Presenter> {
              var isActive: Boolean
              fun setLoadingIndicator(active: Boolean)
              fun showDroneDetails(drone: Drone)
              fun showNoDrones()
              fun showError()
          }
      
          interface Presenter : BasePresenter {
              fun result(requestCode: Int, resultCode: Int)
          }
      }
      ```
