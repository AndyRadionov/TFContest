package com.radionov.tfcontests.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class HomeWork(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("tasks") val tasks: List<Task>
)

data class HomeWorksResponse(
    @SerializedName("homeworks") val homeworks: List<HomeWork>
)

data class Task(
    @SerializedName("id") val id: Int,
    @SerializedName("task") val task: TaskDetails,
    @SerializedName("status") val status: String,
    @SerializedName("mark") val mark: String
)

data class TaskDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("task_type") val taskType: String,
    @SerializedName("contest_info") val contestInfo: ContestInfo,
    @SerializedName("short_name") val shortName: String
)

data class ContestInfo(
    @SerializedName("contest_status") val contestStatus: ContestStatus,
    @SerializedName("contest_url") val contestUrl: String
)

data class ContestStatus(
    @SerializedName("status") val status: String
)
