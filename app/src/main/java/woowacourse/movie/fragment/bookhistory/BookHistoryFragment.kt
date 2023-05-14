package woowacourse.movie.fragment.bookhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.BookHistoryRecyclerViewAdapter
import woowacourse.movie.R
import woowacourse.movie.activity.bookcomplete.BookCompleteActivity
import woowacourse.movie.database.BookHistories
import woowacourse.movie.database.BookingHistoryRepositoryImpl
import woowacourse.movie.databinding.FragmentBookHistoryBinding

class BookHistoryFragment : Fragment(), BookHistoryContract.View {
    private var _binding: FragmentBookHistoryBinding? = null
    private val binding get() = _binding!!
    override lateinit var presenter: BookHistoryContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_book_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = BookHistoryPresenter(this)
        super.onViewCreated(view, savedInstanceState)
        presenter.reloadBookingData(
            BookingHistoryRepositoryImpl(
                BookHistories.getDBInstance(
                    requireContext()
                )
            )
        )
        setMovieRecyclerView(view)
    }

    override fun showDetailPage(dataPosition: Int) {
        startActivity(
            BookCompleteActivity.intent(
                requireContext(),
                BookHistories.items[dataPosition]
            )
        )
    }

    private fun setMovieRecyclerView(view: View) {
        val movieRecyclerView: RecyclerView = view.findViewById(R.id.rv_book_history_list)
        movieRecyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayout.VERTICAL
            )
        )

        val bookHistoryRecyclerViewAdapter = BookHistoryRecyclerViewAdapter(
            BookHistories.items,
            getBookHistoryOnClickListener()
        )

        movieRecyclerView.adapter = bookHistoryRecyclerViewAdapter
        bookHistoryRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun getBookHistoryOnClickListener() = { position: Int ->
        showDetailPage(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
