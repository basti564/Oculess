package com.bos.oculess

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.Html
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bos.oculess.util.AppOpsUtil
import kotlin.concurrent.fixedRateTimer
import android.os.Handler as Handler


class MainActivity : AppCompatActivity() {
    private var audioApps: Array<String>? = null

    @SuppressLint("QueryPermissionsNeeded")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val updaterName = "com.oculus.updater"
        val telemetryApps = arrayOf(
            "com.oculus.unifiedtelemetry",
            "com.oculus.gatekeeperservice",
            "com.oculus.notification_proxy",
            "com.oculus.bugreporter",
            "com.oculus.os.logcollector",
            "com.oculus.appsafety"
        )

        val isEnabledText = findViewById<TextView>(R.id.isEnabledText)

        val viewAdminsBtn = findViewById<Button>(R.id.viewAdminsBtn)
        val viewAccountsBtn = findViewById<Button>(R.id.viewAccountsBtn)
        val viewOtaBtn = findViewById<Button>(R.id.viewOtaBtn)
        val viewTelemetryBtn = findViewById<Button>(R.id.viewTelemetryBtn)
        val viewPermissionsBtn = findViewById<Button>(R.id.viewPermissionsBtn)

        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        val deviceAdminReceiverComponentName = ComponentName(
            applicationContext,
            DevAdminReceiver::class.java
        )

        fixedRateTimer("timer", false, 0L, 1000) {
            this@MainActivity.runOnUiThread {
                if (dpm.isAdminActive(
                        ComponentName(
                            "com.oculus.companion.server",
                            "com.oculus.companion.server.CompanionDeviceAdmin\$CompanionDeviceAdminReceiver"
                        )
                    )
                ) {
                    isEnabledText.setTextColor(Color.GREEN)
                    isEnabledText.text = getString(R.string.is_enabled)
                    viewAdminsBtn.text = getString(R.string.disable_companion)
                } else {
                    isEnabledText.setTextColor(Color.RED)
                    isEnabledText.text = getString(R.string.is_disabled)
                    viewAdminsBtn.text = getString(R.string.enable_companion)
                }
                if (dpm.isDeviceOwnerApp(packageName)) {
                    if (dpm.isApplicationHidden(
                            deviceAdminReceiverComponentName, updaterName
                        )) {
                        viewOtaBtn.text = getString(R.string.enable_ota)
                    }
                    else {
                        viewOtaBtn.text = getString(R.string.disable_ota)
                    }
                } else {
                    viewOtaBtn.text = getString(R.string.disable_ota)
                }
            }
        }

        viewPermissionsBtn.setOnClickListener {
            if (dpm.isDeviceOwnerApp(packageName)) {
                updateAudioPackages()

                // Set recurring task (every 2s)
                val handler = Handler(Looper.getMainLooper())
                val run = object : Runnable {
                    override fun run() {
                        handler.postDelayed(this, 2000)
                        enableBackgroundAudio()
                    }
                }
                handler.post(run)

                viewPermissionsBtn.isEnabled = false

            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.title0))
                builder.setMessage(getString(R.string.message2))
                builder.setPositiveButton(
                    getString(R.string.ok)
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
                alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

            }
        }

        viewAdminsBtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.title0))
            builder.setMessage(getString(R.string.message0))
            builder.setPositiveButton(
                getString(R.string.ok)
            ) { _, _ ->
                startActivity(
                    Intent().setComponent(
                        ComponentName(
                            "com.android.settings",
                            "com.android.settings.DeviceAdminSettings"
                        )
                    )
                )
            }
            builder.setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()

            if (dpm.isAdminActive(
                    ComponentName(
                        "com.oculus.companion.server",
                        "com.oculus.companion.server.CompanionDeviceAdmin\$CompanionDeviceAdminReceiver"
                    )
                )
            ) {
                alertDialog.show()
                alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            } else {
                startActivity(
                    Intent().setComponent(
                        ComponentName(
                            "com.android.settings",
                            "com.android.settings.DeviceAdminSettings"
                        )
                    )
                )
            }

        }

        viewAccountsBtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.title0))
            builder.setMessage(getString(R.string.message1))
            builder.setPositiveButton(
                getString(R.string.ok)
            ) { _, _ ->
                startActivity(
                    Intent(Settings.ACTION_SYNC_SETTINGS)
                )
            }
            builder.setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()

            alertDialog.show()
            alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        viewOtaBtn.setOnClickListener {
            if (dpm.isDeviceOwnerApp(packageName)) {
                if (!dpm.isApplicationHidden(
                        deviceAdminReceiverComponentName, updaterName
                    )
                ) {
                    dpm.setApplicationHidden(
                        deviceAdminReceiverComponentName, updaterName, true
                    )
                } else {
                    dpm.setApplicationHidden(
                        deviceAdminReceiverComponentName, updaterName, false
                    )
                }
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.title0))
                builder.setMessage(getString(R.string.message2))
                builder.setPositiveButton(
                    getString(R.string.ok)
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
                alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }

        viewTelemetryBtn.setOnClickListener {
            if (dpm.isDeviceOwnerApp(packageName)) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.app_name))
                builder.setMessage(getString(R.string.message3))
                builder.setPositiveButton(
                    getString(R.string.disable)
                ) { _, _ ->
                    telemetryApps.forEach {
                        dpm.setApplicationHidden(
                            deviceAdminReceiverComponentName, it, true
                        )
                    }

                    val message = StringBuilder()
                    telemetryApps.forEach {
                        message.append("<b>")
                            .append(it)
                            .append("</b> is ")
                            .append(if (dpm.isApplicationHidden(deviceAdminReceiverComponentName, it)) "disabled\r" else "<b>enabled</b>\r")
                    }
                    val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder1.setTitle(getString(R.string.title1))
                    builder1.setMessage(Html.fromHtml(message.toString(), 0))
                    builder1.setPositiveButton(
                        getString(R.string.ok)
                    ) { dialog, _ ->
                        dialog.dismiss()
                    }
                    val alertDialog1: AlertDialog = builder1.create()
                    alertDialog1.show()
                    alertDialog1.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                }
                builder.setNegativeButton(
                    getString(R.string.enable)
                ) { _, _ ->
                    telemetryApps.forEach {
                        dpm.setApplicationHidden(
                            deviceAdminReceiverComponentName, it, false
                        )
                    }
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
                alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.title0))
                builder.setMessage(getString(R.string.message2))
                builder.setPositiveButton(
                    getString(R.string.ok)
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
                alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun enableBackgroundAudio() {
        for (app in audioApps!!) {
            val info: ApplicationInfo = applicationContext.packageManager.getApplicationInfo(app, 0)
            // Allow background audio playback
            AppOpsUtil.setMode(applicationContext, 28, info.uid, app, AppOpsManager.MODE_ALLOWED)
            // Allow background audio recording (app must have recording permission for this to do anything)
            AppOpsUtil.setMode(applicationContext, 27, info.uid, app, AppOpsManager.MODE_ALLOWED)
        }
    }

    fun updateAudioPackages() {
        /* Get All Installed Packages for Audio */
        //getInstalledPackages no longer works properly in android 11, but the quest is on android 10 so it's fine
        val packageinfos = applicationContext.packageManager.getInstalledPackages(0)
        val packageNames = arrayListOf<String>()
        for (packageinfo in packageinfos) {
            packageNames.add(packageinfo.packageName)
        }
        audioApps = packageNames.toTypedArray()
    }
}

