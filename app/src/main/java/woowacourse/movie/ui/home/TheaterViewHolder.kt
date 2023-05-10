package woowacourse.movie.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.model.TheaterUiModel

class TheaterViewHolder private constructor(
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

    companion object {

        fun from(parent: ViewGroup): TheaterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TheaterItemBinding.inflate(layoutInflater, parent, false)

            return TheaterViewHolder(binding)
        }
    }
}
