package woowacourse.movie.view.home.theaters.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.view.home.theaters.TheaterListEventHandler

class TheaterViewHolder private constructor(
    private val binding: TheaterItemBinding,
    private val handler: TheaterListEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    constructor(
        parent: ViewGroup,
        handler: TheaterListEventHandler,
    ) : this(
        TheaterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        handler,
    )

    fun bind(
        item: Theater,
        count: Int,
    ) {
        binding.theater = item
        binding.handler = handler
        binding.screeningTimes.text =
            binding.root.context.getString(R.string.text_schedule_size)
                .format(count)
    }
}
