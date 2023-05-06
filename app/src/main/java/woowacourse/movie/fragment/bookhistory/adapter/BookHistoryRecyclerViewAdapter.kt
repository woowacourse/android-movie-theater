package woowacourse.movie.fragment.bookhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.BookingHistoryData

class BookHistoryRecyclerViewAdapter(
    private val bookHistory: List<BookingHistoryData>,
    private val bookHistoryOnClickListener: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book_history_list, parent, false)
        return BookHistoryViewHolder(view, bookHistoryOnClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = bookHistory[position]
        (holder as BookHistoryViewHolder).bind(item)
    }

    override fun getItemCount(): Int = bookHistory.size
}
