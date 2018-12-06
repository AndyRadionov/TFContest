package com.radionov.tfcontests.ui.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.utils.NetworkManager
import es.dmoral.toasty.Toasty
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
private const val NETWORK_STATE = "network_state"

abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    @Inject
    lateinit var networkManager: NetworkManager
    private var networkState = true

    private val networkStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onNetworkChange(networkManager.isInternetAvailable())
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onSaveInstanceState(outState)
        outState?.putBoolean(NETWORK_STATE, networkState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        networkState = savedInstanceState?.getBoolean(NETWORK_STATE) ?: true
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkStatusReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkStatusReceiver)
    }

    override fun showNotConnected() {
        Toasty.error(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
    }

    private fun onNetworkChange(isConnected: Boolean) {
        if (networkState == isConnected) return
        networkState = isConnected

        val msgRes = if (isConnected) R.string.connected else R.string.no_internet
        Snackbar.make(
            findViewById<View>(android.R.id.content),
            msgRes, Snackbar.LENGTH_LONG
        ).show()
    }
}
