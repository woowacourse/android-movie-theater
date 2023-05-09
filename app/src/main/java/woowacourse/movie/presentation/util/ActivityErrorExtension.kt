package woowacourse.movie.presentation.util

import android.app.Activity
import android.widget.Toast

fun Activity.noIntentExceptionHandler(errorMessage: String) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    finish()
}
