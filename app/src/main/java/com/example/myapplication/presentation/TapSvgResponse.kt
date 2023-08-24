package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class TapSvgResponse(
    val x: Double,
    val y: Double,
    val h: Double,
    val w: Double,
)