package es.iessaladillo.pedrojoya.baldogym.utils

import android.view.View

fun View.invisibleUnless(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.INVISIBLE
}