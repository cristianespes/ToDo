package com.cristianespes.todo.ui.adapter

import android.animation.ValueAnimator
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.base.BaseViewholder
import kotlinx.android.synthetic.main.item_subtask.view.*

/*class SubtaskAdapter(val listener: Listener) : ListAdapter<Subtask, SubtaskAdapter.SubtaskViewHolder>(SubtaskDiffUtil.getInstance()) {

    interface Listener {
        fun onSubtaskClicked(subtask: Subtask)
        fun onSubtaskMarked(subtask: Subtask, isDone: Boolean)
        fun onSubtaskLongClicked(subtask: Subtask)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtaskAdapter.SubtaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subtask, parent, false)
        return SubtaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubtaskAdapter.SubtaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class SubtaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(subtask: Subtask) {
            with(itemView) {
                if (subtask.isDone) {
                    applyStrikethrough(subtaskContent, subtask.content)
                } else {
                    removeStrikethrough(subtaskContent, subtask.content)
                }

                checkIsDone.isChecked = subtask.isDone

                setOnClickListener {
                    listener.onSubtaskClicked(subtask)
                }

                setOnLongClickListener {
                    listener.onSubtaskLongClicked(subtask)
                    true
                }

                checkIsDone.setOnClickListener {
                    val isChecked = (it as CheckBox).isChecked

                    listener.onSubtaskMarked(subtask, isChecked)

                    it.animate()
                        .rotationBy(360f)
                        .setDuration(300)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .start()

                    executeAnimation(itemView, isChecked)
                }

            }
        }

        private fun executeAnimation(view: View, isDone: Boolean) {
            val textContent = view.findViewById<TextView>(R.id.subtaskContent)
            val content = textContent.text.toString()

            if (isDone) {
                applyStrikethrough(textContent, content, animate=true)
            } else {
                removeStrikethrough(textContent, content, animate=true)
            }
        }

        private fun applyStrikethrough(view: TextView, content: String, animate: Boolean = false) {
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

        private fun removeStrikethrough(view: TextView, content: String, animate: Boolean = false) {
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

}*/

class SubtaskAdapter(val listener: Listener) : ListAdapter<Subtask, SubtaskAdapter.SubtaskViewHolder>(SubtaskDiffUtil.getInstance()) {

    interface Listener {
        fun onSubtaskClicked(subtask: Subtask)
        fun onSubtaskMarked(subtask: Subtask, isDone: Boolean)
        fun onSubtaskLongClicked(subtask: Subtask)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtaskAdapter.SubtaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subtask, parent, false)
        return SubtaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubtaskAdapter.SubtaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class SubtaskViewHolder(view: View) : BaseViewholder<Subtask>(view) {

        override fun bind(item: Subtask) {
            with(itemView) {
                if (item.isDone) {
                    applyStrikethrough(subtaskContent, item.content)
                } else {
                    removeStrikethrough(subtaskContent, item.content)
                }

                checkIsDone.isChecked = item.isDone

                setOnClickListener {
                    listener.onSubtaskClicked(item)
                }

                setOnLongClickListener {
                    listener.onSubtaskLongClicked(item)
                    true
                }

                checkIsDone.setOnClickListener {
                    val isChecked = (it as CheckBox).isChecked

                    listener.onSubtaskMarked(item, isChecked)

                    it.animate()
                        .rotationBy(360f)
                        .setDuration(300)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .start()

                    val textContent = itemView.findViewById<TextView>(R.id.subtaskContent)
                    executeAnimation(textContent, isChecked)
                }

            }
        }

    }

}

