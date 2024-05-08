package woowacourse.movie.presentation.movieList.cinemaListDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.presentation.common.withArgs
import woowacourse.movie.databinding.FragmentBottomSheetTheatersBinding
import woowacourse.movie.presentation.error.ErrorActivity
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.presentation.movieDetail.MovieDetailActivity
import woowacourse.movie.presentation.movieList.MovieListFragment.Companion.THEATER_KEY

class ChooseCinemasBottomSheetFragment : BottomSheetDialogFragment(), ChooseCinemasContract.View {
    private var _binding: FragmentBottomSheetTheatersBinding? = null
    private val binding get() = _binding ?: error("error")
    private lateinit var adapter: ChooseCinemasAdapter

    private val presenter: ChooseCinemasContract.Presenter by lazy {
        ChooseCinemasPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBottomSheetTheatersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        arguments?.let {
            BundleCompat.getSerializable(it, THEATER_KEY, Theater::class.java)
        }?.let {
            presenter.loadCinema(it)
        } ?: ErrorActivity.start(requireActivity())
    }

    override fun navigateToMovieDetail(cinema: Cinema) {
        requireActivity().apply {
            startActivity(MovieDetailActivity.newIntent(this, cinema))
            dismiss()
        }
    }

    override fun showCinemas(cinemas: List<Cinema>) {
        adapter.submitList(cinemas)
    }

    private fun initViews() {
        adapter = ChooseCinemasAdapter(onTheaterClicked = { presenter.selectCinema(it) })
        binding.rvCinema.adapter = adapter
    }

    companion object {
        val TAG: String? = this::class.java.canonicalName

        fun newInstance(theater: Theater) =
            ChooseCinemasBottomSheetFragment().withArgs {
                putSerializable(THEATER_KEY, theater)
            }
    }
}
