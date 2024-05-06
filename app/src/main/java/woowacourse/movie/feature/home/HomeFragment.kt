package woowacourse.movie.feature.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.db.advertisement.AdvertisementDao
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.feature.home.adapter.MovieCatalogAdapter
import woowacourse.movie.feature.theater.TheaterSelectionFragment
import woowacourse.movie.model.advertisement.Advertisement
import woowacourse.movie.model.movie.Movie

class HomeFragment : Fragment(), ReservationHomeContract.View {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val presenter = ReservationHomePresenter(this, ScreeningDao(), AdvertisementDao())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadMovieCatalog()
    }

    override fun showMovieCatalog(
        movies: List<Movie>,
        advertisements: List<Advertisement>,
    ) {
        val movieCatalogAdapter =
            MovieCatalogAdapter { movieId ->
                presenter.sendMovieIdToTheaterSelection(movieId)
            }
        binding.rvHome.adapter = movieCatalogAdapter
        movieCatalogAdapter.updateData(movies, advertisements)
    }

    @SuppressLint("ResourceType")
    override fun navigateToTheaterSelection(movieId: Int) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movieId)
        val bottomSheetDialogFragment = TheaterSelectionFragment()
        bottomSheetDialogFragment.arguments = bundle
        bottomSheetDialogFragment
            .show(childFragmentManager, bottomSheetDialogFragment.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val MOVIE_ID = "movieId"
    }
}
