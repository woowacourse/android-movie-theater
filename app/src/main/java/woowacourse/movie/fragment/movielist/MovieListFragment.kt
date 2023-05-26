package woowacourse.movie.fragment.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.datasource.MockAdDataSource
import woowacourse.movie.datasource.MockMovieDataSource
import woowacourse.movie.dialog.TheaterDialog
import woowacourse.movie.domain.model.TheatersMock
import woowacourse.movie.domain.repository.AdRepository
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.fragment.movielist.adapter.MovieAdapter
import woowacourse.movie.view.data.MovieListViewData
import woowacourse.movie.view.data.MovieListViewType
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.MovieViewDatas
import woowacourse.movie.view.mapper.MovieMapper.toDomain

class MovieListFragment : Fragment(), MovieListContract.View {
    override lateinit var presenter: MovieListContract.Presenter
    private val binding: FragmentMovieListBinding by lazy {
        FragmentMovieListBinding.inflate(layoutInflater)
    }
    private val theaters = TheatersMock.getTheaters()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MovieListPresenter(
            this,
            MovieRepository(MockMovieDataSource()),
            AdRepository(MockAdDataSource()),
        )
        presenter.setUpMovieList()
    }

    override fun initMovieRecyclerView(movieViewDatas: MovieViewDatas) {
        binding.mainMovieList.adapter = MovieAdapter(movieViewDatas) {
            when (it.viewType) {
                MovieListViewType.MOVIE -> onMovieClick(it)
                MovieListViewType.ADVERTISEMENT -> Unit
            }
        }
    }

    override fun onMovieClick(data: MovieListViewData) {
        val data = data as MovieViewData
        val dialog = TheaterDialog(data.toDomain(), theaters)
        dialog.isCancelable = true
        dialog.show(requireActivity().supportFragmentManager, "TheaterDialog")
    }
}
