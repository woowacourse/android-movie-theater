package woowacourse.movie.view.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.view.home.MovieType.AdvertisementItem
import woowacourse.movie.view.home.MovieType.ItemType
import woowacourse.movie.view.home.MovieType.MovieItem

class MovieAdapter(
    private val movieClickListener: MovieClickListener,
) : ListAdapter<MovieType, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<MovieType>() {
            override fun areItemsTheSame(
                oldItem: MovieType,
                newItem: MovieType,
            ): Boolean =
                when (oldItem is MovieItem && newItem is MovieItem) {
                    true -> oldItem.movie.id == newItem.movie.id
                    false -> oldItem == newItem
                }

            override fun areContentsTheSame(
                oldItem: MovieType,
                newItem: MovieType,
            ): Boolean = oldItem == newItem
        },
    ) {
    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is MovieItem -> ItemType.MOVIE.ordinal
            is AdvertisementItem -> ItemType.ADVERTISEMENT.ordinal
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (ItemType.valueOf(viewType)) {
            ItemType.MOVIE -> MovieViewHolder.from(parent, movieClickListener::onReservationClick)
            ItemType.ADVERTISEMENT ->
                AdvertisementViewHolder.from(
                    parent,
                    movieClickListener::onAdvertisementClick,
                )
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MovieViewHolder -> holder.bind((getItem(position) as MovieItem).movie)
            is AdvertisementViewHolder -> holder.bind(getItem(position) as AdvertisementItem)
        }
    }
}
