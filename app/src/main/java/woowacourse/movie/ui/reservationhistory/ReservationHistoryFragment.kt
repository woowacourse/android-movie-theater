package woowacourse.movie.ui.reservationhistory

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.room.Room
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.db.AppDatabase
import woowacourse.movie.ui.reservationhistory.adapter.ReservationHistoryAdapter
import woowacourse.movie.ui.reservationhistorydetail.ReservationHistoryDetailActivity

class ReservationHistoryFragment : Fragment() {
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding: FragmentReservationHistoryBinding
        get() = requireNotNull(_binding)
    private lateinit var reservationHistoryAdapter: ReservationHistoryAdapter

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
        val db =
            Room.databaseBuilder(
                requireContext(),
                AppDatabase::class.java,
                "reservationHistory",
            ).build()

        val thread =
            Thread {
                val reservationHistories = db.reservationHistoryDao().getAll()

                reservationHistoryAdapter = ReservationHistoryAdapter { reservationHistoryId ->
                    ReservationHistoryDetailActivity.startActivity(requireContext(), reservationHistoryId)
                }
                reservationHistoryAdapter.submitList(reservationHistories)

                binding.adapter = reservationHistoryAdapter
                val decoration = DividerItemDecoration(requireContext(), HORIZONTAL)
                binding.reservationHistoryList.addItemDecoration(decoration)
            }
        thread.start()

        thread.join()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
