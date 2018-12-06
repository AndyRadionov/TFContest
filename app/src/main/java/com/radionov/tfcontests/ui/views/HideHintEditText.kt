package com.radionov.tfcontests.ui.views

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet

/**
 * @author Andrey Radionov
 */
class HideHintEditText : AppCompatEditText {

    private val initialHint: CharSequence

    constructor(context: Context) : super(context) {
        initialHint = this.hint
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialHint = this.hint
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialHint = this.hint
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)

        this.hint = if (focused) "" else initialHint
    }
}
