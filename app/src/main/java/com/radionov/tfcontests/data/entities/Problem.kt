package com.radionov.tfcontests.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class Problem(
    @SerializedName("id") val id: Int,
    @SerializedName("position") val position: Int,
    @SerializedName("problem") val problem: ProblemDetails,
    @SerializedName("status") val status: String?,
    @SerializedName("last_submission") val lastSubmission: LastSubmission?
)

data class ProblemDetails(
    @SerializedName("cms_page") val cmsPage: CmsPage,
    @SerializedName("problem_type") val problemType: String,
    @SerializedName("answer_choices") val answerChoices: List<String>
)

data class CmsPage(
    @SerializedName("unstyled_statement") val statement: String
)

data class LastSubmission(
    @SerializedName("file") val file: String?
)
