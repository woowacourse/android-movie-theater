package woowacourse.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ListView
import androidx.fragment.app.Fragment
import woowacourse.movie.domain.BookingStatus

class BookingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val bookingStatus = BookingStatus.value
        val view = inflater.inflate(R.layout.fragment_booking, container, false)
        val reservationList: ListView = view.findViewById(R.id.lv_reservation)
        val reservationListAdapter = ReservationListAdapter(bookingStatus)
        reservationList.adapter = reservationListAdapter
        return view
    }
}
