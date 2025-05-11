package woowacourse.movie.presentation.view.history.historyList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.model.TicketBundleUiModel
import woowacourse.movie.presentation.model.toUiModel
import woowacourse.movie.presentation.view.history.detailResult.ReservationResultActivity

class ReservationHistoryFragment :
    BaseFragment<FragmentReservationHistoryBinding>(R.layout.fragment_reservation_history),
    ReservationHistoryContract.View {
    private lateinit var repository: ReservationHistoryRepository
    private val adapter = TicketBundleAdapter(::moveToDetail)

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

    private fun moveToDetail(ticket: TicketBundleUiModel) {
        val intent =
            Intent(requireContext(), ReservationResultActivity::class.java).apply {
                putExtra("ticket", ticket)
            }
        startActivity(intent)
    }
}
