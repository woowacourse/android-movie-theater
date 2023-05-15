package woowacourse.movie.feature.movieList.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.domain.usecase.GetTheaterScreeningInfoByMovieUseCase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.data.TheaterRepositoryImpl
import woowacourse.movie.databinding.FragmentTheaterBottomSheetBinding
import woowacourse.movie.feature.common.Toaster
import woowacourse.movie.feature.movieList.MovieListFragment
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.util.getParcelableCompat

class TheaterBottomSheetFragment : BottomSheetDialogFragment(), TheaterContract.View {

    private var _binding: FragmentTheaterBottomSheetBinding? = null
    private val binding: FragmentTheaterBottomSheetBinding
        get() = _binding!!

    private lateinit var presenter: TheaterContract.Presenter

    private lateinit var movie: MovieState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelableCompat(MOVIE_KEY) ?: return dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTheaterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TheaterPresenter(
            this,
            GetTheaterScreeningInfoByMovieUseCase(TheaterRepositoryImpl())
        )
        presenter.loadTheatersData(movie)
    }

    override fun setTheaterItems(theaters: List<TheaterItemModel>) {
        binding.rvTheater.adapter = TheaterAdapter(theaters)
    }

    override fun selectTheater(theater: SelectTheaterAndMovieState) {
        setFragmentResult(
            MovieListFragment.THEATER_SELECT_KEY,
            bundleOf(MovieListFragment.THEATER_MOVIE_KEY to theater)
        )
    }

    override fun loadTheaterIsEmpty() {
        binding.theaterIsEmptyText.visibility = View.VISIBLE
        binding.rvTheater.visibility = View.GONE
    }

    override fun errorLoadTheaterData() {
        Toaster.showToast(requireContext(), getString(R.string.error_load_theater_data))
    }

    override fun bottomSheetDismiss() {
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: MovieState) =
            TheaterBottomSheetFragment().apply {
                arguments = bundleOf(
                    MOVIE_KEY to movie
                )
            }

        private const val MOVIE_KEY = "movie_key"
    }
}
