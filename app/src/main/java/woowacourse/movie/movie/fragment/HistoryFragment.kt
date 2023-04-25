package woowacourse.movie.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.movie.BookingHistoryDto
import woowacourse.movie.movie.history.HistoryRVAdapter

class HistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        setUpHistoryDatas(view)

        return view
    }

    private fun setUpHistoryDatas(view: View) {
        val history_rv = view.findViewById<RecyclerView>(R.id.history_rv)
        val historyRVAdapter = HistoryRVAdapter(
            BookingHistoryDto.getHistory(),
        )

        history_rv.adapter = historyRVAdapter
        historyRVAdapter.notifyDataSetChanged()
    }
}
