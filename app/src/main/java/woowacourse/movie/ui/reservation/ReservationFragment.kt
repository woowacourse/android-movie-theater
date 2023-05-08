package woowacourse.movie.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.databinding.FragmentReservationBinding
import woowacourse.movie.ui.reservation.adapter.ReservationAdapter
import woowacourse.movie.ui.ticket.MovieTicketActivity
import woowacourse.movie.uimodel.MovieTicketModel

class ReservationFragment : Fragment(), ReservationContract.View {

    override val presenter by lazy {
        ReservationPresenter(this, ReservationRepositoryImpl(requireContext()))
    }

    private lateinit var binding: FragmentReservationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextOnEmptyState()
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        presenter.setAdapter()
    }

    private fun initAdapter() {
        presenter.setAdapter()
    }

    override fun setReservationViewAdapter(tickets: List<MovieTicketModel>) {
        binding.reservationRecyclerview.adapter = ReservationAdapter(tickets) {
            presenter.clickItem(it)
        }
    }

    override fun clickItem(ticket: MovieTicketModel) {
        moveToMovieTicketActivity(ticket)
    }

    private fun setTextOnEmptyState() {
        if (presenter.isReservationEmpty()) {
            binding.reservationEmpty.isVisible = true
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
