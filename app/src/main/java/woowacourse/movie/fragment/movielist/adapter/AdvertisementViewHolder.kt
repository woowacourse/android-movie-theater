package woowacourse.movie.fragment.movielist.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class AdvertisementViewHolder(view: View, onClickItem: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }
}
