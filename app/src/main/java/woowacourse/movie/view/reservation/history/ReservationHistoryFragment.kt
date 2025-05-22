package woowacourse.movie.view.reservation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.view.reservation.result.ReservationResultActivity

class ReservationHistoryFragment :
    Fragment(),
    ReservationHistoryContract.View {
    private val presenter: ReservationHistoryContract.Presenter by lazy {
        ReservationHistoryPresenter.newInstance(this)
    }

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

        presenter.fetchReservationHistory()
    }

    override fun showReservationHistory(reservationInfos: List<ReservationInfo>) {
        requireActivity().runOnUiThread {
            val list = requireActivity().findViewById<RecyclerView>(R.id.rv_history_reservationInfo)
            list.adapter =
                ReservationHistoryAdapter(
                    items = reservationInfos,
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
