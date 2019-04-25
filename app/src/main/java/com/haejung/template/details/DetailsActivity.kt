package com.haejung.template.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.haejung.template.R
import com.haejung.template.drones.DronesFragment
import com.haejung.template.util.replaceFragmentInActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Create Fragment (View)
        val detailsFragment = supportFragmentManager.findFragmentById(R.id.content)
                as DetailsFragment? ?: DetailsFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.content)
        }

        // Create Presenter
    }
}
