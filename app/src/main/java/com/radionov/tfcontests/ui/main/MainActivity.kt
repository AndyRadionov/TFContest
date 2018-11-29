package com.radionov.tfcontests.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.R
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.data.entities.Task
import com.radionov.tfcontests.ui.login.LoginActivity
import com.radionov.tfcontests.ui.profile.ProfileActivity
import com.radionov.tfcontests.ui.settings.SettingsActivity
import com.radionov.tfcontests.utils.TaskStatuses
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = mainPresenter
    private lateinit var tasksAdapter: TasksAdapter
    private val clickListener = object : TasksAdapter.OnItemClickListener {
        override fun onClick(url: String) {
            //todo implement
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        mainPresenter.getHomeWorks()
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

    override fun showTasks(tasks: List<Task>) {
        tv_tests_number.text = getString(R.string.all_tests, tasks.size)
        val ongoingTask = tasks.firstOrNull { task -> task.status == TaskStatuses.ONGOING.title }
        tasksAdapter.updateData(tasks)
        ongoingTask?.let { tasks_container.smoothScrollToPosition(tasks.indexOf(ongoingTask)) }
    }

    override fun showError() {
        Toasty.error(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
    }

    private fun init() {
        tasksAdapter = TasksAdapter(clickListener)

        tasks_container.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        tasks_container.adapter = tasksAdapter
    }
}
