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
    @SerializedName("short_name") val shortName: String
)

data class TaskDetails(
    val id: Int,
    val title: String,

    @SerializedName("task_type") val taskType: String,
    @SerializedName("contest_info") val contestInfo: ContestInfo

    )
data class ContestInfo(
    @SerializedName("contest_status") val contestStatus: ContestStatus,
    @SerializedName("contest_url") val contestUrl: String
)

data class ContestStatus(val status: String)

/*{"id":29151,"task":
    {
    "max_score":"1.00",
    "deadline_date":null,
    "contest_info":{
        "contest_status":{
            "time_left":"0:15:0","status":"announcement"
        },"contest_url":"lecture_test_509"
    },"short_name":"Т"
},"status":"new","mark":"0.00"}]}*/
