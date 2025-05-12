package woowacourse.movie.view.reservation

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class ShowNotificationPermissionInfoDialog(
    context: Context,
) {
    private val builder = AlertDialog.Builder(context)

    operator fun invoke(onDismiss: () -> Unit) {
        builder
            .setTitle(TITLE)
            .setMessage(MESSAGE)
            .setPositiveButton(
                BUTTON_TEXT,
                DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() },
            ).setOnDismissListener {
                onDismiss()
            }.show()
    }

    companion object {
        private const val TITLE = "푸시 알림 권한을 허용해주세요."
        private const val MESSAGE = "권한을 허용하시면 영화 시작 시간 30분 전에 알려드려요!"
        private const val BUTTON_TEXT = "확인"
    }
}
