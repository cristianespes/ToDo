package com.cristianespes.todo.util

import com.cristianespes.todo.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BottomSheetDialog : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.Dialog

}