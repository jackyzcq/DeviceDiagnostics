package org.silvermoon.devicediagnostics.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent

import android.view.VelocityTracker
import android.view.View


class TouchEventView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private val paint: Paint = Paint()
    private val path: Path = Path()
    private var mVelocityTracker: VelocityTracker? = null

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventX = event.x
        val eventY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(eventX, eventY)
                mVelocityTracker = VelocityTracker.obtain()
                mVelocityTracker!!.addMovement(event)
                return true
            }
            MotionEvent.ACTION_MOVE -> path.lineTo(eventX, eventY)
            MotionEvent.ACTION_UP -> {
            }
            else -> return false
        }

        // Schedules a repaint.
        invalidate()
        return true
    }

    init {
        paint.setAntiAlias(true)
        paint.setStrokeWidth(6f)
        paint.setColor(Color.BLACK)
        paint.setStyle(Paint.Style.STROKE)
        paint.setStrokeJoin(Paint.Join.ROUND)
    }
}