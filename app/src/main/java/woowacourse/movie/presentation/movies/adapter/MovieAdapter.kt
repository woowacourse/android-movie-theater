package woowacourse.movie.presentation.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdsBinding
import woowacourse.movie.databinding.ItemMovieBinding

class MovieAdapter(
    private val items: List<MovieListItem>,
    private val clickListener: MovieListClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (MovieListItem.ViewType.entries[viewType]) {
            MovieListItem.ViewType.TYPE_MOVIE -> {
                val binding =
                    DataBindingUtil.inflate<ItemMovieBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.item_movie,
                        parent,
                        false,
                    )
                MovieViewHolder(binding, clickListener)
            }

            MovieListItem.ViewType.TYPE_ADS -> {
                val binding =
                    DataBindingUtil.inflate<ItemAdsBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.item_ads,
                        parent,
                        false,
                    )
                AdsViewHolder(binding)
            }
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = items[position]) {
            is MovieListItem.MovieItem -> (holder as MovieViewHolder).bind(item)
            is MovieListItem.AdsItem -> (holder as AdsViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int = items[position].type.ordinal
}
