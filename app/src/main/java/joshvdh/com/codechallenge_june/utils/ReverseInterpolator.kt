package joshvdh.com.codechallenge_june.utils

import android.view.animation.Interpolator

open class ReverseInterpolator(private val target: Interpolator) : Interpolator {
    override fun getInterpolation(input: Float) = target.getInterpolation(1f - input)
}

fun Interpolator.reversed() = ReverseInterpolator(this)