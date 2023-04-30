package woowacourse.movie.presentation.activities.main.fragments.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyRecyclerView = view.findViewById<RecyclerView>(R.id.history_recycler_view)
        val historyAdapter = HistoryListAdapter { item ->

            when (item) {
                is Reservation -> {
                    startActivity(
                        Intent(requireContext(), TicketingResultActivity::class.java)
                            .putExtra(TicketingResultActivity.RESERVATION_KEY, item),
                    )
                }
                is Ad -> {}
                is Movie -> {}
            }
        }
        historyAdapter.appendAll(Reservation.provideDummy())
        historyRecyclerView.adapter = historyAdapter
        historyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL,
            ),
        )
        Log.d("krrong", "onViewCreated")
    }

    companion object {
        private val historyFragment = HistoryFragment()

        fun getInstance(): HistoryFragment = historyFragment
    }
}
