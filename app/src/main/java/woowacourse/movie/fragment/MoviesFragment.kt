package woowacourse.movie.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import domain.Movie
import woowacourse.movie.MockAdvertisementFactory
import woowacourse.movie.MockMoviesFactory
import woowacourse.movie.R
import woowacourse.movie.activity.MovieReservationActivity
import woowacourse.movie.adapter.MovieAdapter
import woowacourse.movie.view.model.AdvertisementUiModel

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        val movies = MockMoviesFactory.makeMovies()
        val advertisementUiModel = MockAdvertisementFactory.generateAdvertisement()
        val movieList = view.findViewById<RecyclerView>(R.id.main_movie_list)

        movieList.adapter = MovieAdapter(
            movies,
            advertisementUiModel,
            ::advertisementClick,
            ::reservationButtonClick
        )
        return view
    }

    private fun reservationButtonClick(movie: Movie) {
        val context: Context = requireContext()
        MovieReservationActivity.start(context, movie)
    }

    private fun advertisementClick(advertisementUiModel: AdvertisementUiModel) {
        val url = advertisementUiModel.url
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
