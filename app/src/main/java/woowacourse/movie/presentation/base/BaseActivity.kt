package woowacourse.movie.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.MessageType.AllSeatsSelectedMessage
import woowacourse.movie.presentation.model.MessageType.ReservationSuccessMessage
import woowacourse.movie.presentation.model.MessageType.TicketMaxCountMessage
import woowacourse.movie.presentation.model.MessageType.TicketMinCountMessage

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), BaseView {
    abstract val layoutResourceId: Int
    private var _binding: T? = null
    val binding
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResourceId)
        initStartView()
    }

    abstract fun initStartView()

    override fun showToastMessage(messageType: MessageType) {
        runOnUiThread { Toast.makeText(this, messageType.toMessage(), Toast.LENGTH_SHORT).show() }
    }

    override fun showToastMessage(e: Throwable) {
        runOnUiThread { Toast.makeText(this, e.toErrorMessage(), Toast.LENGTH_SHORT).show() }
    }

    override fun showSnackBar(e: Throwable) {
        Snackbar.make(
            findViewById(android.R.id.content),
            e.toErrorMessage(),
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    override fun showSnackBar(messageType: MessageType) {
        Snackbar.make(
            findViewById(android.R.id.content),
            messageType.toMessage(),
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    private fun MessageType.toMessage(): String {
        return when (this) {
            is TicketMaxCountMessage -> getString(R.string.ticke_max_count_message, this.count)
            is TicketMinCountMessage -> getString(R.string.ticke_min_count_message, this.count)
            is AllSeatsSelectedMessage -> getString(R.string.all_seats_selected_message, this.count)
            is ReservationSuccessMessage -> getString(R.string.reservation_success_message)
        }
    }

    private fun Throwable.toErrorMessage(): String {
        return when (this) {
            is NoSuchElementException -> getString(R.string.no_such_element_exception_message)
            else -> getString(R.string.unforeseen_error_message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
