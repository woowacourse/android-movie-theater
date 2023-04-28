package woowacourse.movie.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.Advertisement
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.advertismentPolicy.AdvertisementPolicy
import woowacourse.movie.view.data.MovieListItemViewData
import woowacourse.movie.view.data.MovieListItemsViewData
import woowacourse.movie.view.data.MovieListViewType
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.mapper.AdvertisementMapper.toView
import woowacourse.movie.view.mapper.MovieMapper.toView
import woowacourse.movie.view.viewholder.AdvertisementViewHolder
import woowacourse.movie.view.viewholder.MovieInfoViewHolder

class MovieAdapter(
    movie: List<Movie>,
    advertisement: List<Advertisement>,
    advertisementPolicy: AdvertisementPolicy,
    val onClickItem: (data: MovieListItemViewData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movieListItemsViewData: MovieListItemsViewData =
        makeMovieListViewData(movie, advertisement, advertisementPolicy)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (MovieListViewType.values()[viewType]) {
            MovieListViewType.MOVIE -> MovieInfoViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_movie, parent, false
                )
            ) {
                onClickItem(movieListItemsViewData.value[it])
            }

            MovieListViewType.ADVERTISEMENT -> AdvertisementViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_advertisement, parent, false
                )
            ) {
                onClickItem(movieListItemsViewData.value[it])
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (MovieListViewType.values()[getItemViewType(position)]) {
            MovieListViewType.MOVIE -> (holder as MovieInfoViewHolder).bind(movieListItemsViewData.value[position] as MovieViewData)
            MovieListViewType.ADVERTISEMENT -> (holder as AdvertisementViewHolder)
        }
    }

    override fun getItemViewType(position: Int): Int =
        movieListItemsViewData.value[position].viewType.ordinal

    override fun getItemId(position: Int): Long = position.toLong()
    override fun getItemCount(): Int = movieListItemsViewData.value.size

    fun updateMovieListViewData(
        movie: List<Movie>,
        advertisement: List<Advertisement>,
        advertisementPolicy: AdvertisementPolicy
    ) {
        movieListItemsViewData = makeMovieListViewData(movie, advertisement, advertisementPolicy)
        notifyDataSetChanged()
    }

    private fun makeMovieListViewData(
        movie: List<Movie>,
        advertisement: List<Advertisement>,
        advertisementPolicy: AdvertisementPolicy
    ): MovieListItemsViewData {
        return mutableListOf<MovieListItemViewData>().apply {
            for (index in movie.indices) {
                if (index > 0 && index % advertisementPolicy.movieCount == 0) add(advertisement[0].toView())
                add(movie[index].toView())
            }
        }.let {
            MovieListItemsViewData(it)
        }
    }
}
