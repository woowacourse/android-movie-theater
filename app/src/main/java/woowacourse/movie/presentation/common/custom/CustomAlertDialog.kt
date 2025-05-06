package woowacourse.movie.presentation.common.custom

import android.app.AlertDialog
import android.content.Context

class CustomAlertDialog(
    private val context: Context,
) {
    private var dialog: AlertDialog? = null
    private var currentInfo: DialogInfo? = null

    fun show(dialogInfo: DialogInfo) {
        if (dialog == null || currentInfo != dialogInfo) {
            dialog = AlertDialog.Builder(context).create(dialogInfo)
            currentInfo = dialogInfo
        }

        dialog?.show()
    }

    private fun AlertDialog.Builder.create(dialogInfo: DialogInfo): AlertDialog {
        setTitle(dialogInfo.title)
        setMessage(dialogInfo.message)
        setCancelable(dialogInfo.isCancelable)

        dialogInfo.positiveButtonText?.let { text ->
            setPositiveButton(text) { dialog, _ -> dialogInfo.onClickPositiveButton(dialog) }
        }

        dialogInfo.negativeButtonText?.let { text ->
            setNegativeButton(text) { dialog, _ -> dialogInfo.onClickNegativeButton(dialog) }
        }

        return this.create()
    }
}
