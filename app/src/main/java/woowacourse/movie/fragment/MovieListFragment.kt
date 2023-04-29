package woowacourse.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.activity.MovieReservationActivity
import woowacourse.movie.domain.AdvertisementMock
import woowacourse.movie.domain.advertismentPolicy.MovieAdvertisementPolicy
import woowacourse.movie.view.adapter.MovieAdapter
import woowacourse.movie.view.data.MovieListViewData
import woowacourse.movie.view.data.MovieListViewType
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.repository.MainRepository

class MovieListFragment : Fragment() {

    private val mainRepository: MainRepository = MainRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        makeMovieRecyclerView(view)

        return view
    }

    private fun makeMovieRecyclerView(view: View) {
        val movies = mainRepository.requestMovie()
        val advertisementDatas = AdvertisementMock.createAdvertisements()
        val advertisementPolicy =
            MovieAdvertisementPolicy(MOVIE_COUNT, ADVERTISEMENT_COUNT)

        val movieRecyclerView = view.findViewById<RecyclerView>(R.id.main_movie_list)
        movieRecyclerView.adapter = MovieAdapter(
            movies, advertisementDatas, advertisementPolicy, ::onClickItem
        )
    }

    private fun onClickItem(view: View, data: MovieListViewData) {
        when (data.viewType) {
            MovieListViewType.MOVIE -> MovieReservationActivity.from(
                view.context, data as MovieViewData
            ).run {
                startActivity(this)
            }
            MovieListViewType.ADVERTISEMENT -> Unit
        }
    }

    companion object {
        private const val MOVIE_COUNT = 3
        private const val ADVERTISEMENT_COUNT = 1
    }
}
