package woowacourse.movie.ui.main.movies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import woowacourse.movie.adapter.MovieAdapter
import woowacourse.movie.data.model.mapper.MovieMapper
import woowacourse.movie.data.model.uimodel.AdvertisementUIModel
import woowacourse.movie.data.model.uimodel.MovieUIModel
import woowacourse.movie.data.model.uimodel.TheaterUIModel
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.repository.MoviesRepositoryImpl
import woowacourse.movie.ui.main.TheaterBottomDialogFragment
import woowacourse.movie.ui.moviereservation.MovieReservationActivity

class MoviesFragment : Fragment(), MoviesContract.View {
    override lateinit var presenter: MoviesContract.Presenter
    private lateinit var adapter: MovieAdapter

    private var _binding: FragmentMoviesBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        setUpPresenter()
        setUpAdapter()

        return binding.root
    }

    private fun setUpPresenter() {
        presenter = MoviesPresenter(this, MoviesRepositoryImpl)
    }

    private fun setUpAdapter() {
        adapter = MovieAdapter()
        binding.mainMovieList.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpOnClick()
    }

    private fun setUpOnClick() {
        presenter.setUpClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun updateMovies() {
        val movies = presenter.getMovies()
        adapter.updateMovieItems(MovieMapper.toUi(movies), ::setOnMovieItemClick)
    }

    override fun setOnMovieItemClick(movieUiModel: MovieUIModel) {
        val theaterBottomDialogFragment = TheaterBottomDialogFragment()
        theaterBottomDialogFragment.show(parentFragmentManager, THEATER_BOTTOM_DIALOG_TAG)
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val theater: TheaterUIModel = bundle.getSerializableCompat("bundleKey") ?: throw IllegalStateException("못찾음")
            val intent = MovieReservationActivity.getIntent(requireContext(), MovieMapper.toDomain(movieUiModel), theater)
            startActivity(intent)
        }
    }

    override fun updateAdvertisements() {
        val advertisements = presenter.getAdvertisements()
        adapter.updateAdvertisementItems(advertisements, ::setOnAdvertisementItemClick)
    }

    override fun setOnAdvertisementItemClick(advertisementUiModel: AdvertisementUIModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(advertisementUiModel.url))
        startActivity(intent)
    }

    companion object {
        private const val THEATER_BOTTOM_DIALOG_TAG = "THEATER_BOTTOM_DIALOG_TAG"
    }
}
