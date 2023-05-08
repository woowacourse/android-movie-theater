package woowacourse.movie.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding

class AdvertisementViewHolder(binding: ItemAdvertisementBinding, onClickItem: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }

    companion object {
        fun from(
            root: ViewGroup,
            onClickItem: (Int) -> Unit
        ): AdvertisementViewHolder {
            return AdvertisementViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(root.context), R.layout.item_advertisement, root, false
                ),
                onClickItem
            )
        }
    }
}
