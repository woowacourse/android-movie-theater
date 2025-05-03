package woowacourse.movie.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.databinding.AdBannerItemBinding
import woowacourse.movie.databinding.MovieListItemBinding
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
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.movie_list_item -> {
                val binding = MovieListItemBinding.inflate(inflater, parent, false)
                MovieViewHolder(binding, onReserveClick)
            }

            R.layout.ad_banner_item -> {
                val binding = AdBannerItemBinding.inflate(inflater, parent, false)
                AdViewHolder(binding)
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
            is AdViewHolder -> holder.binding(item as MovieFeedUiModel.AdvertisementItem)
        }
    }

    companion object {
        private const val INVALID_VIEW_TYPE: String = "지원하지 않는 아이템 타입입니다"
    }
}
