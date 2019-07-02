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

    private val maxSamples = (maxDuration / sampleRate).toInt()
    private val sampleQueue = ArrayDeque<Float>(maxSamples)
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

        transitionInterpolator?.update(deltaTime)

        //Draw line
        canvas.apply {
            //TODO: Draw shadow

            drawLines(calculateCanvasPoints(bounds.width().toFloat(), bounds.height().toFloat()), linePaint)

            //TODO: Offset matrix according to progress?
        }

        if (transitionInterpolator?.isComplete() == true)
            transitionInterpolator = null

        invalidateSelf()
    }

    private fun calculateCanvasPoints(canvasWidth: Float, canvasHeight: Float) = ArrayList<Float>().apply {
        val maxSizePercent = canvasHeight * (bottomBoundOffsetPercent - topBoundOffsetPercent)

        val multiplier = transitionInterpolator?.let { interpolator ->
            //Interpolate!
            if (isPlaying)
                interpolator.getProgress()
            else
                1f - interpolator.getProgress()
        } ?: if (isPlaying) 1f else 0f

        //Build points array (X, Y, X, Y etc)
        sampleQueue.forEachIndexed { index, sample ->
            val correctedSample = sample * multiplier

            //X
            add((index.toFloat() / sampleQueue.size.toFloat()) * canvasWidth)

            //Y
            add((correctedSample * maxSizePercent * canvasHeight) + (topBoundOffsetPercent * canvasHeight))
        }
    }.toFloatArray()

    override fun setAlpha(alpha: Int) {
        linePaint.alpha = alpha
    }

    override fun getOpacity() = resolveOpacity(linePaint.alpha, shadowPaint.alpha)

    override fun setColorFilter(colorFilter: ColorFilter?) {
        //Stub
    }

    fun addSample(sample: Float) {
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