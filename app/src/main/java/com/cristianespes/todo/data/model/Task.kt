package com.cristianespes.todo.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*


data class Task(
    val id: Long,
    val content: String,
    val createdAt: Date,
    val isDone: Boolean,
    val isHighPriority: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readLong(),
        content = parcel.readString()!!,
        createdAt = Date(parcel.readLong()),
        isDone = parcel.readByte() != 0.toByte(),
        isHighPriority = parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeLong(id)
        parcel.writeString(content)
        parcel.writeLong(createdAt.time)
        parcel.writeByte(if (isDone) 1 else 0)
        parcel.writeByte(if (isHighPriority) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}
