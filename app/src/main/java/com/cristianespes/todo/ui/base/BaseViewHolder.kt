package com.cristianespes.todo.ui.base

import android.animation.ValueAnimator
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.View
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.cristianespes.todo.R
import com.cristianespes.todo.util.IconButton

abstract class BaseViewholder<TaskType>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: TaskType)

    fun applyColorToHighPriority(view: IconButton, isHighPriority: Boolean) {
        if (isHighPriority) {
            view.setColorDrawable(Color.RED, R.drawable.ic_high_priority)
        } else {
            view.setColorDrawable(Color.WHITE, R.drawable.ic_low_priority)
        }
    }

    fun executeAnimation(textContent: TextView, isDone: Boolean) {
        //val textContent = view.findViewById<TextView>(R.id.textContent)
        val content = textContent.text.toString()

        if (isDone) {
            applyStrikethrough(textContent, content, animate=true)
        } else {
            removeStrikethrough(textContent, content, animate=true)
        }
    }

    fun applyStrikethrough(view: TextView, content: String, animate: Boolean = false) {
        val span = SpannableString(content)
        val spanStrike = StrikethroughSpan()

        if (animate) {
            ValueAnimator.ofInt(content.length).apply {
                duration = 300
                interpolator = FastOutSlowInInterpolator()
                addUpdateListener { newValue ->
                    span.setSpan(spanStrike, 0, newValue.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    view.text = span
                }
            }.start()

        } else {
            span.setSpan(spanStrike, 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            view.text = span
        }
    }

    fun removeStrikethrough(view: TextView, content: String, animate: Boolean = false) {
        val span = SpannableString(content)
        val spanStrike = StrikethroughSpan()

        if (animate) {
            ValueAnimator.ofInt(content.length, 0).apply {
                duration = 300
                interpolator = FastOutSlowInInterpolator()
                addUpdateListener { newValue ->
                    span.setSpan(spanStrike, 0, newValue.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    view.text = span
                }
            }.start()

        } else {
            view.text = content
        }
    }
}
