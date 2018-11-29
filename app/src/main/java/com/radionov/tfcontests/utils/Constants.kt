package com.radionov.tfcontests.utils

/**
 * @author Andrey Radionov
 */

enum class TaskStatuses(val title: String) {
    NEW("new"),
    ANNOUNCEMENT("announcement"),
    ONGOING("ongoing"),
    CONTEST_REVIEW("contest_review"),
    ACCEPTED("accepted"),
    FAILED("failed")
}
