package com.bos.oculess

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
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

        val viewAdminsBtn = findViewById<Button>(R.id.viewAdminsBtn)
        val isEnabledText = findViewById<TextView>(R.id.isEnabledText)

        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        fixedRateTimer("timer", false, 0L, 1000) {
            this@MainActivity.runOnUiThread {
                if (dpm.isAdminActive(ComponentName("com.oculus.companion.server", "com.oculus.companion.server.CompanionDeviceAdmin\$CompanionDeviceAdminReceiver"))){
                    isEnabledText.setTextColor(Color.GREEN)
                    isEnabledText.text = getString(R.string.is_enabled)
                    viewAdminsBtn.text = getString(R.string.disable_companion)
                }
                else {
                    isEnabledText.setTextColor(Color.RED)
                    isEnabledText.text = getString(R.string.is_disabled)
                    viewAdminsBtn.text = getString(R.string.enable_companion)
                }
            }
        }

        viewAdminsBtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.title))
            builder.setMessage(getString(R.string.message))
            builder.setPositiveButton(getString(R.string.ok), DialogInterface.OnClickListener { _, _ ->
                startActivity(Intent().setComponent(ComponentName("com.android.settings", "com.android.settings.DeviceAdminSettings")))
            })
            builder.setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })
            val alertDialog: AlertDialog = builder.create()

            if (dpm.isAdminActive(ComponentName("com.oculus.companion.server", "com.oculus.companion.server.CompanionDeviceAdmin\$CompanionDeviceAdminReceiver"))){
                alertDialog.show()
                alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
            else {
                startActivity(Intent().setComponent(ComponentName("com.android.settings", "com.android.settings.DeviceAdminSettings")))
            }

        }
    }
}