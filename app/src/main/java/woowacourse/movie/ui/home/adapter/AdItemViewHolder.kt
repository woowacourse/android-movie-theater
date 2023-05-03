package woowacourse.movie.ui.home.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.uimodel.MovieListModel

class AdItemViewHolder(
    view: View,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(view) {

    private val banner = view.findViewById<ImageView>(R.id.item_banner)

    init {
        banner.setOnClickListener { onItemClick(adapterPosition) }
    }

    fun bind(ad: MovieListModel.AdModel) {
        banner.setImageResource(ad.banner)
    }
}
