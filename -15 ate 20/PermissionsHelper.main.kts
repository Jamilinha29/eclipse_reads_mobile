#!/usr/package com.example.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

/**
 * Exemplo de como pedir permissões com ActivityResultContracts de forma centralizada.
 * Use no Activity/Fragment:
 *
 * val helper = PermissionsHelper(this)
 * helper.requestCameraPermission()
 */
class PermissionsHelper(private val activity: Activity) {

    private lateinit var multiplePermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var singlePermissionLauncher: ActivityResultLauncher<String>

    fun registerLaunchers(onResult: (Map<String, Boolean>) -> Unit, onSingleResult: (Boolean) -> Unit) {
        multiplePermissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            onResult(result)
        }

        singlePermissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            onSingleResult(granted)
        }
    }

    fun requestCameraPermission() {
        singlePermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    fun requestLocationPermissions() {
        // ex.: request both fine and coarse
        multiplePermissionLauncher.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    fun showPermissionRationale(context: Context, title: String, message: String, onOk: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> onOk() }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = android.net.Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }
}
bin/env kotlin

