package woowacourse.movie.ui.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(message: String, buttonText: String, onButtonClick: () -> Unit) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).apply {
        setAction(buttonText) {
            onButtonClick()
        }
    }.show()
}
