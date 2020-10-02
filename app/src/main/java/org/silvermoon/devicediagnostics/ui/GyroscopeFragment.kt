package org.silvermoon.devicediagnostics.ui

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_gyroscope.*
import org.silvermoon.devicediagnostics.R
import org.silvermoon.devicediagnostics.ui.gallery.GalleryViewModel


class GyroscopeFragment : Fragment(), SensorEventListener {

    private lateinit var galleryViewModel: GalleryViewModel
    lateinit var sensmgr: SensorManager
    var gyrosensor: Sensor? = null
    lateinit var sensorvalues: FloatArray
    val TAG = "GyroscopeFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gyroscope, container, false)

        sensmgr=requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager;
        gyrosensor=sensmgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        return root
    }

    override fun onResume() {
        sensmgr.registerListener(this, gyrosensor, SensorManager.SENSOR_DELAY_NORMAL)
        super.onResume()
    }


    override fun onPause() {
        sensmgr.unregisterListener(this)
        super.onPause()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        sensorvalues = event!!.values
        val x = sensorvalues[0]
        val y = sensorvalues[1]
        val z = sensorvalues[2]
        tvX.text = "x $x rad/sec"
        tvY.text = "y $y rad/sec"
        tvZ.text = "z $z rad/sec"
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.i(TAG, "onAccuracyChanged: ")
    }
}