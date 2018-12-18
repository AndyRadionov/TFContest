package com.radionov.tfcontests.ui.views

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet

/**
 * @author Andrey Radionov
 */
class HideHintEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val initialHint: CharSequence = this.hint

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)

        this.hint = if (focused) "" else initialHint
    }
}
