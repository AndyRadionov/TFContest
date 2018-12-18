package com.radionov.tfcontests.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class Contest(
    @SerializedName("title") val title: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("time_left") val timeLeft: Int
)

data class ContestResponse(
    @SerializedName("contest") val contest: Contest,
    @SerializedName("problems") val problems: List<String?>
)

data class Answer(
    @SerializedName("answer") val answer: String,
    @SerializedName("language") val language: Int = 1
)
