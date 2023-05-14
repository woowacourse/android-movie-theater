package woowacourse.movie.history.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import domain.BookingMovie
import woowacourse.movie.databinding.HistoryItemBinding

class HistoryAdapter(
    private val histories: List<BookingMovie>,
    private val onClick: (BookingMovie) -> Unit
) : RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding).apply {
            itemView.setOnClickListener {
                onClick(histories[bindingAdapterPosition])
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
