package org.silvermoon.devicediagnostics.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.silvermoon.devicediagnostics.R
import org.silvermoon.devicediagnostics.ui.slideshow.SlideshowViewModel
import org.silvermoon.devicediagnostics.views.TouchEventView

class TouchSensorFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel::class.java)

        return TouchEventView(container!!.context, null)
    }
}