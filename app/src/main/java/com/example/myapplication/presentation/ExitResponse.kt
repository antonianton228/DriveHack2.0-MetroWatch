package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class ExitResponse(
    val title: TextResponse,
    val exitNumber: Int,
    val location: LocationResponse? = null,
    val bus: String? = null,
    val trolleybus: String? = null,
    val tram: String? = null,
)