package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class AdditionalResponse(
    val id: String,
    val svg: String
)