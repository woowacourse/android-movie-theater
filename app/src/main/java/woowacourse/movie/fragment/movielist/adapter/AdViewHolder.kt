package woowacourse.movie.fragment.movielist.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdListBinding
import woowacourse.movie.model.AdUIModel

class AdViewHolder(
    private val binding: ItemAdListBinding,
    private val listener: (AdUIModel) -> Unit,
    ad: AdUIModel
) :
    RecyclerView.ViewHolder(binding.root) {
    private val adImage = itemView.findViewById<ImageView>(R.id.iv_ad)

    init {
        adImage.setOnClickListener {
            listener(ad)
        }
    }

    fun bind(item: AdUIModel) {
        adImage.setImageResource(item.adImage)
    }
}
