package woowacourse.movie.view.reservation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.ReservationCount
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.view.reservation.result.ReservationResultActivity
import java.time.LocalDateTime

class ReservationHistoryFragment : Fragment() {
    val fakeReservations: List<ReservationInfo> =
        listOf(
            ReservationInfo(
                title = "미친영화",
                reservationDateTime = LocalDateTime.of(2025, 5, 12, 12, 0, 0),
                reservationCount = ReservationCount(2),
                seats = listOf(Seat(row = 1, column = 1), Seat(row = 1, column = 1)),
                cinema = Cinema(1, "선릉 극장"),
            ),
            ReservationInfo(
                title = "미친영화",
                reservationDateTime = LocalDateTime.of(2025, 5, 12, 12, 0, 0),
                reservationCount = ReservationCount(2),
                seats = listOf(Seat(row = 1, column = 1), Seat(row = 1, column = 1)),
                cinema = Cinema(1, "선릉 극장"),
            ),
            ReservationInfo(
                title = "미친영화",
                reservationDateTime = LocalDateTime.of(2025, 5, 12, 12, 0, 0),
                reservationCount = ReservationCount(2),
                seats = listOf(Seat(row = 1, column = 1), Seat(row = 1, column = 1)),
                cinema = Cinema(1, "선릉 극장"),
            ),
            ReservationInfo(
                title = "미친영화",
                reservationDateTime = LocalDateTime.of(2025, 5, 12, 12, 0, 0),
                reservationCount = ReservationCount(2),
                seats = listOf(Seat(row = 1, column = 1), Seat(row = 1, column = 1)),
                cinema = Cinema(1, "선릉 극장"),
            ),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_reservation_history, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val list = view.findViewById<RecyclerView>(R.id.rv_history_reservationInfo)
        list.adapter =
            ReservationHistoryAdapter(
                items = fakeReservations,
                onClickHistory = { reservationInfo: ReservationInfo ->
                    startActivity(
                        ReservationResultActivity.newIntent(
                            requireContext(),
                            reservationInfo = reservationInfo,
                        ),
                    )
                },
            )
    }

    override fun onResume() {
        super.onResume()
    }
}
