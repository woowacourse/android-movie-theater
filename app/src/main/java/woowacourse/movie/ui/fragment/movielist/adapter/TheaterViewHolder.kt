package woowacourse.movie.ui.fragment.movielist.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.ui.model.TheaterModel

class TheaterViewHolder(view: View, private val onItemViewClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val binding: TheaterItemBinding

    init {
        binding = TheaterItemBinding.bind(view)
        view.setOnClickListener { onItemViewClick(absoluteAdapterPosition) }
        binding.ivTheaterNext.setOnClickListener { onItemViewClick(absoluteAdapterPosition) }
    }

    fun bind(theater: TheaterModel) {
        binding.tvTheaterName.text =
            binding.tvTheaterName.context.getString(R.string.theater_name, theater.name)
        binding.tvTheaterDescription.text =
            binding.tvTheaterDescription.context.getString(
                R.string.theater_description,
                theater.times.size
            )
    }
}
