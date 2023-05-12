package woowacourse.movie.fragment.theater.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.util.listener.OnClickListener

class TheaterViewHolder private constructor(
    private val binding: TheaterItemBinding,
    onItemClickListener: OnClickListener<Int>,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.clickButton.setOnClickListener {
            onItemClickListener.onClick(absoluteAdapterPosition)
        }
    }

    fun bind(movieId: Int, item: TheaterUIModel) {
        binding.theater = item
        binding.movieId = movieId
    }

    companion object {
        fun from(
            parent:ViewGroup,
            onItemClickListener: OnClickListener<Int>
        ): TheaterViewHolder {
            val binding = TheaterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TheaterViewHolder(binding, onItemClickListener)
        }
    }
}
