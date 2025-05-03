package woowacourse.movie.view.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.domain.model.TheaterUIModel

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    clickListener: TheaterClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.theaterClickListener = clickListener
    }

    fun bind(theaterUIModel: TheaterUIModel) {
        binding.theater = theaterUIModel
    }
}
