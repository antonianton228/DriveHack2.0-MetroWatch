package ru.mosmetro.metro.map.data.models.schema.data

import kotlinx.serialization.Serializable

@Serializable
data class StationResponse(
    val id: Int,
    val perspective: Boolean,
    val alternative: Boolean? = null,
    val name: TextResponse,
    val lineId: Int,
    val location: LocationResponse,
    val exits: List<ExitResponse>,
    val enterTime: Int? = null,
    val exitTime: Int? = null,
    val ordering: Int,
    val mcd: Boolean? = null,
    val outside: Boolean? = null,
    val mcc: Boolean? = null,
    val history: String? = null,
    val audios: List<String> = emptyList(),
    val services: List<String> = emptyList(),
    val scheduleTrains: Map<Int, List<ScheduleTrainResponse>>? = null,
    val workTime: List<WorkTimeResponse>,
    val accessibilityImages: List<String>,
    val buildingImages: List<String>,
    val stationSvg: StationSvgResponse,
    val textSvg: TextSvgResponse,
    val tapSvg: TapSvgResponse,
)