package woowacourse.movie.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieApplication
import woowacourse.movie.data.movie.MovieRepositoryImpl
import woowacourse.movie.data.reservationref.ReservationRefRepositoryImpl
import woowacourse.movie.data.screeningref.ScreeningRefRepositoryImpl
import woowacourse.movie.data.theater.TheaterRepositoryImpl
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.purchaseconfirmation.PurchaseConfirmationActivity
import woowacourse.movie.reservationlist.uimodel.ReservationUiModel
import woowacourse.movie.usecase.FetchAllReservationsUseCase
import woowacourse.movie.usecase.FetchScreeningWithIdUseCase

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
        val fetchAllReservationsUseCase = buildFetchAllReservationsUseCase()
        presenter = ReservationListPresenter(this, fetchAllReservationsUseCase)
        presenter.loadContent()
    }

    private fun buildFetchAllReservationsUseCase(): FetchAllReservationsUseCase {
        val db = (requireActivity().application as MovieApplication).db
        val reservationRefRepository = ReservationRefRepositoryImpl(db.reservationDao())
        val movieRepository = MovieRepositoryImpl(db.movieDao())
        val theaterRepository = TheaterRepositoryImpl(db.theaterDao())
        val screeningRefRepository = ScreeningRefRepositoryImpl(db.screeningDao())
        val screeningWithIdUseCase =
            FetchScreeningWithIdUseCase(
                movieRepository,
                theaterRepository,
                screeningRefRepository,
            )
        return FetchAllReservationsUseCase(reservationRefRepository, screeningWithIdUseCase)
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
