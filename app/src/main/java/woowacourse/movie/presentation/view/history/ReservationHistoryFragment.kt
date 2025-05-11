package woowacourse.movie.presentation.view.history

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.model.toUiModel

class ReservationHistoryFragment :
    BaseFragment<FragmentReservationHistoryBinding>(R.layout.fragment_reservation_history),
    ReservationHistoryContract.View {
    private lateinit var repository: ReservationHistoryRepository
    private val adapter = TicketBundleAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val dao = ReservationDatabase.getInstance(context).reservationDao()
        repository = ReservationHistoryRepository(dao)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovie.adapter = adapter
        showScreen()
    }

    override fun showScreen() {
        lifecycleScope.launch(Dispatchers.IO) {
            val ticketBundleUiModels = repository.getReservation().map { it.toUiModel() }
            launch(Dispatchers.Main) {
                binding.ticketBundleList = ticketBundleUiModels
            }
        }
    }
}
