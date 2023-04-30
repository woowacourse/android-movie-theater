package woowacourse.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.woowacourse.domain.BookHistories
import woowacourse.movie.BookHistoryRecyclerViewAdapter
import woowacourse.movie.R
import woowacourse.movie.activity.BookCompleteActivity
import woowacourse.movie.movie.toPresentation

class BookHistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_history, container, false)

        setMovieRecyclerView(view)

        return view
    }

    private fun setMovieRecyclerView(view: View) {
        val movieRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_book_history_list)
        movieRecyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayout.VERTICAL
            )
        )
        val bookHistoryRecyclerViewAdapter = BookHistoryRecyclerViewAdapter(
            BookHistories.items,
            onClickBookHistory(view)
        )
        movieRecyclerView.adapter = bookHistoryRecyclerViewAdapter
    }

    private fun onClickBookHistory(view: View) = { position: Int ->
        val intent = BookCompleteActivity.getIntent(
            view.context,
            BookHistories.items[position].toPresentation()
        )
        this.startActivity(intent)
    }
}
