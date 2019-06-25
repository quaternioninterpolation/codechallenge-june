package joshvdh.com.codechallenge_june.AnimatedImageView

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

class AnimatedImageView(
    context: Context,
    private val animatedDrawable: AnimatedVectorDrawable
) : AppCompatImageView(context) {

    init {
        val a : AnimatedVectorDrawable
        a.
    }

    constructor(context: Context, @DrawableRes animatedDrawableRes: Int) : this(
        context,
        context.resources.getDrawable(animatedDrawableRes) as AnimatedVectorDrawable
    )

    fun reverse() {
        animatedDrawable
        animatedDrawable.start()
    }
}