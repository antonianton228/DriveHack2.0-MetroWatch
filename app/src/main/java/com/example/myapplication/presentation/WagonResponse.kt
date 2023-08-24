package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class WagonResponse(
    val stationToId: Int,
    val stationPrevId: Int,
    val types: List<String>
)