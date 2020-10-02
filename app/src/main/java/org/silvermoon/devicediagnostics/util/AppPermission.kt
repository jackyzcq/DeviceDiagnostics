package org.silvermoon.devicediagnostics.util

import android.Manifest
import org.silvermoon.devicediagnostics.R

sealed class AppPermission(
    val permissionName: String, val requestCode: Int, val deniedMessageId: Int, val explanationMessageId: Int
) {
    companion object {
        val permissions: List<AppPermission> by lazy {
            listOf(
                ACCESS_FINE_LOCATION,
                BLUETOOTH,
                CAMERA
            )
        }
    }

    object ACCESS_FINE_LOCATION : AppPermission(
        Manifest.permission.ACCESS_FINE_LOCATION, 42,
        R.string.permission_required_text, R.string.permission_required_text
    )

    object BLUETOOTH : AppPermission(
        Manifest.permission.BLUETOOTH, 43,
        R.string.permission_required_text, R.string.permission_required_text
    )

    object CAMERA : AppPermission(
        Manifest.permission.CAMERA, 44,
        R.string.permission_required_text, R.string.permission_required_text
    )
}