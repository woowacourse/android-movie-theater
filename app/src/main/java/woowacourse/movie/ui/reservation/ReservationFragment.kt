package woowacourse.movie.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.ui.reservation.adapter.ReservationAdapter
import woowacourse.movie.ui.ticket.MovieTicketActivity
import woowacourse.movie.uimodel.MovieTicketModel

class ReservationFragment : Fragment(), ReservationContract.View {

    override val presenter by lazy {
        ReservationPresenter(this, ReservationRepositoryImpl(requireContext()))
    }

    private lateinit var reservationView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextOnEmptyState(view)
        initAdapter(view)
    }

    override fun onResume() {
        super.onResume()
        presenter.setAdapter()
    }

    private fun initAdapter(view: View) {
        reservationView = view.findViewById(R.id.reservation_recyclerview)
        presenter.setAdapter()
    }

    override fun setReservationViewAdapter(tickets: List<MovieTicketModel>) {
        reservationView.adapter = ReservationAdapter(tickets) {
            presenter.clickItem(it)
        }
    }

    override fun clickItem(ticket: MovieTicketModel) {
        moveToMovieTicketActivity(ticket)
    }

    private fun setTextOnEmptyState(view: View) {
        if (presenter.isReservationEmpty()) {
            view.findViewById<TextView>(R.id.reservation_empty).isVisible = true
        }
    }

    private fun moveToMovieTicketActivity(ticket: MovieTicketModel) {
        startActivity(
            MovieTicketActivity.getIntent(
                ticket,
                requireContext(),
            ),
        )
    }
}
