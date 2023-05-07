package woowacourse.movie.presentation.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.presentation.model.Movie
import woowacourse.movie.presentation.view.main.home.moviedetail.MovieDetailActivity

class MovieListFragment : Fragment(), MovieListContract.View {
    private val presenter by lazy { MovieListPresenter(this) }
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getMovieData()
    }

    override fun setRecyclerView(movies: List<Movie>) {
        val adapter = MovieListAdapter(
            movies,
            ContextCompat.getDrawable(requireContext(), R.drawable.advertise_wooteco)!!
        ) {
            val intent = MovieDetailActivity.getIntent(requireContext())
            intent.putExtra(MovieDetailActivity.MOVIE_DATA_INTENT_KEY, it)
            requireContext().startActivity(intent)
        }
        binding.rvMovieList.adapter = adapter
    }
}
