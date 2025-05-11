package woowacourse.movie.view.reservation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.db.ReservationInfoDatabase
import woowacourse.movie.db.ReservationInfoEntity
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.ReservationCount
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.view.reservation.result.ReservationResultActivity
import kotlin.concurrent.thread

class ReservationHistoryFragment : Fragment() {
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
        thread {
            val database = ReservationInfoDatabase.getInstance(requireContext())
            val dao = database.reservationInfoDao()
            val reservationHistories: List<ReservationInfoEntity> = dao.getAll()
            view.post {
                val list = view.findViewById<RecyclerView>(R.id.rv_history_reservationInfo)
                list.adapter =
                    ReservationHistoryAdapter(
                        items =
                            reservationHistories.map {
                                ReservationInfo(
                                    it.title,
                                    it.reservationDateTime,
                                    ReservationCount(it.reservationCount),
                                    listOf(Seat(1, 2), Seat(1, 2)),
                                    Cinema(0, "선릉 극장"),
                                )
                            },
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
        }
    }
}
