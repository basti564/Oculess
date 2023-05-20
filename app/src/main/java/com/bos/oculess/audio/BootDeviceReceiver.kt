package com.bos.oculess.audio

import android.app.admin.DevicePolicyManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import com.bos.oculess.util.AppOpsUtil
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class BootDeviceReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "OculessBootReceiver"
        private var audioApps: Array<String>? = null
        private var applicationContext: Context? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        val message = "BootDeviceReceiver onReceive, action is $action"
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        Log.d(
            TAG,
            action!!
        )
        if (Intent.ACTION_BOOT_COMPLETED == action) {
            applicationContext = context
            startLoop()
        }
    }

    /* Start RunAfterBootService service directly and invoke the service every 10 seconds. */
    private fun startLoop() {
        val dpm = Companion.applicationContext?.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        if (! dpm.isDeviceOwnerApp(applicationContext?.packageName!!)) {
            Log.w(TAG, "No device owner - canceling background audio thread")
            return
        }

        updateAudioPackages()

        val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        scheduler.scheduleAtFixedRate(Runnable {
            enableAudioPermission()

        }, 5, 1, TimeUnit.SECONDS)

        val scheduler2: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        scheduler2.scheduleAtFixedRate(Runnable {
            updateAudioPackages()

        }, 1, 1, TimeUnit.MINUTES)


    }

    private fun updateAudioPackages() {
        /* Get All Installed Packages for Audio */
        // Requires query-all-packages permission
        val packageInfoList = applicationContext?.packageManager?.getInstalledPackages(0)
        val packageNames = arrayListOf<String>()
        for (packageInfo in packageInfoList!!) {
            packageNames.add(packageInfo.packageName)
        }
        // Remove system apps
        val packageInfoListSystem = applicationContext?.packageManager?.getInstalledPackages(PackageManager.MATCH_SYSTEM_ONLY)
        for (packageInfoSystem in packageInfoListSystem!!) {
            packageNames.remove(packageInfoSystem.packageName)
        }
        // Commit to app list
        audioApps = packageNames.toTypedArray()
    }

    private fun enableAudioPermission() {
        for (app in audioApps!!) {
            val info: ApplicationInfo ?= applicationContext?.packageManager?.getApplicationInfo(app, 0)
            // https://cs.android.com/android/platform/superproject/+/master:frameworks/proto_logging/stats/enums/app/enums.proto;l=138?q=PLAY_AUDIO
            AppOpsUtil.allowOp(applicationContext, 28, info?.uid!!, app)
        }
    }
}