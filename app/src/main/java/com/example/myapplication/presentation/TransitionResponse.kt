package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class TransitionResponse(
    val id: Int,
    val perspective: Boolean,
    val alternative: Boolean? = null,
    val stationFromId: Int,
    val stationToId: Int,
    val pathLength: Int,
    val videoFrom: String? = null,
    val videoTo: String? = null,
    val bi: Boolean? = null,
    val ground: Boolean,
    val svg: String? = null,
    val wagons: List<WagonResponse>,
)