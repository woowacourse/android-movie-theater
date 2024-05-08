package woowacourse.movie.presentation.message

import android.view.View
import woowacourse.movie.presentation.model.MessageType

interface Message {
    fun showToast(message: String)

    fun showToast(messageType: MessageType)

    fun showToast(e: Throwable)

    fun showSnackBar(
        view: View,
        message: String,
    )

    fun showSnackBar(
        view: View,
        messageType: MessageType,
    )

    fun showSnackBar(
        view: View,
        e: Throwable,
    )
}
