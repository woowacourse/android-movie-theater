package woowacourse.movie.presentation.message

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.presentation.model.message.MessageType

class AndroidMessage(private val context: Context) : Message {
    private var toast: Toast? = null
    private var snackbar: Snackbar? = null

    override fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun showToast(messageType: MessageType) {
        showToast(messageType.toMessage(context))
    }

    override fun showToast(e: Throwable) {
        showToast(e.toErrorMessage())
    }

    override fun showSnackBar(
        view: View,
        message: String,
    ) {
        snackbar?.dismiss()
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar?.show()
    }

    override fun showSnackBar(
        view: View,
        messageType: MessageType,
    ) {
        showSnackBar(view, messageType.toMessage(context))
    }

    override fun showSnackBar(
        view: View,
        messageType: MessageType,
        action: Snackbar.() -> Snackbar,
    ) {
        snackbar?.dismiss()
        snackbar =
            Snackbar.make(view, messageType.toMessage(context), Snackbar.LENGTH_SHORT).action()
        snackbar?.show()
    }

    override fun showSnackBar(
        view: View,
        e: Throwable,
    ) {
        showSnackBar(view, e.toErrorMessage())
    }

    private fun Throwable.toErrorMessage(): String {
        return when (this) {
            is NoSuchElementException -> context.getString(R.string.no_such_element_exception_message)
            else -> context.getString(R.string.unforeseen_error_message)
        }
    }
}
