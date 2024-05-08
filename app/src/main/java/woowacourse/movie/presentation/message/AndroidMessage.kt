package woowacourse.movie.presentation.message

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.presentation.model.MessageType

class AndroidMessage(private val context: Context) : Message {
    private var toast: Toast? = null
    private var snackbar: Snackbar? = null

    override fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun showToast(messageType: MessageType) {
        showToast(messageType.toMessage())
    }

    override fun showToast(e: Throwable) {
        showToast(e.toErrorMessage())
    }

    override fun showSnackBar(message: String) {
        snackbar?.dismiss()
        snackbar = Snackbar.make(View(context), message, Snackbar.LENGTH_SHORT)
        snackbar?.show()
    }

    override fun showSnackBar(messageType: MessageType) {
        showSnackBar(messageType.toMessage())
    }

    override fun showSnackBar(e: Throwable) {
        showSnackBar(e.toErrorMessage())
    }

    private fun MessageType.toMessage(): String {
        return when (this) {
            is MessageType.TicketMaxCountMessage ->
                context.getString(
                    R.string.ticke_max_count_message,
                    this.count,
                )

            is MessageType.TicketMinCountMessage ->
                context.getString(
                    R.string.ticke_min_count_message,
                    this.count,
                )

            is MessageType.AllSeatsSelectedMessage ->
                context.getString(
                    R.string.all_seats_selected_message,
                    this.count,
                )

            is MessageType.ReservationSuccessMessage -> context.getString(R.string.reservation_success_message)
            is MessageType.NotificationFailureMessage -> context.getString(R.string.notification_failure_message)
            is MessageType.NotificationSuccessMessage -> context.getString(R.string.notification_success_message)
        }
    }

    private fun Throwable.toErrorMessage(): String {
        return when (this) {
            is NoSuchElementException -> context.getString(R.string.no_such_element_exception_message)
            else -> context.getString(R.string.unforeseen_error_message)
        }
    }
}
