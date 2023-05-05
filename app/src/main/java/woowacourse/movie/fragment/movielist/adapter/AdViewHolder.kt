package woowacourse.movie.fragment.movielist.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.AdUIModel

class AdViewHolder(private val view: View, private val listener: (AdUIModel) -> Unit, ad: AdUIModel) :
    RecyclerView.ViewHolder(view) {
    val adImage = itemView.findViewById<ImageView>(R.id.iv_ad)

    init {
        adImage.setOnClickListener {
            listener(ad)
        }
    }

    fun bind(item: AdUIModel) {
        adImage.setImageResource(item.adImage)
    }
}
