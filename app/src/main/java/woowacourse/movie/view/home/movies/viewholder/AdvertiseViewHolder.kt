package woowacourse.movie.view.home.movies.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.view.home.movies.model.UiModel.AdvertiseUiModel

class AdvertiseViewHolder private constructor(
    private val binding: AdvertisementItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    constructor(parent: ViewGroup) : this(
        AdvertisementItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

    fun bind(item: AdvertiseUiModel) {
        binding.model = item
    }
}
