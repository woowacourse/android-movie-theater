package woowacourse.movie.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import domain.BookingMovie
import woowacourse.movie.databinding.HistoryItemBinding
import woowacourse.movie.movielist.OnClickListener

class HistoryAdapter(private val histories: List<BookingMovie>) : RecyclerView.Adapter<HistoryViewHolder>() {
    lateinit var itemViewClick: OnClickListener<BookingMovie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding).apply {
            itemView.setOnClickListener {
                itemViewClick.onClick(histories[bindingAdapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = histories[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return histories.size
    }
}
