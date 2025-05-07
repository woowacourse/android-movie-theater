package woowacourse.movie.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R

class AdViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.ad_banner_item, parent, false),
    ) {
    private val imgBanner: ImageView = itemView.findViewById(R.id.img_banner)

    fun bind() {
        imgBanner.setImageResource(R.drawable.img_advertisement)
    }
}
