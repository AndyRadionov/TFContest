package com.radionov.tfcontests.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class HomeWork(
    val id: Int,
    val title: String,
    val tasks: List<Task>
)

data class HomeWorksResponse(val homeworks: List<HomeWork>)

data class Task(
    val id: Int,
    val task: TaskDetails,
    val status: String,
    val mark: String
)

data class TaskDetails(
    val id: Int,
    val title: String,
    @SerializedName("task_type") val taskType: String,
    @SerializedName("contest_info") val contestInfo: ContestInfo,
    @SerializedName("short_name") val shortName: String
)

data class ContestInfo(
    @SerializedName("contest_status") val contestStatus: ContestStatus,
    @SerializedName("contest_url") val contestUrl: String
)

data class ContestStatus(val status: String)
