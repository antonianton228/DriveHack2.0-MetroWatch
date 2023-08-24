package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class ConnectionResponse(
    val id: Int,
    val perspective: Boolean,
    val alternative: Boolean? = null,
    val stationFromId: Int,
    val stationToId: Int,
    val pathLength: Int,
    val bi: Boolean? = null,
    val svg: String? = null,
    val closedBackward: Boolean? = null,
)