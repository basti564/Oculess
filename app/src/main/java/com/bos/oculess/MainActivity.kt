@file:Suppress("DEPRECATION")

package com.bos.oculess

import android.app.admin.DevicePolicyManager
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.json.JSONTokener
import kotlin.concurrent.fixedRateTimer


class MainActivity : AppCompatActivity() {
//    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queue = Volley.newRequestQueue(this)

        val manager = this.packageManager
        val info = manager.getPackageInfo(this.packageName, PackageManager.GET_ACTIVITIES)

        val stringRequest = StringRequest(
            Request.Method.GET, "https://api.github.com/repos/basti564/oculess/releases/latest",
            { response ->
                try {
                    val jsonObject =
                        JSONTokener(response).nextValue() as JSONObject
                    if (jsonObject.getString("tag_name") != "v" + info.versionName) {
                        Log.v("Oculess", "New version available!!!!")

                        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setTitle("An update is available!")
                        builder.setMessage(
                            "We recommend you to update to the latest version of Oculess (" + jsonObject.getString(
                                "tag_name"
                            ) + ")"
                        )
                        builder.setPositiveButton("View") { _, _ ->
                            val browserIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(jsonObject.getString("html_url"))
                            )
                            startActivity(browserIntent)
                        }
                        builder.setNegativeButton("Dismiss") { dialog, _ ->
                            dialog.dismiss()
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.show()
                        alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                    } else {
                        Log.i("Oculess", "Oculess is up to date :)")
                    }
                }
                catch (e: Exception){
                    Log.e("Oculess", "Received invalid JSON", e)
                }
            },
            { Log.w("Oculess", "Couldn't get update info") })

        queue.add(stringRequest)

        val updaterName = "com.oculus.updater"
        val telemetryApps = arrayOf(
            "com.oculus.unifiedtelemetry",
            "com.oculus.gatekeeperservice",
            "com.oculus.notification_proxy",
            "com.oculus.bugreporter",
            "com.oculus.os.logcollector",
            "com.oculus.appsafety"
        )

        val viewAdminsBtn = findViewById<Button>(R.id.viewAdminsBtn)
        val viewAccountsBtn = findViewById<Button>(R.id.viewAccountsBtn)
        val viewOtaBtn = findViewById<Button>(R.id.viewOtaBtn)
        val viewTelemetryBtn = findViewById<Button>(R.id.viewTelemetryBtn)
        val audioBtn = findViewById<Button>(R.id.audioBtn)
        val ownershipBtn = findViewById<Button>(R.id.ownershipBtn)
        val status = findViewById<TextView>(R.id.Status)

        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        var isOwner = false
        var companionStatus:String ?= null

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
                    viewAdminsBtn.text = getString(R.string.disable_companion)
                    companionStatus = getString(R.string.companion_enabled)
                } else {
                    viewAdminsBtn.text = getString(R.string.enable_companion)
                    companionStatus = getString(R.string.companion_disabled)
                }
                isOwner = dpm.isDeviceOwnerApp(packageName)
                // Owner-only buttons
                viewOtaBtn.isEnabled = isOwner
                viewTelemetryBtn.isEnabled = isOwner
                audioBtn.isEnabled = isOwner
                ownershipBtn.text = if (isOwner) getString(R.string.disable_ownership) else getString(R.string.enable_ownership)
                status.text = if (isOwner) getString(R.string.owner_enabled) else getString(R.string.owner_disabled)
                // Enable/disable update button
                if (isOwner) {
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
            builder.setTitle(getString(R.string.title0))
            builder.setMessage(companionStatus + "\n" + getString(R.string.message0))
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
                    deviceAdminReceiverComponentName
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
            val intentSettings = Intent()
            intentSettings.setPackage("com.android.settings")
            intentSettings.component = ComponentName("com.android.settings","com.android.settings.Settings\$AccountDashboardActivity")

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.title0))
            builder.setMessage(getString(R.string.message1))
            builder.setPositiveButton(
                getString(R.string.ok)
            ) { _, _ ->
                startActivity(
                    intentSettings
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
                    getString(R.string.disable_telemetry)
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
                            .append(if (dpm.isApplicationHidden(deviceAdminReceiverComponentName, it)) "disabled\r" else "<b>enabled</b>\r")                    }
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
                    getString(R.string.enable_telemetry)
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

        audioBtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.title0))
            if (!isOwner) {
                builder.setMessage(getString(R.string.audio_info_none))
                builder.setPositiveButton(
                    getString(R.string.ok)
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            } else if (!AudioService.isAccessibilityInitialized(applicationContext)) {
                builder.setMessage(getString(R.string.audio_info_serv))
                builder.setPositiveButton(
                    getString(R.string.ok)
                ) { dialog, _ ->
                    AudioService.requestAccessibility(applicationContext)
                    dialog.dismiss()
                }
                builder.setNegativeButton(
                    getString(R.string.cancel)
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            } else {
                builder.setMessage(getString(R.string.audio_info_done))
                builder.setPositiveButton(
                    getString(R.string.settings)
                ) { dialog, _ ->
                    AudioService.requestAccessibility(applicationContext)
                    dialog.dismiss()
                }
                builder.setNegativeButton(
                    getString(R.string.cancel)
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
            alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        ownershipBtn.setOnClickListener {
            if (isOwner) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.title0))
                builder.setMessage(getString(R.string.message4))
                builder.setPositiveButton(
                    getString(R.string.ok)
                ) { _, _ ->
                    val devicePolicyManager =
                        getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
                    devicePolicyManager.clearDeviceOwnerApp(this.packageName)
                }
                builder.setNegativeButton(
                    getString(R.string.cancel)
                ) { dialog, _ ->
                    dialog.dismiss()
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
                alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.title0))
                builder.setMessage(getString(R.string.owner_info))
                builder.setPositiveButton(
                    getString(R.string.copy)
                ) { dialog, _ ->

                    val clipboard: ClipboardManager =
                        getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText(getString(R.string.adb), getString(R.string.adb))
                    clipboard.setPrimaryClip(clip)
                    dialog.dismiss()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
                alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

            }
        }
    }
}
