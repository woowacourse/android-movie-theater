package woowacourse.movie.reservationfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.reservationfragment.ReservationListAdapter
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.moviebooked.MovieBookedActivity

class ReservationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val bookingStatus = BookingStatus.Companion.value
        val view = inflater.inflate(R.layout.fragment_booking, container, false)
        val reservationList: ListView = view.findViewById(R.id.lv_reservation)
        val reservationListAdapter = ReservationListAdapter(
            bookingStatus
        ) { bookingStatus -> navigateToBooked(bookingStatus) }
        reservationList.adapter = reservationListAdapter
        return view
    }

    private fun navigateToBooked(bookingStatus: BookingStatus) {
        val intent =
            MovieBookedActivity.Companion.movieBookedIntent(requireContext(), bookingStatus)
        startActivity(intent)
    }
}
