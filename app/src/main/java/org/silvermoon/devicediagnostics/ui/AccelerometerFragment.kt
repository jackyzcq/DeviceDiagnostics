package org.silvermoon.devicediagnostics.ui

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_accelerometer.*
import org.silvermoon.devicediagnostics.R


class AccelerometerFragment : Fragment(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    lateinit var sensorValues: FloatArray

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        sensorManager = requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        return inflater.inflate(R.layout.fragment_accelerometer, container, false)
    }

    override fun onSensorChanged(p0: SensorEvent?) {

        sensorValues = p0!!.values
        val x = sensorValues[0]
        val y = sensorValues[1]
        val z = sensorValues[2]

        tvX.text = "x "+ x +" m/s^2"
        tvY.text = "x "+ y +" m/s^2"
        tvZ.text = "x "+ z +" m/s^2"

        if(x>15||y>15||z>15)
        {
            Toast.makeText(
                requireContext(),
                "Device is moved along one of it's axes.",
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun onResume() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        super.onResume()
    }


    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


}