package woowacourse.movie.movie.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HistoryItemBinding
import woowacourse.movie.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.movie.movielist.OnClickListener

class HistoryAdapter(private val histories: List<BookingMovieEntity>,) : RecyclerView.Adapter<HistoryViewHolder>() {
    lateinit var itemViewClick: OnClickListener<BookingMovieEntity>

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
