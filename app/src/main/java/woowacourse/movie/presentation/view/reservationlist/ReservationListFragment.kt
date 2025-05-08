package woowacourse.movie.presentation.view.reservationlist

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.data.ReservationDatabase
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.presentation.Extras
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.view.reservation.complete.ReservationCompleteActivity

class ReservationListFragment :
    Fragment(),
    ReservationListContract.View {
    private lateinit var binding: FragmentReservationListBinding
    private val reservationDao by lazy {
        ReservationDatabase.getInstance(requireContext()).reservationDao()
    }
    private val presenter: ReservationListContract.Presenter by lazy {
        ReservationListPresenter(this, reservationDao)
    }
    private val reservationAdapter: ReservationAdapter by lazy {
        ReservationAdapter(
            object :
                ReservationClickListener {
                override fun onReservationClick(reservation: ReservationInfoUiModel) {
                    reservationSelect(reservation)
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReservationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupReservationAdapter()
        presenter.fetchReservations()
    }

    override fun showReservations(reservations: List<ReservationInfoUiModel>) {
        requireActivity().runOnUiThread {
            reservationAdapter.submitList(reservations)
        }
    }

    override fun navigateToComplete(reservation: ReservationInfoUiModel) {
        val intent =
            Intent(requireContext(), ReservationCompleteActivity::class.java).apply {
                putExtra(Extras.ReservationInfoData.RESERVATION_KEY, reservation)
            }
        startActivity(intent)
    }

    private fun setupReservationAdapter() {
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(Color.GRAY.toDrawable())
        binding.rvReservationList.addItemDecoration(dividerItemDecoration)
        binding.rvReservationList.adapter = reservationAdapter
    }

    private fun reservationSelect(reservation: ReservationInfoUiModel) {
        presenter.reservationSelected(reservation)
    }
}
