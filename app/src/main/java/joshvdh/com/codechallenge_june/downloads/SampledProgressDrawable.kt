package joshvdh.com.codechallenge_june.downloads

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import java.util.*

/**
 *  Created by Josh van den Heever on 2019-06-26.
 */
class SampledProgressDrawable(
    private val maxDuration: Long,
    private val sampleRate: Int,
    @ColorInt lineColor: Int,
    @ColorInt shadowColor: Int,
    private val topBoundOffsetPercent: Float = 0.75f,
    private val bottomBoundOffsetPercent: Float = 0.93f,
    private val stateTransitionDuration: Long = 1_000L
) : Drawable() {

    private val maxSamples = (maxDuration * sampleRate).toInt()
    private val sampleQueue = ArrayDeque<Float>(maxSamples)
    private val curPoints = FloatArray(maxSamples)
    //TODO: Drawn points
    private var progress = 0f

    private var lastTime = System.currentTimeMillis()
    private var transitionInterpolator: ProgressInterpolator? = null
    private var isPlaying = false

    private val linePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        color = lineColor
    }

    private val shadowPaint = Paint().apply {
        style = Paint.Style.FILL
        color = shadowColor
    }

    override fun draw(canvas: Canvas) {
        val deltaTime = (System.currentTimeMillis() - lastTime) / 1000f
        lastTime = System.currentTimeMillis()

        //Update points
        updatePoints()

        //Draw line
        canvas.apply {
            //TODO: Draw shadow

            drawLines(sampleQueue.toFloatArray(), linePaint)
        }

        invalidateSelf()
    }

    private fun updatePoints() {
        val multiplier = transitionInterpolator?.let { interpolator ->
            //Interpolate!
            if (isPlaying)
                interpolator.getProgress()
            else
                1f - interpolator.getProgress()
        } ?: 1f

        if (transitionInterpolator?.isComplete() == true)
            transitionInterpolator = null

        sampleQueue.forEachIndexed { index, sample ->
            curPoints[index] = sample * multiplier
        }
    }

    private fun getCanvasPoints(canvasWidth: Float) = ArrayList<Float>().apply {
        //Build points array (X, Y, X, Y etc)
        sampleQueue.forEachIndexed { index, sample ->
            val
            add(sample)
        }
    }.toFloatArray()

    override fun setAlpha(alpha: Int) {
        linePaint.alpha = alpha
    }

    override fun getOpacity() = resolveOpacity(linePaint.alpha, shadowPaint.alpha)

    override fun setColorFilter(colorFilter: ColorFilter?) {
        //Stub
    }

    fun addSample(sample: Float, progressPercent: Float) {
        sampleQueue.apply {
            push(sample)
            if (size > maxSamples) {
                pop()
            }
        }
    }

    fun setPlaying(playing: Boolean) {
        isPlaying = playing

        transitionInterpolator = ProgressInterpolator(stateTransitionDuration / 1000f)
    }

    inner class ProgressInterpolator(private val duration: Float) {
        private var curTime = 0f

        fun reset() {
            curTime = 0f
        }

        fun getProgress() = curTime / duration

        fun update(deltaTime: Float) {
            curTime += deltaTime
        }

        fun isComplete() = getProgress() >= 1f
    }
}