package woowacourse.movie.presentation.message

import android.view.View
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.presentation.model.MessageType

object Messenger {
    private lateinit var message: Message

    fun init(message: Message) {
        this.message = message
    }

    fun showToast(message: String) = this.message.showToast(message)

    fun showToast(e: Throwable) = this.message.showToast(e)

    fun showToast(messageType: MessageType) = this.message.showToast(messageType)

    fun showSnackBar(
        view: View,
        message: String,
    ) = this.message.showSnackBar(view, message)

    fun showSnackBar(
        view: View,
        e: Throwable,
    ) = this.message.showSnackBar(view, e)

    fun showSnackBar(
        view: View,
        messageType: MessageType,
    ) = this.message.showSnackBar(view, messageType)

    fun showSnackBar(
        view: View,
        messageType: MessageType,
        action: Snackbar.() -> Snackbar,
    ) = this.message.showSnackBar(view, messageType, action)
}
