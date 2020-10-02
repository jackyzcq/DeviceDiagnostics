package org.silvermoon.devicediagnostics.util

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment

private val LOCATION_PERM = 101

fun Fragment.isGranted(permission: AppPermission) = run {
    context?.let {
        (PermissionChecker.checkSelfPermission(it, permission.permissionName
        ) == PermissionChecker.PERMISSION_GRANTED)
    } ?: false
}

fun Fragment.shouldShowRationale(permission: AppPermission) = run {
    shouldShowRequestPermissionRationale(permission.permissionName)
}

fun Fragment.requestPermission(permission: AppPermission) {
    requestPermissions(arrayOf(permission.permissionName), permission.requestCode
    )
}

fun AppCompatActivity.checkPermission(permission: AppPermission) = run {
    applicationContext?.let {
        (ActivityCompat.checkSelfPermission(it, permission.permissionName
        ) == PermissionChecker.PERMISSION_GRANTED)
    } ?: false
}

fun AppCompatActivity.shouldRequestPermissionRationale(permission: AppPermission) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission.permissionName)

fun AppCompatActivity.requestAllPermissions(permission: AppPermission) {
    ActivityCompat.requestPermissions(this, arrayOf(permission.permissionName), permission.requestCode)
}

//region Permissions
fun checkPermission(activity: AppCompatActivity): Boolean {
    return if ((ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
                !== PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
                !== PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
                !== PackageManager.PERMISSION_GRANTED)
    ) {
        // Permission is not granted
        false
    } else {
        true
    }
}

fun requestPermission(activity: AppCompatActivity) {

    if (!ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ) {
        ActivityCompat.requestPermissions(
            activity, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            LOCATION_PERM
        )
    }
}

//endregion