package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class WorkTimeResponse(
    val open: String? = null,
    val close: String? = null
)