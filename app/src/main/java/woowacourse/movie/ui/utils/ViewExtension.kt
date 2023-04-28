package woowacourse.movie.ui.utils

import android.view.View
import android.widget.Toast
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

fun View.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
