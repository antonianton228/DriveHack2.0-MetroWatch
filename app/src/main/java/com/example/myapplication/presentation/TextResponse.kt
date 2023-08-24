package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class TextResponse(
    val ru: String? = null,
    val en: String? = null,
)