package com.example.misandroid.utility

import com.example.misandroid.database.MeasurementEntity
import kotlin.math.pow

class Utils {
    companion object {
        fun calculateDistance(
            userPosition: String,
            measurements: List<MeasurementEntity>
        ): Int {
            val userStrCoords = userPosition.split(',').map { c -> c.toInt() }
            var measurement = measurements[0]
            var minD = Double.MAX_VALUE
            for (mr in measurements) {
                val strength = mr.strength.split(',').map { c -> c.toInt() }
                var d: Double = (userStrCoords[0] - strength[0]).toDouble().pow(2) +
                        (userStrCoords[1] - strength[1]).toDouble().pow(2) +
                        (userStrCoords[2] - strength[2]).toDouble().pow(2)
                //maybe iter instead??

                if (minD > d) {
                    measurement = mr;
                    minD = d;
                }
            }
            return measurement.measurementId
        }
    }
}