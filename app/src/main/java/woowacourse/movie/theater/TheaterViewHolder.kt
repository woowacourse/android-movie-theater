package woowacourse.movie.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterViewHolder(
    parent: ViewGroup,
    onSelectClick: SelectClickListener,
) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.theater_item, parent, false),
    ) {
    private val binding = TheaterItemBinding.bind(itemView)

    init {
        binding.clickListener = onSelectClick
    }

    fun bind(theater: TheaterUiModel) {
        binding.theater = theater
    }
}
