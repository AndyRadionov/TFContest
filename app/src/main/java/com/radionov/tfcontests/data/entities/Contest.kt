package com.radionov.tfcontests.data.entities

/**
 * @author Andrey Radionov
 */
data class Contest(
    val title: String,
    val duration: Int
)

data class ContestResponse(
    val contest: Contest,
    val problems: List<String?>
)
