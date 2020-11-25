package org.silvermoon.devicediagnostics.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.android.synthetic.main.fragment_flash.*
import kotlinx.android.synthetic.main.fragment_flash.view.*
import org.silvermoon.devicediagnostics.R


class FlashFragment : Fragment() {
   private var camera: ListenableFuture<ProcessCameraProvider>? = null
    private lateinit var cameraControl: CameraControl
    private lateinit var baseView: View
    private var isFlashOn = false
    private var hasFlash = false
    var mp: MediaPlayer? = null
    val TAG = "FlashFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        hasFlash = requireActivity().applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            val alert = AlertDialog.Builder(requireContext())
                .create()
            alert.setTitle("Error")
            alert.setMessage("Device doesn't support flashlight")
            alert.setButton(
                "OK",
                DialogInterface.OnClickListener { dialog, which -> // closing the application
                    requireActivity().finish()
                })
            alert.show()

        }

        // get the camera
        getCamera()

        // displaying button image
        //toggleButtonImage()


/*
				 * Switch button click event to toggle flash on/off
				 */


        // Inflate the layout for this fragment
        baseView = inflater.inflate(R.layout.fragment_flash, container, false)
        /*
               * Switch button click event to toggle flash on/off
               */
        baseView.btnSwitch.setOnClickListener(View.OnClickListener {
            if (isFlashOn) {
                // turn off flash
                turnOffFlash()
            } else {
                // turn on flash
                turnOnFlash()
            }
        })
        return baseView
    }


    override fun onPause() {
        super.onPause()

        turnOffFlash()
    }


//    override fun onResume() {
//        super.onResume()
//        if (hasFlash) turnOnFlash()
//    }

    override fun onStart() {
        super.onStart()
        getCamera()
    }

    override fun onStop() {
        super.onStop()
        if (camera != null) {
            //camera!!.release()
            camera = null
        }
    }

    private fun getCamera() {
        if (camera == null) {
            try {
               camera = ProcessCameraProvider.getInstance(requireContext())


                camera!!.addListener(Runnable {
                    // Used to bind the lifecycle of cameras to the lifecycle owner
                    val cameraProvider: ProcessCameraProvider = camera!!.get()

                    // Preview
                    val preview = Preview.Builder()
                        .build()
                        .also {
                            it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                        }

                    // Select back camera as a default
                    val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

                    try {
                        // Unbind use cases before rebinding
                        cameraProvider.unbindAll()

                        // Bind use cases to camera
                     cameraControl =   cameraProvider.bindToLifecycle(
                            this, cameraSelector, preview).cameraControl



                    } catch(exc: Exception) {
                        Log.e(TAG, "Use case binding failed", exc)
                    }



                }, ContextCompat.getMainExecutor(requireContext()))

            } catch (e: RuntimeException) {
                Toast.makeText(requireContext(), "Camera error", Toast.LENGTH_SHORT).show()
            }
        }

        val cameraInfo = camera



    }


    private fun turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || cameraControl == null) {
                return
            }
            cameraControl.enableTorch(true)
            isFlashOn = true


            //     toggleButtonImage()
        }
        toggleButtonImage()
    }

    private fun turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || cameraControl == null) {
                return
            }

            cameraControl.enableTorch(false)
            isFlashOn = false

              toggleButtonImage()
        }
    }


    private fun toggleButtonImage() {
        if (isFlashOn) {
            baseView.btnSwitch.setImageResource(R.drawable.switchon)
        } else {
            baseView.btnSwitch.setImageResource(R.drawable.switchoff)
        }
    }

}



