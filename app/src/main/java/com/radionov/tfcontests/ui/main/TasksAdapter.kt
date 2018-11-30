package com.radionov.tfcontests.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.Task
import com.radionov.tfcontests.utils.TaskStatuses
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * @author Andrey Radionov
 */
class TasksAdapter(private val clickListener: OnItemClickListener):
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private val tasks = ArrayList<Task>()

    interface OnItemClickListener {
        fun onClick(task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, i: Int) {
        viewHolder.bind(i)
    }

    override fun getItemCount() = tasks.size

    fun updateData(tasks: List<Task>) {
        //todo change to Differ
        this.tasks.clear()
        this.tasks.addAll(tasks)
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        fun bind(position: Int) {
            itemView.setOnClickListener(this)

            val task = tasks[position]
            itemView.tv_short_name.text = task.task.shortName
            itemView.tv_title.text = task.task.title
            val isOngoing = task.task.contestInfo.contestStatus.status == TaskStatuses.ONGOING.title
            itemView.ongoing_badge.visibility = if (isOngoing) View.VISIBLE else View.INVISIBLE
        }

        override fun onClick(v: View) {
            val task = tasks[adapterPosition]
            clickListener.onClick(task)
        }
    }
}