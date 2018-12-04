package com.radionov.tfcontests.ui.main.adapter

import android.support.v4.content.res.ResourcesCompat
import android.support.v7.recyclerview.extensions.AsyncListDiffer
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

    private val differ = AsyncListDiffer<Task>(this, TasksDiffCallback())

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

    override fun getItemCount() = differ.currentList.size

    fun updateData(tasks: List<Task>) {
        differ.submitList(tasks)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val failColor: Int
        private val acceptColor: Int

        init {
            val resources = itemView.resources
            failColor = ResourcesCompat.getColor(resources, R.color.task_red_color, null)
            acceptColor = ResourcesCompat.getColor(resources, android.R.color.white, null)
        }

        fun bind(position: Int) {
            itemView.setOnClickListener(this)

            val task = differ.currentList[position]
            itemView.tv_short_name.text = task.task.shortName
            itemView.tv_title.text = task.task.title
            val isOngoing = task.task.contestInfo.contestStatus.status == TaskStatuses.ONGOING.title
            val isFailed = task.status == TaskStatuses.FAILED.title
            val isAccepted = task.status == TaskStatuses.ACCEPTED.title
            itemView.ongoing_badge.visibility = if (isOngoing) View.VISIBLE else View.INVISIBLE
            if (isFailed || isAccepted) {
                itemView.tv_point.visibility = View.VISIBLE
                itemView.tv_point.text = task.mark
                itemView.tv_point.setTextColor(if (isFailed) failColor else acceptColor)
            } else {
                itemView.tv_point.visibility = View.INVISIBLE
            }
        }

        override fun onClick(v: View) {
            val task = differ.currentList[adapterPosition]
            clickListener.onClick(task)
        }
    }
}