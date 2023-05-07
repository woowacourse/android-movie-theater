package woowacourse.movie.fragment.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.view.data.MovieListViewData
import woowacourse.movie.view.data.MovieListViewType
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.MovieViewDatas

class MovieAdapter(
    private val movieViewDatas: MovieViewDatas,
    val onClickItem: (data: MovieListViewData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val movieBinding: ItemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val adBinding: ItemAdvertisementBinding =
            ItemAdvertisementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (MovieListViewType.values()[viewType]) {
            MovieListViewType.MOVIE -> MovieInfoViewHolder(
                movieBinding
            ) { onClickItem(movieViewDatas.value[it]) }

            MovieListViewType.ADVERTISEMENT -> AdvertisementViewHolder(
                adBinding
            ) { onClickItem(movieViewDatas.value[it]) }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (MovieListViewType.values()[getItemViewType(position)]) {
            MovieListViewType.MOVIE -> (holder as MovieInfoViewHolder).bind(movieViewDatas.value[position] as MovieViewData)
            MovieListViewType.ADVERTISEMENT -> (holder as AdvertisementViewHolder)
        }
    }

    override fun getItemViewType(position: Int): Int =
        movieViewDatas.value[position].viewType.ordinal

    override fun getItemId(position: Int): Long = position.toLong()
    override fun getItemCount(): Int = movieViewDatas.value.size
}
