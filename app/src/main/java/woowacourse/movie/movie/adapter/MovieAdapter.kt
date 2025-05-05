package woowacourse.movie.movie.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.ui.model.MovieFeedUiModel

class MovieAdapter(
    private val items: List<MovieFeedUiModel>,
    private val onReserveClick: ReserveClickListener,
) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MovieFeedUiModel.MovieItem -> R.layout.movie_list_item
            is MovieFeedUiModel.AdvertisementItem -> R.layout.ad_banner_item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (viewType) {
            R.layout.movie_list_item -> {
                MovieViewHolder(parent, onReserveClick)
            }

            R.layout.ad_banner_item -> {
                AdViewHolder(parent)
            }
            else -> throw IllegalArgumentException(INVALID_VIEW_TYPE)
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = items[position]

        when (holder) {
            is MovieViewHolder -> holder.bind((item as MovieFeedUiModel.MovieItem).movie)
            is AdViewHolder -> holder.bind(item as MovieFeedUiModel.AdvertisementItem)
        }
    }

    companion object {
        private const val INVALID_VIEW_TYPE: String = "지원하지 않는 아이템 타입입니다"
    }
}
