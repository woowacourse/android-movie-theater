package woowacourse.movie.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.mock.MockAdvertisementFactory
import woowacourse.movie.mock.MockMoviesFactory
import woowacourse.movie.R
import woowacourse.movie.view.activity.MovieReservationActivity
import woowacourse.movie.view.adapter.MovieAdapter
import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel


class MoviesFragment : Fragment(R.layout.fragment_movies) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movies = MockMoviesFactory.makeMovies()
        val advertisementUiModel = MockAdvertisementFactory.generateAdvertisement()
        val movieList = view.findViewById<RecyclerView>(R.id.main_movie_list)
        movieList.adapter = MovieAdapter(
            movies, advertisementUiModel, ::advertisementClick, ::reservationButtonClick
        )
    }
    private fun reservationButtonClick(movieUiModel: MovieUiModel) {
        MovieReservationActivity.start(requireContext(), movieUiModel)
    }

    private fun advertisementClick(advertisementUiModel: AdvertisementUiModel) {
        val url = advertisementUiModel.url
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
