package woowacourse.movie.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.common.model.MovieListItemsViewData
import woowacourse.movie.common.model.MovieListViewType
import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.model.TheatersViewData

class MovieAdapter(
    movieDao: MovieDao,
    val createTheaterDialog: (MovieViewData, TheatersViewData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), MovieAdapterContract.View {
    private var movieListItemsViewData: MovieListItemsViewData = MovieListItemsViewData(emptyList())

    override val presenter: MovieAdapterContract.Presenter = MovieAdapterPresenter(this, movieDao)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (MovieListViewType.values()[viewType]) {
            MovieListViewType.MOVIE -> MovieInfoViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_movie, parent, false
                )
            ) {
                presenter.makeTheaterDialog(movieListItemsViewData.value[it])
            }

            MovieListViewType.ADVERTISEMENT -> AdvertisementViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_advertisement, parent, false
                )
            ) {
                presenter.makeTheaterDialog(movieListItemsViewData.value[it])
            }
        }
    }

    override fun onClickItem(movieViewData: MovieViewData, theatersViewData: TheatersViewData) {
        when (movieViewData.viewType) {
            MovieListViewType.MOVIE -> createTheaterDialog(movieViewData, theatersViewData)
            MovieListViewType.ADVERTISEMENT -> Unit
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

    override fun setAdapterData(movies: MovieListItemsViewData) {
        movieListItemsViewData = movies
        notifyDataSetChanged()
    }
}
