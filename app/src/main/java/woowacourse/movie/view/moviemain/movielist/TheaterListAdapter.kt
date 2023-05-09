package woowacourse.movie.view.moviemain.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import java.time.LocalTime

class TheaterListAdapter(
    schedule: Map<String, List<LocalTime>>,
    private val onItemClick: OnItemClick,
) : RecyclerView.Adapter<TheaterItemViewHolder>() {
    private val convertSchedule = schedule.toList()

    fun interface OnItemClick {
        fun onClick(name: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.theater_item, parent, false)
        return TheaterItemViewHolder(TheaterItemBinding.bind(view), onItemClick)
    }

    override fun getItemCount(): Int {
        return convertSchedule.size
    }

    override fun onBindViewHolder(holder: TheaterItemViewHolder, position: Int) {
        holder.bind(convertSchedule[position].first, convertSchedule[position].second)
    }
}
