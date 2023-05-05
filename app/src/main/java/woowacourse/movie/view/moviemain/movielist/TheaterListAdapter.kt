package woowacourse.movie.view.moviemain.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.util.getKeyFromIndex
import woowacourse.movie.util.getOrEmptyList
import java.time.LocalTime

class TheaterListAdapter(
    private val schedule: Map<String, List<LocalTime>>,
    private val onItemClick: OnItemClick,
) : RecyclerView.Adapter<TheaterItemViewHolder>() {
    fun interface OnItemClick {
        fun onClick(name: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.theater_item, parent, false)
        return TheaterItemViewHolder(TheaterItemBinding.bind(view), onItemClick)
    }

    override fun getItemCount(): Int {
        return schedule.size
    }

    override fun onBindViewHolder(holder: TheaterItemViewHolder, position: Int) {
        val key = schedule.getKeyFromIndex(position)
        holder.bind(key, schedule.getOrEmptyList(key))
    }
}
