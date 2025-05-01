package woowacourse.movie.view.movies.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.theater.Theater

class TheaterViewHolder(
    private val binding: TheaterItemBinding,
    private val onClick: (Theater) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Theater,
        count: Int,
    ) {
        binding.model = item
        binding.root.setOnClickListener {
            onClick(item)
        }
        binding.screeningTimes.text =
            binding.root.context.getString(R.string.text_schedule_size)
                .format(count)
    }
}
