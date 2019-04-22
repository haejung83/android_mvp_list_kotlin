package com.haejung.template.drones

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haejung.template.R
import com.haejung.template.data.Drone
import com.haejung.template.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_drone_item.*

import kotlin.properties.Delegates


class DronesAdapter : RecyclerView.Adapter<DronesAdapter.ViewHolder>() {

    var items: List<Drone> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.view_drone_item))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view

        @SuppressLint("SetTextI18n")
        fun bind(drone: Drone) {
            with(drone) {
                textDroneName.text = fc
                textDroneType.text = type
            }
        }
    }

}