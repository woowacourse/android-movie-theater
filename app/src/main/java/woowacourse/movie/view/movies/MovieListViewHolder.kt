package woowacourse.movie.view.movies

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.model.MovieListItem

sealed class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: MovieListItem) {
        when (item) {
            is MovieListItem.AdItem -> (this as AdViewHolder).bind(item.ad)
            is MovieListItem.MovieItem -> (this as MovieViewHolder).bind(item.movie)
        }
    }
}
