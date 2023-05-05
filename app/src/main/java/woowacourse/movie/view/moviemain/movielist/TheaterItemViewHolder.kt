package woowacourse.movie.view.moviemain.movielist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import java.time.LocalTime

class TheaterItemViewHolder(
    private val binding: TheaterItemBinding,
    private val onItemClick: TheaterListAdapter.OnItemClick,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(name: String, times: List<LocalTime>) {
        val context = binding.root.context
        with(binding) {
            textTheaterName.text = name
            textScreeningTimeCount.text = context.getString(R.string.screening_times_count, times.size)
            layoutTheaterItem.setOnClickListener { onItemClick.onClick(name) }
        }
    }
}
