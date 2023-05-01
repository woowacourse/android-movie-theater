package woowacourse.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.BookHistories
import woowacourse.movie.BookHistoryRecyclerViewAdapter
import woowacourse.movie.BundleKeys
import woowacourse.movie.R
import woowacourse.movie.activity.BookCompleteActivity

class BookHistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMovieRecyclerView(view)
    }

    private fun setMovieRecyclerView(view: View) {
        val movieRecyclerView = view.findViewById<RecyclerView>(R.id.rv_book_history_list)
        movieRecyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayout.VERTICAL
            )
        )
        val bookHistoryRecyclerViewAdapter = BookHistoryRecyclerViewAdapter(
            BookHistories.items,
            getBookHistoryOnClickListener(view)
        )
        movieRecyclerView.adapter = bookHistoryRecyclerViewAdapter
        bookHistoryRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun getBookHistoryOnClickListener(view: View) = { position: Int ->
        val intent = BookCompleteActivity.intent(view.context)
        intent.putExtra(BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY, BookHistories.items[position])
        this.startActivity(intent)
    }
}
