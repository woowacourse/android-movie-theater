package woowacourse.movie.feature.bookinghistory.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.domain.model.BookingInfo
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieSeat
import woowacourse.movie.domain.model.MovieSeats
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.domain.model.TicketCount
import woowacourse.movie.domain.repository.BookingRepository
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.bookinghistory.presenter.BookingHistoryPresenter
import woowacourse.movie.feature.bookinghistory.view.adapter.BookingHistoryAdapter
import woowacourse.movie.feature.bookinghistory.view.adapter.BookingHistoryAdapter.Handler
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.model.BookingInfoUiModel
import java.time.LocalDate
import java.time.LocalTime

class BookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View {
    private val bookingRepository: BookingRepository by lazy {
        (requireActivity().application as MovieApplication).bookingRepository
    }
    private val presenter: BookingHistoryContract.Presenter by lazy { BookingHistoryPresenter(this, bookingRepository) }
    private lateinit var bookingHistoryAdapter: BookingHistoryAdapter
    private lateinit var binding: FragmentBookingHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bookingInfo =
            BookingInfo(
                movie =
                    Movie(
                        id = 1,
                        title = "레디 플레이어 원",
                        startDate = MovieDate(),
                        endDate = MovieDate(),
                        runningTime = 120,
                    ),
                theaterName = "잠실",
                date = MovieDate(LocalDate.now()),
                time = MovieTime(LocalTime.now()),
                seats = MovieSeats(setOf(MovieSeat(1, 1), MovieSeat(1, 2))),
                ticketCount = TicketCount(4),
            )

        bookingRepository.insertAll(bookingInfo)
        bookingHistoryAdapter = BookingHistoryAdapter(bookingRepository.getAll().map { it.toUi() }, setupAdapterClickListener())
        presenter.getBookingHistory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booking_history, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.bookingHistoryAdapter = bookingHistoryAdapter
    }

    override fun showBookingHistory(bookingHistory: List<BookingInfoUiModel>) {
        bookingHistoryAdapter = BookingHistoryAdapter(bookingRepository.getAll().map { it.toUi() }, setupAdapterClickListener())
    }

    override fun navigateToBookingComplete(bookingInfo: BookingInfoUiModel) {
        val intent = BookingCompleteActivity.newIntent(requireContext(), bookingInfo)
        startActivity(intent)
    }

    private fun setupAdapterClickListener() =
        object : Handler {
            override fun onBookingHistoryClick(bookingInfo: BookingInfoUiModel) {
                presenter.selectBookingHistory(bookingInfo)
            }
        }
}
