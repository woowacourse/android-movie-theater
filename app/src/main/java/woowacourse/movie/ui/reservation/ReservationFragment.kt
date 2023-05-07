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
import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.ui.reservation.adapter.ReservationAdapter
import woowacourse.movie.ui.ticket.MovieTicketActivity

class ReservationFragment : Fragment() {
    private lateinit var reservationView: RecyclerView
    private val repository: ReservationRepository by lazy {
        ReservationRepositoryImpl(requireContext())
    }

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
        setReservationViewAdapter()
    }

    private fun initAdapter(view: View) {
        reservationView = view.findViewById(R.id.reservation_recyclerview)
        setReservationViewAdapter()
    }

    private fun setReservationViewAdapter() {
        reservationView.adapter = ReservationAdapter(repository.getData()) {
            moveToMovieTicketActivity(it)
        }
    }

    private fun setTextOnEmptyState(view: View) {
        if (repository.getData().isNotEmpty()) {
            view.findViewById<TextView>(R.id.reservation_empty).isVisible = false
        }
    }

    private fun moveToMovieTicketActivity(position: Int) {
        startActivity(
            MovieTicketActivity.getIntent(
                repository.getData()[position],
                requireContext(),
            ),
        )
    }
}
