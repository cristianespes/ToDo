package com.cristianespes.todo.util

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.TintableBackgroundView
import com.cristianespes.todo.R

class IconButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    deffStyleAttr: Int = 0
): AppCompatImageButton(context, attributeSet, deffStyleAttr), TintableBackgroundView {

    private var touchableArea = 8

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        changeTouchableArea(touchableArea)
    }

    fun setColorDrawable(color: Int, drawable: Int) {
        val newDrawable = ContextCompat.getDrawable(context, drawable)

        newDrawable?.let {
            val finalDrawable = DrawableCompat.wrap(it)
            DrawableCompat.setTint(finalDrawable.mutate(), color)
            setImageDrawable(finalDrawable)
        }
    }

}