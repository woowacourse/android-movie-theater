package woowacourse.app.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.shortToast(@StringRes id: Int) {
    Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
