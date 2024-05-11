package woowacourse.movie.presentation.reservation

import android.os.Bundle
import android.view.View
import woowacourse.movie.MovieReservationApp
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationBinding
import woowacourse.movie.model.Reservation
import woowacourse.movie.presentation.base.BindingFragment
import woowacourse.movie.presentation.error.ErrorActivity
import woowacourse.movie.presentation.purchaseConfirmation.PurchaseConfirmationActivity

class ReservationFragment :
    BindingFragment<FragmentReservationBinding>(R.layout.fragment_reservation),
    ReservationContract.View {
    private val repository by lazy {
        (requireActivity().applicationContext as MovieReservationApp).movieRepository
    }
    private val presenter by lazy {
        ReservationPresenter(repository, this)
    }
    private lateinit var adapter: ReservationAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.loadReservations()
    }

    override fun showReservations(reservations: List<Reservation>) {
        requireActivity().runOnUiThread {
            adapter.updateItems(reservations)
        }
    }

    override fun navigateToConfirmPurchaseView(reservationId: Long) {
        val intent = PurchaseConfirmationActivity.newIntent(requireContext(), reservationId)
        startActivity(intent)
    }

    override fun showError() {
        ErrorActivity.start(requireContext())
    }

    private fun initViews() {
        adapter = ReservationAdapter { presenter.findReservation(it) }
        binding.rvReservationMovies.adapter = adapter
    }
}
