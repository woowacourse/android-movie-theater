package woowacourse.movie.feature.bookinghistory.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.room.Room
import woowacourse.movie.R
import woowacourse.movie.data.database.BookingDatabase
import woowacourse.movie.data.database.BookingDatabase.Companion.DATABASE_NAME
import woowacourse.movie.data.mapper.toData
import woowacourse.movie.data.mapper.toDomain
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.domain.model.BookingInfo
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieSeats
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.domain.model.TicketCount
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.bookinghistory.view.adapter.BookingHistoryAdapter
import woowacourse.movie.feature.bookinghistory.view.adapter.BookingHistoryAdapter.Handler
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.model.BookingInfoUiModel
import java.time.LocalDate
import java.time.LocalTime

class BookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View {
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
                seats = MovieSeats(),
                ticketCount = TicketCount(20),
            )
        val database =
            Room
                .databaseBuilder(requireActivity().applicationContext, BookingDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        val dao = database.bookingDao()

        dao.insertAll(bookingInfo.toData())
        bookingHistoryAdapter = BookingHistoryAdapter(dao.getAll().map { it.toDomain().toUi() }, setupAdapterClickListener())
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

    private fun setupAdapterClickListener() =
        object : Handler {
            override fun onBookingHistoryClick(bookingInfo: BookingInfoUiModel) {
                val intent = BookingCompleteActivity.newIntent(requireContext(), bookingInfo)
                startActivity(intent)
            }
        }
}
