package woowacourse.movie.ui.movielist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.theater.Theater

class TheaterViewHolder(
    private val itemBinding: TheaterItemBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(theater: Theater) {
        itemBinding.theater = theater
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClickTheater: TheaterClickListener,
        ): TheaterViewHolder {
            val binding =
                TheaterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            binding.theaterClickListener = onClickTheater
            return TheaterViewHolder(binding)
        }
    }
}
