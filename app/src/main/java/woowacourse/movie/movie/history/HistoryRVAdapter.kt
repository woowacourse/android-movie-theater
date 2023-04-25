package woowacourse.movie.movie.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.movie.dto.BookingMovieDto

class HistoryRVAdapter(
    private val histories: List<BookingMovieDto>,
) :
    RecyclerView.Adapter<HistoryViewHolder>() {

    // lateinit var itemMovieClick: OnClickListener<MovieDto>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = histories[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return histories.size
    }
}
