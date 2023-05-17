package woowacourse.movie.presentation.view.main.home.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.Movie
import woowacourse.movie.presentation.model.Theater
import woowacourse.movie.presentation.view.main.home.moviedetail.MovieDetailActivity

class TheaterFragment : BottomSheetDialogFragment(), TheaterContract.View {
    private lateinit var binding: FragmentTheaterBinding
    private val presenter: TheaterContract.Presenter by lazy {
        TheaterPresenter(this, movie)
    }

    private val movie: Movie? by lazy {
        requireArguments().getParcelableCompat(KEY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTheaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getTheaters()
    }

    override fun showTheatersView(theaters: List<Theater>, movieSchedule: List<List<String>>) {
        binding.rvTheater.adapter = TheaterAdapter(theaters, movieSchedule) { theater, schedules ->
            val intent = MovieDetailActivity.getIntent(requireContext())
                .putExtra(MovieDetailActivity.THEATER_INTENT_KEY, theater)
                .putStringArrayListExtra(
                    MovieDetailActivity.MOVIE_SCHEDULES_KEY,
                    ArrayList(schedules)
                )
                .putExtra(MovieDetailActivity.MOVIE_INTENT_KEY, movie)
            requireContext().startActivity(intent)
        }
    }

    override fun finishErrorView() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }

    companion object {
        private const val KEY = "key"
        fun newInstance(value: Movie) = TheaterFragment().apply {
            arguments = bundleOf().apply {
                putParcelable(KEY, value)
            }
        }
    }
}