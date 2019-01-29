package com.cristianespes.todo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.ListAdapter
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.ui.base.BaseViewholder
import kotlinx.android.synthetic.main.item_subtask.view.*

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

