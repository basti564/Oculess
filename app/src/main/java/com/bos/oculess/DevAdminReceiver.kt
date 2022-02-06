package com.bos.oculess

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

class DevAdminReceiver : DeviceAdminReceiver() {
    private val TAG = "DeviceAdminReceiver"
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(TAG, "Device Owner Enabled")
        Toast.makeText(context, "Device Owner Enabled", Toast.LENGTH_LONG).show()
    }
}