package woowacourse.movie.home.view.adapter.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.home.view.listener.MovieHomeClickListener
import woowacourse.movie.model.Advertisement

class AdvertisementViewHolder(private val binding: ItemAdvertisementBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        advertisement: Advertisement,
        clickListener: MovieHomeClickListener,
    ) {
        binding.advertisement = advertisement
        binding.advertisementClickListener = clickListener
    }
}
