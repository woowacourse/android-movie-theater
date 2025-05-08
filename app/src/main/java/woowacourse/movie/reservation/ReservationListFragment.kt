package woowacourse.movie.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.booking.complete.BookingCompleteActivity
import woowacourse.movie.booking.complete.BookingType
import woowacourse.movie.data.ReservationDatabase
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.ui.model.TicketUiModel

class ReservationListFragment : Fragment(), ReservationListContract.View {
    private lateinit var presenter: ReservationListPresenter
    private lateinit var binding: FragmentReservationListBinding
    private lateinit var repository: ReservationRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reservation_list, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val db = ReservationDatabase.getInstance(requireContext())
        repository = ReservationRepository(db!!.reservationDao())
        presenter = ReservationListPresenter(this, repository)

        presenter.initializeData()
    }

    override fun showReservationList(reservations: List<TicketUiModel>) {
        binding.recyclerViewReservationList.adapter =
            ReservationListAdapter(reservations) { reservation ->
                presenter.setReservations(reservation)
            }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun startBookingCompleteActivity(reservation: TicketUiModel) {
        val intent = BookingCompleteActivity.createIntent(requireContext(), BookingType.HISTORY, reservation)
        startActivity(intent)
    }
}
