package woowacourse.movie.view.main.reservationlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.model.reservation.ReservationInfo
import woowacourse.movie.view.main.reservationlist.adapter.ReservationListAdapter
import woowacourse.movie.view.reservation.complete.ReservationCompleteActivity
import woowacourse.movie.view.util.ExceptionMessages
import woowacourse.movie.view.util.Extras

class ReservationListFragment :
    Fragment(),
    ReservationListContract.View {
    @Suppress("ktlint:standard:backing-property-naming")
    private var _binding: FragmentReservationListBinding? = null
    private val binding: FragmentReservationListBinding
        get() =
            _binding
                ?: throw IllegalStateException(ExceptionMessages.FRAGMENT_BINDING_STATE_EXCEPTION)
    private lateinit var presenter: ReservationListContract.Presenter

    private val reservationListAdapter: ReservationListAdapter by lazy {
        ReservationListAdapter { reservationInfo ->
            val intent =
                Intent(requireContext(), ReservationCompleteActivity::class.java).apply {
                    putExtra(Extras.ReservationInfoData.RESERVATION_KEY, reservationInfo)
                }
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentReservationListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.reservationListLayout.apply {
            adapter = reservationListAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        presenter = ReservationListPresenter(this, requireContext())
        presenter.onViewCreated()
    }

    override fun showReservationInfos(reservationInfos: List<ReservationInfo>) {
        Log.d("ReservationListFragment", "showReservationInfos: $reservationInfos")
        reservationListAdapter.submitList(reservationInfos)
    }
}
