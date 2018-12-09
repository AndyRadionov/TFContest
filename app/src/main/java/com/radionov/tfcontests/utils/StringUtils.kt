package com.radionov.tfcontests.utils

import android.text.Html

/**
 * @author Andrey Radionov
 */
fun stripHtml(html: String): String {
    val text =  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }

    return text.toString().replace("\n", "").trim()
}