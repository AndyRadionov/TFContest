package com.radionov.tfcontests.ui.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.radionov.tfcontests.R
import com.radionov.tfcontests.utils.isInternetAvailable

/**
 * @author Andrey Radionov
 */
abstract class BaseActivity : MvpAppCompatActivity() {

    private val networkStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onNetworkChange(isInternetAvailable(this@BaseActivity))
        }
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

    private fun onNetworkChange(isConnected: Boolean) {
        val msgId = if(isConnected) R.string.connecten else R.string.no_internet

        Snackbar.make(
            findViewById<View>(android.R.id.content),
            msgId, Snackbar.LENGTH_LONG
        ).show()
    }
}
