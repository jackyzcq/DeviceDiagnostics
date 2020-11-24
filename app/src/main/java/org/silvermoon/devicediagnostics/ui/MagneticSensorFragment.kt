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


/**
 * A simple [Fragment] subclass.
 * Use the [MagneticSensorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MagneticSensorFragment : Fragment(), SensorEventListener {
    lateinit var sensmgr: SensorManager
    var magnetSensor: Sensor? = null
    lateinit var sensorvalues: FloatArray
    val TAG = "MagnetFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sensmgr=requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        magnetSensor=sensmgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_magnetic_sensor, container, false)
    }

    override fun onResume() {
        sensmgr.registerListener(this, magnetSensor, SensorManager.SENSOR_DELAY_NORMAL)
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
        tvX.text = "x $x uT"
        tvY.text = "y $y uT"
        tvZ.text = "z $z uT"
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.i(TAG, "onAccuracyChanged: ")
    }


}