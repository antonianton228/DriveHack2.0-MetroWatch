package ru.mosmetro.metro.map.data.models.schema

import kotlinx.serialization.Serializable
import ru.mosmetro.metro.map.data.models.schema.data.*
import ru.mosmetro.metro.map.data.models.schema.data.AdditionalResponse
import ru.mosmetro.metro.map.data.models.schema.data.ConnectionResponse
import ru.mosmetro.metro.map.data.models.schema.data.LineResponse
import ru.mosmetro.metro.map.data.models.schema.data.StationResponse

@Serializable
data class SchemaResponse(
    val stations: List<StationResponse>,
    val lines: List<LineResponse>,
    val connections: List<ConnectionResponse>,
    val transitions: List<TransitionResponse>,
    val additional: List<AdditionalResponse>,
    val height: Int,
    val width: Int,

)
@Serializable
data class Answer(
    val success: Boolean,
    val data: SchemaResponse
)
