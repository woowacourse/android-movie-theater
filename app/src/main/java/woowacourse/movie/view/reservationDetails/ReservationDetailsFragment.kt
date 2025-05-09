package woowacourse.movie.view.reservationDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.db.AppDatabase
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.databinding.FragmentReservationDetailsBinding
import woowacourse.movie.presenter.reservationDetail.ReservationDetailsContracts
import woowacourse.movie.presenter.reservationDetail.ReservationDetailsPresenter
import woowacourse.movie.view.reservationComplete.ReservationCompleteActivity

class ReservationDetailsFragment :
    Fragment(R.layout.fragment_reservation_details),
    ReservationDetailsContracts.View {
    private var _binding: FragmentReservationDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var reservationDetailsPresenter: ReservationDetailsPresenter
    private val reservationDetailAdapter =
        ReservationDetailAdapter { navigateToReservationDetail(it) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReservationDetailsBinding.bind(view)

        binding.rvReservationDetails.adapter = reservationDetailAdapter
        reservationDetailsPresenter =
            ReservationDetailsPresenter(this, AppDatabase.getDatabase(requireContext()))
        reservationDetailsPresenter.updateReservationDetails()
    }

    private fun navigateToReservationDetail(reservationDetailId: Long) {
        startActivity(ReservationCompleteActivity.getIntent(requireContext(), reservationDetailId))
    }

    override fun showReservationDetails(reservationDetails: List<MovieTicketEntity>) {
        requireActivity().runOnUiThread {
            reservationDetailAdapter.submitList(reservationDetails)
        }
    }

    override fun onResume() {
        super.onResume()

        reservationDetailsPresenter.updateReservationDetails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
