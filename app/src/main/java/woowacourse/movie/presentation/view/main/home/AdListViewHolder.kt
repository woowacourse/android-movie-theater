package woowacourse.movie.presentation.view.main.home

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R

class AdListViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_advertise, parent, false)
) {
    private val advertise = itemView.findViewById<ImageView>(R.id.iv_advertise)
    fun bind(ad: Drawable) {
        advertise.setImageDrawable(ad)
    }
}
