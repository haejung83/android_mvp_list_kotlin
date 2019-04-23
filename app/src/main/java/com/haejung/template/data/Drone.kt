package com.haejung.template.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "drones")
data class Drone @JvmOverloads constructor(
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "type") var type: String = "",
    @ColumnInfo(name = "prop_size") var size : Int = 5,
    @ColumnInfo(name = "fc") var fc : String = "",
    @ColumnInfo(name = "battery") var battery : Int = 1300,
    @PrimaryKey @ColumnInfo(name = "id") var id : String = UUID.randomUUID().toString()
)