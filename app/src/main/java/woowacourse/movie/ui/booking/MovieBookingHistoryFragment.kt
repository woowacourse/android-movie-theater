package woowacourse.movie.ui.booking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieBookingHistoryBinding
import woowacourse.movie.model.db.UserTicket
import woowacourse.movie.model.db.UserTicketRepositoryImpl
import woowacourse.movie.ui.booking.adapter.MovieBookingHistoryAdapter
import woowacourse.movie.ui.complete.MovieReservationCompleteActivity

class MovieBookingHistoryFragment : Fragment(), MovieBookingHistoryContract.View {
    private lateinit var binding: FragmentMovieBookingHistoryBinding
    private val adapter by lazy { movieBookingHistoryAdapter() }
    private val presenter: MovieBookingHistoryPresenter by lazy {
        MovieBookingHistoryPresenter(this, UserTicketRepositoryImpl.get())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_movie_booking_history,
                container,
                false,
            )
        presenter.loadBookingHistories()
        return binding.root
    }

    override fun showBookingHistories(bookingHistories: List<UserTicket>) {
        adapter.submitList(bookingHistories)
        binding.bookedMovieList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL,
            ),
        )
        binding.bookedMovieList.adapter = adapter
    }

    private fun movieBookingHistoryAdapter() =
        MovieBookingHistoryAdapter { userTicketId ->
            Intent(activity, MovieReservationCompleteActivity::class.java).apply {
                putExtra(MovieBookingHistoryKey.TICKET_ID, userTicketId)
                startActivity(this)
            }
        }
}
