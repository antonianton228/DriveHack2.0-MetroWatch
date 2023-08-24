package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleTrainResponse(
    val stationToId: Int,
    val stationToName: String,
    val first: String?,
    val last: String?,
    val dayType: String?,
    val weekend: Boolean?
)