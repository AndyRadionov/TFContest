package com.radionov.tfcontests.ui.main.adapter

import android.support.v7.util.DiffUtil
import com.radionov.tfcontests.data.entities.Task

/**
 * @author Andrey Radionov
 */

class TasksDiffCallback : DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(task1: Task, task2: Task) = task1.id == task2.id

    override fun areContentsTheSame(task1: Task, task2: Task) = task1 == task2
}
