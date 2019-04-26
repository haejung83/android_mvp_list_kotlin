package com.haejung.template.drones

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haejung.template.R
import com.haejung.template.data.Drone
import com.haejung.template.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_drone_item.*

import kotlin.properties.Delegates


class DronesAdapter(private val listener: DronesFragment.DroneItemListener) :
    RecyclerView.Adapter<DronesAdapter.ViewHolder>() {

    var items: List<Drone> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.view_drone_item), listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(private val view: View, private val listener: DronesFragment.DroneItemListener) :
        RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view

        private var droneItem: Drone? = null

        @SuppressLint("SetTextI18n")
        fun bind(drone: Drone) {
            droneItem = drone
            droneItem?.apply {
                textDroneName.text = name
                textDroneType.text = type
                image.also {
                    Glide.with(view).load(it).into(imageDrone)
                    Glide.with(view).load(it).into(imageThumbnail)
                }
                view.setOnClickListener {
                    listener.onDroneClick(this)
                }
            }
        }
    }

}