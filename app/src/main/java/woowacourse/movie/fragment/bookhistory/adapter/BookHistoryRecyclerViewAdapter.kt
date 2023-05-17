package woowacourse.movie.fragment.bookhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemBookHistoryListBinding
import woowacourse.movie.model.BookingHistoryData

class BookHistoryRecyclerViewAdapter(
    private val bookHistory: List<BookingHistoryData>,
    private val bookHistoryOnClickListener: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemBookHistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookHistoryViewHolder(binding, bookHistoryOnClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = bookHistory[position]
        (holder as BookHistoryViewHolder).bind(item)
    }

    override fun getItemCount(): Int = bookHistory.size
}
