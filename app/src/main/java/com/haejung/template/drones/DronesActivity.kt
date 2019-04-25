package com.haejung.template.drones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.haejung.template.R
import com.haejung.template.data.injectRepository
import com.haejung.template.util.replaceFragmentInActivity

class DronesActivity : AppCompatActivity() {

    private lateinit var dronesPresenter: DronesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drones)

        // Create Fragment (View)
        val dronesFragment = supportFragmentManager.findFragmentById(R.id.content)
                as DronesFragment? ?: DronesFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.content)
        }

        // Create Presenter (Presenter)
        dronesPresenter = DronesPresenter(injectRepository(applicationContext), dronesFragment)
    }

}
