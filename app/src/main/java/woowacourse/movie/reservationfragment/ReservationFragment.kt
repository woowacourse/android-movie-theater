package woowacourse.movie.reservationfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.dao.bookingStatus.BookingStatusDatabase
import woowacourse.movie.dao.bookingStatus.BookingStatusEntity
import woowacourse.movie.R
import woowacourse.movie.dao.bookingseats.BookingSeatDatabase
import woowacourse.movie.dao.bookingseats.BookingSeatEntity
import woowacourse.movie.databinding.FragmentBookingBinding
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.moviebooked.MovieBookedActivity
import kotlin.concurrent.thread

class ReservationFragment : Fragment() {
    private var bookingStatus: MutableList<BookingStatus> = mutableListOf()
    private lateinit var binding: FragmentBookingBinding
    private lateinit var bookingStatusEntity: List<BookingStatusEntity>
    private lateinit var bookingSeatsEntity: List<BookingSeatEntity>
    private lateinit var reservationListAdapter: ReservationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookingStatus.clear()
        thread {
            val bookingStatusDatabase = BookingStatusDatabase.database(requireContext())
            val bookingSeatDatabase = BookingSeatDatabase.database(requireContext())
            bookingStatusEntity = bookingStatusDatabase.getAll()
            val newBookingStatusList = mutableListOf<BookingStatus>()
            bookingStatusEntity.forEach { bookingStatusEntity ->
                bookingSeatsEntity = bookingSeatDatabase.get(bookingStatusEntity.id)
                val bookingStatus = BookingStatus.toDomain(bookingStatusEntity, bookingSeatsEntity)
                newBookingStatusList.add(bookingStatus)
            }

            requireActivity().runOnUiThread {
                bookingStatus.clear()
                bookingStatus.addAll(newBookingStatusList)
                reservationListAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booking, container, false)
        reservationListAdapter = ReservationListAdapter(
            bookingStatus
        ) { bookingStatus -> navigateToBooked(bookingStatus) }
        binding.lvReservation.adapter = reservationListAdapter
        return binding.root
    }

    private fun navigateToBooked(bookingStatus: BookingStatus) {
        val intent =
            MovieBookedActivity.Companion.movieBookedIntent(requireContext(), bookingStatus)
        startActivity(intent)
    }
}
