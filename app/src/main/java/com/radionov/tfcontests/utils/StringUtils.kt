package com.radionov.tfcontests.utils

import android.support.v4.text.HtmlCompat

/**
 * @author Andrey Radionov
 */
fun stripHtml(html: String): String {
    val text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    return text.toString().replace("\n", "").trim()
}
