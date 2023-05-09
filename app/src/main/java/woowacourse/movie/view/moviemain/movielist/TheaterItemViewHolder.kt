package woowacourse.movie.view.moviemain.movielist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import java.time.LocalTime

class TheaterItemViewHolder(
    private val binding: TheaterItemBinding,
    private val onItemClick: TheaterListAdapter.OnItemClick,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(name: String, times: List<LocalTime>) {
        binding.theaterName = name
        binding.timeCount = times.size
        binding.onItemClick = onItemClick
    }
}
