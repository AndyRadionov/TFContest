package com.radionov.tfcontests.utils

/**
 * @author Andrey Radionov
 */
const val ANSWERED_QUESTION_STATUS = "AC"
const val TEST_LECTURE_TYPE = "test_during_lecture"
const val EMPTY_STRING = ""

enum class TaskStatuses(val title: String) {
    NEW("new"),
    ANNOUNCEMENT("announcement"),
    ONGOING("ongoing"),
    CONTEST_REVIEW("contest_review"),
    ACCEPTED("accepted"),
    FAILED("failed")
}
