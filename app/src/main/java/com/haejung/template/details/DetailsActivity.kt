package com.haejung.template.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.haejung.template.R
import com.haejung.template.data.injectRepository
import com.haejung.template.util.replaceFragmentInActivity

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsPresenter: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Create Fragment (View)
        val detailsFragment = supportFragmentManager.findFragmentById(R.id.content)
                as DetailsFragment? ?: DetailsFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.content)
        }

        // Get a drone name from intent
        val droneName = intent.getStringExtra(EXTRA_DRONE_NAME)

        // Create Presenter
        detailsPresenter = DetailsPresenter(droneName, injectRepository(applicationContext), detailsFragment)
    }


    companion object {
        const val EXTRA_DRONE_NAME = "drone_name"
    }

}
