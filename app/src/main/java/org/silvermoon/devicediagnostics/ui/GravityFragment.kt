package org.silvermoon.devicediagnostics.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_gyroscope.*
import org.silvermoon.devicediagnostics.R

class GravityFragment : Fragment(), SensorEventListener {

    lateinit var sensmgr: SensorManager
    var gyrosensor: Sensor? = null
    lateinit var sensorvalues: FloatArray
    val TAG = "GravityFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sensmgr=requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        gyrosensor=sensmgr.getDefaultSensor(Sensor.TYPE_GRAVITY);

        return inflater.inflate(R.layout.fragment_gravity, container, false)
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