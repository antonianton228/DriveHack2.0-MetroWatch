package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val lat: Double,
    val lon: Double
)