package woowacourse.movie.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieBookingHistoryBinding
import woowacourse.movie.model.movie.MovieDatabase
import woowacourse.movie.model.movie.TicketDao
import woowacourse.movie.model.movie.TicketEntity
import woowacourse.movie.model.movie.UserTicket
import woowacourse.movie.model.movie.toUserTicket
import woowacourse.movie.ui.HandleError
import woowacourse.movie.ui.complete.MovieReservationCompleteActivity
import woowacourse.movie.ui.complete.MovieReservationCompleteKey.TICKET_ID
import kotlin.concurrent.thread

class MovieBookingHistoryFragment : Fragment(), BookingHistoryContract.View, BookingHistoryAdapter.BookingHistoryClickListener {
    private var _binding: FragmentMovieBookingHistoryBinding? = null
    private val binding: FragmentMovieBookingHistoryBinding
        get() = _binding!!
    private val ticketDao: TicketDao by lazy {
        MovieDatabase.getDatabase(requireContext()).ticketDao()
    }
    private val presenter: BookingHistoryPresenter by lazy {
        BookingHistoryPresenter(
            this,
            ticketDao,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_movie_booking_history,
                container,
                false,
            )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadHistoryItems()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showHistoryItems(items: List<UserTicket>) {
        view?.post {
            val adapter = BookingHistoryAdapter(this)
            adapter.submitList(items)
            binding.rvBookingHistory.adapter = adapter
        }
    }

    override fun showError(throwable: Throwable) {
    }

    override fun onBookingHistoryClick(ticketId: Long) {
        Intent(requireContext(), MovieReservationCompleteActivity::class.java)
            .putExtra(TICKET_ID, ticketId)
            .also(::startActivity)
    }
}

interface BookingHistoryContract {
    interface View : HandleError {
        fun showHistoryItems(items: List<UserTicket>)
    }

    interface Presenter {
        fun loadHistoryItems()
    }
}

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val ticketDao: TicketDao,
) : BookingHistoryContract.Presenter {
    override fun loadHistoryItems() {
        thread {
            val items: List<UserTicket> = ticketDao.findAll().map(TicketEntity::toUserTicket)
            view.showHistoryItems(items)
        }
    }
}
