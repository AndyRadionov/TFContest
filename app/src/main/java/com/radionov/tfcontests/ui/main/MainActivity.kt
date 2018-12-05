package com.radionov.tfcontests.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.Task
import com.radionov.tfcontests.ui.contest.ContestFragmentDialog
import com.radionov.tfcontests.ui.main.adapter.TasksAdapter
import com.radionov.tfcontests.ui.profile.ProfileActivity
import com.radionov.tfcontests.ui.settings.SettingsActivity
import com.radionov.tfcontests.utils.TaskStatuses
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private lateinit var tasksAdapter: TasksAdapter
    private var chartTestsIndex = 0

    private val clickListener = object : TasksAdapter.OnItemClickListener {
        override fun onClick(task: Task) {
            presenter.getContest(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showTasks(tasks: List<Task>, done: Int, points: Float) {

        tv_progress.text = getString(R.string.tests_progress, done, tasks.size)
        tv_tests.text = tasks.size.toString()
        tv_points.text = points.toString()

        val donePercent = done.toFloat() / tasks.size * 100
        tv_tests_percent.text = getString(R.string.tests_percent, donePercent.toInt())
        tests_chart.addEvent(
            DecoEvent.Builder(donePercent)
                .setDuration(CHART_ANIM_DURATION)
                .setIndex(chartTestsIndex)
                .build())
        tv_tests_all.text = getString(R.string.all_tests, tasks.size)

        val ongoingTask = tasks.firstOrNull { task ->
            task.task.contestInfo.contestStatus.status == TaskStatuses.ONGOING.title
        }
        tasksAdapter.updateData(tasks)
        ongoingTask?.let { tasks_container.smoothScrollToPosition(tasks.indexOf(ongoingTask)) }
    }

    override fun openContest(contestUrl: String) {
        ContestFragmentDialog
            .newInstance(contestUrl)
            .show(supportFragmentManager, ContestFragmentDialog.TAG)
    }

    override fun showError(errorResource: Int) {
        Toasty.error(this@MainActivity, getString(errorResource), Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(successResource: Int) {
        Toasty.success(this@MainActivity, getString(successResource), Toast.LENGTH_SHORT).show()
    }

    private fun init() {
        tests_chart.addSeries(
            SeriesItem.Builder(ResourcesCompat.getColor(resources, R.color.light_gray, null))
                .setRange(0f, FULL_CHART, FULL_CHART)
                .setLineWidth(CHART_LINE_WIDTH)
                .build()
        );

        val chartTestsSeries = SeriesItem.Builder(ResourcesCompat.getColor(resources, R.color.test_bg, null))
            .setInitialVisibility(false)
            .setLineWidth(CHART_LINE_WIDTH)
            .build()

        chartTestsIndex = tests_chart.addSeries(chartTestsSeries);

        tasksAdapter = TasksAdapter(clickListener)

        tasks_container.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        tasks_container.adapter = tasksAdapter

        presenter.getHomeWorks()
    }

    companion object {
        private const val FULL_CHART = 100f
        private const val CHART_LINE_WIDTH = 32f
        private const val CHART_ANIM_DURATION = 500L
    }
}
