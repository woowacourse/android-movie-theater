package woowacourse.movie.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieApplication
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.purchaseconfirmation.PurchaseConfirmationActivity
import woowacourse.movie.reservationlist.uimodel.ReservationUiModel
import woowacourse.movie.util.buildFetchAllReservationsUseCase

class ReservationListFragment : Fragment(), ReservationListContract.View, AdapterClickListener {
    private var _binding: FragmentReservationListBinding? = null
    private lateinit var presenter: ReservationListContract.Presenter
    private lateinit var reservationAdapter: ReservationAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationListBinding.inflate(inflater, container, false)
        val adapter = ReservationAdapter(listOf(), this)
        reservationAdapter = adapter
        binding.rcvReservations.adapter = reservationAdapter
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val db = (requireActivity().application as MovieApplication).db
        val fetchAllReservationsUseCase = buildFetchAllReservationsUseCase(db)
        presenter = ReservationListPresenter(this, fetchAllReservationsUseCase)
        presenter.loadContent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showContent(reservationUiModels: List<ReservationUiModel>) {
        reservationAdapter.updateData(reservationUiModels)
    }

    override fun navigateToReservationDetail(reservationId: Long) {
        val intent = PurchaseConfirmationActivity.getIntent(requireContext(), reservationId)
        startActivity(intent)
    }

    override fun onClick(id: Long) {
        presenter.selectReservation(id)
    }
}
