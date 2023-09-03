package com.bos.oculess

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Handler
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.bos.oculess.util.AppOpsUtil
import java.lang.Exception

class AudioService : AccessibilityService() {
    companion object {
        private const val TAG = "OculessAudioService"
        private var audioApps: Array<String>? = null
        private var canUpdateAppList = true
        fun isAccessibilityInitialized(context: Context): Boolean {
            try {
                Settings.Secure.getInt(
                    context.applicationContext.contentResolver,
                    Settings.Secure.ACCESSIBILITY_ENABLED
                )
                val settingValue = Settings.Secure.getString(
                    context.applicationContext.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
                )
                if (settingValue != null) {
                    return settingValue.contains(context.packageName)
                }
            } catch (e: SettingNotFoundException) {
                return false
            }
            return false
        }
        fun requestAccessibility(context: Context) {
            val localIntent = Intent("android.settings.ACCESSIBILITY_SETTINGS")
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            localIntent.setPackage("com.android.settings")
            context.startActivity(localIntent)
        }
    }
    override fun onAccessibilityEvent(e: AccessibilityEvent?) {
        if (e?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            checkUpdateAppList()
            audioFix()
        } else if (e?.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            audioFix()
        }
    }

    override fun onInterrupt() {
        Log.w(TAG, "Service Interrupted!")
    }

    private fun checkUpdateAppList() {
        if (canUpdateAppList) {
            try {
                updateAudioPackages()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val handler: Handler = Handler()
            canUpdateAppList = false
            handler.postDelayed({
                canUpdateAppList = true
                Log.i(TAG, "Allowed to re-scan packages")
            }, 10000) //1s timeout before we're allowed to look for new packages
        }
    }

    // Audio-specific
    private fun audioFix() {
        val handler: Handler = Handler()
        val r = Runnable {
            enableAudioPermission()
        }
        handler.postDelayed(r, 250)
        handler.postDelayed(r, 750)
    }
    private fun updateAudioPackages() {
        Log.i(TAG, "Start package scan...")

        if (applicationContext.packageManager == null) {
            Log.w(TAG, "applicationContext.packageManager is null!")
            return
        }
        /* Get All Installed Packages for Audio */
        // Requires query-all-packages permission
        val packageInfoList = applicationContext.packageManager?.getInstalledPackages(0)
        val packageNames = arrayListOf<String>()
        for (packageInfo in packageInfoList!!) {
            try {
                packageNames.add(packageInfo.packageName)
            } catch (e: Exception) {
                Log.w(TAG, "Error while iterating packages")
                e.printStackTrace()
            }
        }
        // Remove system apps
        val packageInfoListSystem = applicationContext.packageManager?.getInstalledPackages(
            PackageManager.MATCH_SYSTEM_ONLY)
        for (packageInfoSystem in packageInfoListSystem!!) {
            try {
                packageNames.remove(packageInfoSystem.packageName)
            } catch (e: Exception) {
                Log.w(TAG, "Error while iterating packages")
                e.printStackTrace()
            }
        }
        // Commit to app list
        audioApps = packageNames.toTypedArray()
        Log.i(TAG, "...end package scan")
    }

    private fun enableAudioPermission() {
        Log.d(TAG, "Enabling playback permissions")

        if (audioApps == null) {
            Log.i(TAG, "AudioApps is null!")
            return
        }
        for (app in audioApps!!) {
            // https://cs.android.com/android/platform/superproject/+/master:frameworks/proto_logging/stats/enums/app/enums.proto;l=138?q=PLAY_AUDIO
            try {
                val info: ApplicationInfo?= applicationContext.packageManager?.getApplicationInfo(app, 0)
                AppOpsUtil.allowOp(applicationContext, 27, info?.uid!!, app) // Record audio (Future-proofing)
                AppOpsUtil.allowOp(applicationContext, 28, info?.uid!!, app) // Play audio
            } catch (e: SecurityException) {
                Log.w(TAG, "Audio service lacks permission to set appops. Is Oculess device owner?")
            } catch (e: Exception) {
                Log.w(TAG, "An exception occurred while setting permissions")
            }
        }
    }
}