package woowacourse.movie.view.home.movies.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdItemBinding
import woowacourse.movie.view.home.model.FeedUiModel.AdUiModel

class AdViewHolder private constructor(
    private val binding: AdItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    constructor(parent: ViewGroup) : this(
        AdItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

    fun bind(item: AdUiModel) {
        binding.ad = item
    }
}
