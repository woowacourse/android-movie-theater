package woowacourse.movie.dialog

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.RecyclerviewTheaterItemBinding
import woowacourse.movie.domain.model.TheaterMovie

class TheaterViewHolder(
    private val binding: RecyclerviewTheaterItemBinding,
    onClickItem: (Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.theaterClickImage.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }

    fun bind(theaterMovie: TheaterMovie) {
        binding.dialogTheaterNameText.text =
            binding.root.context.getString(R.string.theater_name, theaterMovie.theaterName)
        binding.dialogTimeText.text = binding.root.context.getString(
            R.string.theater_times_count, theaterMovie.screenTimes.size
        )
    }
}
