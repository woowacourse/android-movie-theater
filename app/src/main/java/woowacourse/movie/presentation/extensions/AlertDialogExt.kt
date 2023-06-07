package woowacourse.movie.presentation.extensions

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import woowacourse.movie.R

fun AlertDialog.Builder.title(text: String) {
    this.setTitle(text)
}

fun AlertDialog.Builder.message(text: String) {
    this.setMessage(text)
}

fun AlertDialog.Builder.positiveButton(
    text: String = context.getString(R.string.yes),
    onClick: (dialogInterface: DialogInterface) -> Unit = {},
) {
    this.setPositiveButton(text) { dialogInterface, _ -> onClick(dialogInterface) }
}

fun AlertDialog.Builder.negativeButton(
    text: String = context.getString(R.string.no),
    onClick: (dialogInterface: DialogInterface) -> Unit = {},
) {
    this.setNegativeButton(text) { dialogInterface, _ -> onClick(dialogInterface) }
}
