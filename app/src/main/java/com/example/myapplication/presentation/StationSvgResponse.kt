package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class StationSvgResponse(
    val svg: String,
    val x: Double,
    val y: Double
)