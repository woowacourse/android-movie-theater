package woowacourse.movie.view.main.movieslist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.view.moviereservation.MovieReservationActivity
import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.TheatersUiModel
import woowacourse.movie.view.main.movieslist.adapter.MovieAdapter
import woowacourse.movie.view.main.movieslist.adapter.TheaterAdapter


class MoviesFragment : Fragment(R.layout.fragment_movies), MoviesContract.View {
    override val presenter: MoviesContract.Presenter by lazy { MoviesPresenter(this) }
    private lateinit var binding: FragmentMoviesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.updateMovieList()
    }

    override fun setAdapter(
        movieUiModels: List<MovieUiModel>,
        advertisementUiModel: AdvertisementUiModel
    ) {
        binding.mainMovieList.adapter = MovieAdapter(
            movieUiModels = movieUiModels,
            advertisementUiModel = advertisementUiModel,
            advertisementClickEvent = presenter::onAdvertisementItemClick,
            movieListClickEvent = presenter::onMovieItemClick
        )
    }

    override fun showBottomSheet(theatersUiModel: TheatersUiModel) {
        val bottomSheetDialog = BottomSheetDialog(
            requireContext()
        )
        val bottomSheetView = LayoutInflater.from(requireContext()).inflate(
            R.layout.bottom_sheet, null
        )
        bottomSheetView.findViewById<RecyclerView>(R.id.theater_recycler_view).adapter =
            TheaterAdapter(requireContext(), theatersUiModel, presenter::onTheaterItemClick)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    override fun startMovieReservationActivity(
        movieUiModel: MovieUiModel,
        theaterUiModel: TheaterUiModel
    ) {
        MovieReservationActivity.start(requireContext(), movieUiModel, theaterUiModel)
    }

    override fun startAdvertisementUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
