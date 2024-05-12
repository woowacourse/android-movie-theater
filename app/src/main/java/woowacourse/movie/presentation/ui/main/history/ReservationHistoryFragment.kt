package woowacourse.movie.presentation.ui.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.domain.db.AppDatabase
import woowacourse.movie.domain.db.reservationdb.ReservationDao
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ReservationRepositoryImpl
import woowacourse.movie.presentation.ui.main.history.adapter.ReservationHistoryRecyclerViewAdapter
import woowacourse.movie.presentation.ui.reservation.ReservationActivity

class ReservationHistoryFragment :
    Fragment(),
    ReservationHistoryContract.View,
    ReservationHistoryActionHandler {
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    private lateinit var reservationDao: ReservationDao

    private lateinit var reservationRepository: ReservationRepository
    private lateinit var presenter: ReservationHistoryPresenter
    private lateinit var adapter: ReservationHistoryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRepository()
        loadReservationHistory()
    }

    private fun initAdapter() {
        adapter = ReservationHistoryRecyclerViewAdapter(this)
        val decoration = DividerItemDecoration(this.context, VERTICAL)
        binding.rvReservationHistory.adapter = adapter
        binding.rvReservationHistory.addItemDecoration(decoration)
    }

    private fun initRepository() {
        context?.let { AppDatabase.getDatabase(it.applicationContext) }?.let {
            reservationDao = it.reservationDao()
            reservationRepository = ReservationRepositoryImpl(reservationDao)
        }
    }

    private fun loadReservationHistory() {
        presenter = ReservationHistoryPresenter(this, reservationRepository)
        presenter.fetchData()
    }

    override fun showReservations(reservataions: List<Reservation>) {
        activity?.runOnUiThread {
            adapter.submitList(reservataions)
        }
    }

    override fun onReservationClick(reservationId: Long) {
        this.context?.let { ReservationActivity.startActivity(it, reservationId) }
    }
}
