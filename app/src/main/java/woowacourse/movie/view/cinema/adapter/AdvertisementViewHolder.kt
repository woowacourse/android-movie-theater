package woowacourse.movie.view.cinema.adapter

import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.domain.reservation.Advertisement
import woowacourse.movie.domain.reservation.ScreeningContent
import woowacourse.movie.view.reservation.AdvertisementImage.advertisementResourceId

class AdvertisementViewHolder(
    private val binding: ItemAdvertisementBinding,
) : ScreeningContentViewHolder(binding.root) {
    override fun bind(item: ScreeningContent) {
        val advertisement: Advertisement = item as? Advertisement ?: error("")
        binding.advertisement = advertisement
        binding.srcDrawable = advertisement.advertisementResourceId()
    }
}
