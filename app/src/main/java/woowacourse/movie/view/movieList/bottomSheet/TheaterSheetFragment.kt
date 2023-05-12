package woowacourse.movie.view.movieList.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterSheetBinding
import woowacourse.movie.model.MovieModel
import woowacourse.movie.model.TheaterModel
import woowacourse.movie.utils.getParcelableByKey
import woowacourse.movie.view.movieDetail.MovieDetailActivity
import woowacourse.movie.view.movieList.MovieListFragment

class TheaterSheetFragment : BottomSheetDialogFragment(), TheaterSheetContract.View {
    private var _binding: FragmentTheaterSheetBinding? = null
    private val binding: FragmentTheaterSheetBinding
        get() = _binding!!

    private var movie: MovieModel? = null

    override lateinit var presenter: TheaterSheetContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            movie = it.getParcelableByKey(MovieListFragment.THEATER_SHEET_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTheaterSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = TheaterSheetPresenter(this)

        presenter.setAdapter(movie!!.theaters, ::moveToDetailActivity)
    }

    override fun setTheaterAdapter(adapter: TheaterSheetAdapter) {
        binding.rvTheater.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun moveToDetailActivity(theater: TheaterModel) {
        val intent = MovieDetailActivity.createIntent(requireActivity(), movie!!, theater)
        startActivity(intent)
        parentFragmentManager.commit { remove(this@TheaterSheetFragment) }
    }
}
