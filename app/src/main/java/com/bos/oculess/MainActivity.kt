package com.bos.oculess

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.fixedRateTimer


class MainActivity : AppCompatActivity() {
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

        viewAdminsBtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.title))
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
            builder.setTitle(getString(R.string.title))
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
                builder.setTitle(getString(R.string.title))
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
                builder.setTitle(getString(R.string.title))
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
}