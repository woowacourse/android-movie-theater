package woowacourse.movie.movie.movielist

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.dto.AdDto

class AdViewHolder(view: View, private val onAdClickListener: OnClickListener<AdDto>) :
    RecyclerView.ViewHolder(view) {
    private val adImg = itemView.findViewById<ImageView>(R.id.ad)

    fun bind(item: AdDto) {
        adImg.setImageResource(item.adImage)
        adImg.setOnClickListener {
            onAdClickListener.onClick(item)
        }
    }
}
