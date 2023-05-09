package woowacourse.movie.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.model.MovieListModel.AdModel
import woowacourse.movie.model.MovieListModel.MovieModel
import woowacourse.movie.ui.home.adapter.HomeAdapter
import woowacourse.movie.ui.home.presenter.HomeContract
import woowacourse.movie.ui.home.presenter.HomePresenter
import woowacourse.movie.ui.moviedetail.MovieDetailActivity

class HomeFragment : Fragment(), HomeContract.View {
    override lateinit var homeAdapter: HomeAdapter
    override val presenter: HomeContract.Presenter by lazy { HomePresenter(this) }
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.initAdapter()
        attachAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun attachAdapter() {
        binding.mainMovieList.adapter = homeAdapter
    }

    override fun moveToDetailActivity(movie: MovieModel) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java).apply {
            putExtra(KEY_MOVIE, movie)
        }

        startActivity(intent)
    }

    override fun moveToWebPage(ad: AdModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ad.url))
        startActivity(intent)
    }

    companion object {
        const val KEY_MOVIE = "movie"
    }
}
