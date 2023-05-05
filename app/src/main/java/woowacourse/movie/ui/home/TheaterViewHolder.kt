package woowacourse.movie.ui.home

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.model.TheaterUiModel

class TheaterViewHolder(
    private val binding: TheaterItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        theater: TheaterUiModel,
        onTheaterSelected: () -> Unit
    ) {
        with(binding) {
            textTheaterName.text = theater.name
            textScreeningTimes.text = binding.root.context.getString(
                R.string.number_of_screening_times,
                theater.numberOfScreeningTimes
            )
            root.setOnClickListener {
                onTheaterSelected()
            }
        }
    }
}
