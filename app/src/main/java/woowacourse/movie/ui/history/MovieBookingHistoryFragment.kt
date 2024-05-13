package woowacourse.movie.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.databinding.FragmentMovieBookingHistoryBinding
import woowacourse.movie.domain.UserTicket
import woowacourse.movie.ui.complete.MovieReservationCompleteActivity
import woowacourse.movie.ui.complete.MovieReservationCompleteKey.TICKET_ID

class MovieBookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View,
    BookingHistoryAdapter.BookingHistoryClickListener {
    private var _binding: FragmentMovieBookingHistoryBinding? = null
    private val binding: FragmentMovieBookingHistoryBinding
        get() = _binding!!
    private lateinit var historyContents: List<UserTicket>
    private val adapter: BookingHistoryAdapter by lazy { generateBookingHistoryAdapter() }
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
        binding.rvBookingHistory.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showHistoryItems(items: List<UserTicket>) {
        historyContents = items
    }

    private fun generateBookingHistoryAdapter(): BookingHistoryAdapter =
        BookingHistoryAdapter(this).apply { submitList(historyContents) }

    override fun showError(throwable: Throwable) {
        Toast.makeText(requireContext(), getString(R.string.toast_invalid_key), Toast.LENGTH_SHORT)
            .show()
    }

    override fun onBookingHistoryClick(ticketId: Long) {
        Intent(requireContext(), MovieReservationCompleteActivity::class.java)
            .putExtra(TICKET_ID, ticketId)
            .also(::startActivity)
    }
}
