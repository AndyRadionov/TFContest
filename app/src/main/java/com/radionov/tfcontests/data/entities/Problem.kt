package com.radionov.tfcontests.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class Problem(
    val id: Int,
    val position: Int,
    val problem: ProblemDetails,
    val status: String,
    @SerializedName("last_submission") val lastSubmission: LastSubmission
)

data class ProblemDetails(
    @SerializedName("cms_page") val cmsPage: CmsPage,
    @SerializedName("problem_type") val problemType: String,
    @SerializedName("answer_choices") val answerChoices: List<String>
)

data class CmsPage(
    @SerializedName("unstyled_statement") val statement: String
)

data class LastSubmission(val file: String)
