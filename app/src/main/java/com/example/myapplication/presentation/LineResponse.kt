package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class LineResponse(
    val id: Int,
    val perspective: Boolean,
    val name: TextResponse,
    val color: String,
    val icon: String? = null,
    val ordering: Int,
    val stationStartId: Int,
    val stationEndId: Int,
    val textStart: TextResponse,
    val textEnd: TextResponse,
    val neighboringLines: List<Int>,

)