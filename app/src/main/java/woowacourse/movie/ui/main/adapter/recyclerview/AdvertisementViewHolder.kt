package woowacourse.movie.ui.main.adapter.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.AdvertisementListItemBinding
import woowacourse.movie.model.main.AdvertisementUiModel
import woowacourse.movie.model.main.MainData
import woowacourse.movie.ui.main.adapter.MainViewType

class AdvertisementViewHolder(
    binding: AdvertisementListItemBinding
) : MainViewHolder<AdvertisementListItemBinding>(binding) {

    override val mainViewType: MainViewType = MainViewType.ADVERTISEMENT
    private lateinit var advertisement: AdvertisementUiModel

    override fun onBind(data: MainData) {
        advertisement = data as AdvertisementUiModel
        binding.imageAdvertisement.setImageResource(advertisement.image)
    }

    fun setAdvertisementClick(clickAd: (Intent) -> Unit) {
        binding.imageAdvertisement.setOnClickListener {
            clickAd(advertisement.getIntent())
        }
    }

    companion object {

        fun from(parent: ViewGroup): AdvertisementViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AdvertisementListItemBinding.inflate(layoutInflater, parent, false)

            return AdvertisementViewHolder(binding)
        }
    }
}
