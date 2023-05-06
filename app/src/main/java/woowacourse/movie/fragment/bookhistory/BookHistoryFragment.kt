package woowacourse.movie.fragment.bookhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.woowacourse.domain.BookHistories
import woowacourse.movie.R
import woowacourse.movie.activity.bookComplete.BookCompleteActivity
import woowacourse.movie.fragment.bookhistory.adapter.BookHistoryRecyclerViewAdapter
import woowacourse.movie.movie.toPresentation

class BookHistoryFragment : Fragment(), BookHistoryContract.View {
    override lateinit var presenter: BookHistoryContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = BookHistoryPresenter(this)
        return inflater.inflate(R.layout.fragment_book_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setMovieRecyclerView(onClickBookHistory())
    }

    private fun onClickBookHistory() = { position: Int ->
        val intent = BookCompleteActivity.getIntent(
            requireContext(),
            BookHistories.items[position].toPresentation()
        )
        startActivity(intent)
    }

    override fun setMovieRecyclerView(bookHistoryRecyclerViewAdapter: BookHistoryRecyclerViewAdapter) {
        val bookHistoryRecyclerView =
            requireView().findViewById<RecyclerView>(R.id.recyclerview_book_history_list)
        makeDivider(bookHistoryRecyclerView)
        bookHistoryRecyclerView.adapter = bookHistoryRecyclerViewAdapter
    }

    private fun makeDivider(bookHistoryRecyclerView: RecyclerView) {
        bookHistoryRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayout.VERTICAL
            )
        )
    }
}
