package com.example.realestateapp


import kotlin.math.*

class Commands {

    companion object{

        fun distance(lat1: Int, lng1: Int, lat2: Int, lng2: Int): Int {
            val earthRadius = 6371

            val dLat = Math.toRadians((lat2 - lat1).toDouble())
            val dLng = Math.toRadians((lng2 - lng1).toDouble())

            val sindLat = sin(dLat / 2)
            val sindLng = sin(dLng / 2)

            val a = sindLat.pow(2.0) +
                    (sindLng.pow(2.0) * cos(Math.toRadians(lat1.toDouble())) * cos(Math.toRadians(
                        lat2.toDouble()
                    )))

            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            return (earthRadius * c).roundToInt() // output distance, in MILES
        }




    }

}