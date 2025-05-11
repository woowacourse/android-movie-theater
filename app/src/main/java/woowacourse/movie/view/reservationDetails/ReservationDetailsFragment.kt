package woowacourse.movie.view.reservationDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationDetailsBinding
import woowacourse.movie.model.ticket.MovieTicket
import woowacourse.movie.presenter.reservationDetails.ReservationDatabase
import woowacourse.movie.presenter.reservationDetails.ReservationDetailContracts
import woowacourse.movie.presenter.reservationDetails.ReservationDetailPresenter
import woowacourse.movie.view.reservationComplete.ReservationCompleteActivity.Companion.getIntent

class ReservationDetailsFragment :
    Fragment(),
    ReservationDetailContracts.View {
    private lateinit var binding: FragmentReservationDetailsBinding
    private lateinit var reservationDetailAdapter: ReservationDetailAdapter
    private val db by lazy { ReservationDatabase.getDatabase(requireContext()) }
    private val dao by lazy { db.reservationDao() }
    private lateinit var presenter: ReservationDetailPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_reservation_details,
                container,
                false,
            )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ReservationDetailPresenter(this, dao)
        reservationDetailAdapter =
            ReservationDetailAdapter(reservationDetailClickListener())
        binding.rvReservationDetails.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReservationDetails.adapter = reservationDetailAdapter
        presenter.loadReservations()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadReservations()
    }

    override fun showReservations(reservations: List<MovieTicket>) {
        requireActivity().runOnUiThread {
            reservationDetailAdapter.submitList(reservations)
        }
    }

    override fun showReservationCompleteView(movieTicket: MovieTicket) {
        requireActivity().runOnUiThread {
            startActivity(getIntent(requireContext(), movieTicket))
        }
    }

    private fun reservationDetailClickListener() =
        object : ReservationDetailClickListener {
            override fun onReservationClick(ticketId: Long) {
                presenter.requestReservationComplete(ticketId)
            }
        }
}
