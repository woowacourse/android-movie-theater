package woowacourse.movie.view.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import woowacourse.movie.R

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun dialogMessage(
    activity: Activity,
    messageResId: Int,
    onConfirmed: () -> Unit = { activity.finish() },
) {
    AlertDialog.Builder(activity).run {
        setMessage(messageResId)
            .setCancelable(false)
            .setPositiveButton(R.string.go_back) { _, _ ->
                onConfirmed()
            }.show()
    }
}
