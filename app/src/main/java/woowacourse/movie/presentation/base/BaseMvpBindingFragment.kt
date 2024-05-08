package woowacourse.movie.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.presentation.model.MessageType

abstract class BaseMvpBindingFragment<T : ViewDataBinding> : Fragment(), BaseView {
    abstract val layoutResourceId: Int
    private var _binding: T? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initStartView()
    }

    abstract fun initStartView()

    override fun showToastMessage(messageType: MessageType) {
        Toast.makeText(requireActivity(), messageType.toMessage(), Toast.LENGTH_SHORT).show()
    }

    override fun showToastMessage(e: Throwable) {
        Toast.makeText(requireActivity(), e.toErrorMessage(), Toast.LENGTH_SHORT).show()
    }

    override fun showSnackBar(e: Throwable) {
        Snackbar.make(
            binding.root,
            e.toErrorMessage(),
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    override fun showSnackBar(messageType: MessageType) {
        Snackbar.make(
            binding.root,
            messageType.toMessage(),
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    private fun MessageType.toMessage(): String {
        return when (this) {
            is MessageType.TicketMaxCountMessage ->
                getString(
                    R.string.ticke_max_count_message,
                    this.count,
                )

            is MessageType.TicketMinCountMessage ->
                getString(
                    R.string.ticke_min_count_message,
                    this.count,
                )

            is MessageType.AllSeatsSelectedMessage ->
                getString(
                    R.string.all_seats_selected_message,
                    this.count,
                )

            is MessageType.ReservationSuccessMessage -> getString(R.string.reservation_success_message)
            is MessageType.NotificationFailureMessage -> getString(R.string.notification_failure_message)
            is MessageType.NotificationSuccessMessage -> getString(R.string.notification_success_message)
        }
    }

    private fun Throwable.toErrorMessage(): String {
        return when (this) {
            is NoSuchElementException -> getString(R.string.no_such_element_exception_message)
            else -> getString(R.string.unforeseen_error_message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
