package woowacourse.movie.presentation.base

import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.presentation.model.MessageType

interface BaseView {
    fun showToastMessage(messageType: MessageType)

    fun showSnackBar(messageType: MessageType)

    fun showToastMessage(e: Throwable)

    fun showSnackBar(e: Throwable)

    fun showSnackBar(
        messageType: MessageType,
        action: Snackbar.() -> Snackbar,
    )
}
