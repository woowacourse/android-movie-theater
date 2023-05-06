package woowacourse.app.presentation.ui.main.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.app.data.reservation.ReservationDatabase
import woowacourse.app.data.reservation.ReservationRepositoryImpl
import woowacourse.app.presentation.model.Mapper.toUiModel
import woowacourse.app.presentation.model.Mapper.toUiReservations
import woowacourse.app.presentation.ui.completed.CompletedActivity
import woowacourse.app.presentation.usecase.reservation.ReservationUseCase
import woowacourse.app.presentation.util.shortToast
import woowacourse.movie.R

class BookingHistoryFragment : Fragment(), ReservationHistoryContract.View {

    override val presenter: ReservationHistoryContract.Presenter by lazy {
        ReservationHistoryPresenter(ReservationUseCase(ReservationRepositoryImpl(ReservationDatabase)))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_booking_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerBookingHistory)
        val adapter = BookingHistoryAdapter(::itemClicked)
        recyclerView.adapter = adapter
        adapter.initList(presenter.getReservations().toUiReservations())
    }

    private fun itemClicked(id: Long) {
        val reservation = presenter.getReservation(id)
        if (reservation == null) {
            noSuchElement()
            return
        }
        val intent = CompletedActivity.getIntent(
            context = requireActivity(),
            reservation = reservation.toUiModel(),
        )
        startActivity(intent)
    }

    private fun noSuchElement() {
        requireContext().shortToast(R.string.error_no_such_reservation)
    }
}
