package woowacourse.movie.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterViewHolder(
    private val binding: TheaterItemBinding,
    private val onSelectClick: SelectClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(theater: TheaterUiModel) {
        binding.theater = theater
        binding.clickListener = onSelectClick
    }

    companion object {
        fun from(
            parent: ViewGroup,
            clickListener: SelectClickListener,
        ): TheaterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = TheaterItemBinding.inflate(inflater, parent, false)
            return TheaterViewHolder(binding, clickListener)
        }
    }
}
