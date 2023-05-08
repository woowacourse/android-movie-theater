package woowacourse.movie.view.main.movieslist.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.model.AdvertisementUiModel

class AdvertisementItemViewHolder(private val binding: ItemAdvertisementBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        advertisementUiModel: AdvertisementUiModel,
        onClickEvent: (AdvertisementUiModel) -> Unit
    ) {
        binding.advertisementImage.setImageResource(advertisementUiModel.image)
        binding.advertisementImage.setOnClickListener {
            onClickEvent(advertisementUiModel)
        }
    }
}
