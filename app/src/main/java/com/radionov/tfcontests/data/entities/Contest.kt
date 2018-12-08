package com.radionov.tfcontests.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class Contest(
    val title: String,
    val duration: Int,
    @SerializedName("time_left") val timeLeft: Int
)

data class ContestResponse(
    val contest: Contest,
    val problems: List<String?>
)

data class Answer(
    val answer: String,
    val language: Int = 1
)
