package woowacourse.movie.presentation.ui.main.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.woowacourse.data.datasource.movie.LocalMovieDataSource
import com.woowacourse.data.repository.movie.LocalMovieRepository
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.presentation.base.BaseFragment
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.ui.main.fragments.home.contract.HomeContract
import woowacourse.movie.presentation.ui.main.fragments.home.contract.presenter.HomePresenter
import woowacourse.movie.presentation.ui.main.fragments.home.recyclerview.MovieListAdapter
import woowacourse.movie.presentation.ui.main.fragments.theater.TheaterPickerDialog

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeContract.View {
    override val layoutResId: Int = R.layout.fragment_home
    override val presenter: HomeContract.Presenter by lazy {
        HomePresenter(
            view = this,
            movieRepository = LocalMovieRepository(LocalMovieDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.presenter = presenter
        binding.rvAdapter = MovieListAdapter()
    }

    override fun showMoreMovies(items: List<ListItem>) {
        binding.rvAdapter?.appendAll(items)
    }

    override fun showAdWebSite(item: Ad) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        startActivity(intent)
    }

    override fun showTheaterPicker(item: Movie) {
        val theaterPickerDialog = TheaterPickerDialog.getInstance(item)
        theaterPickerDialog.show(childFragmentManager, TheaterPickerDialog.TAG)
    }

    companion object {
        internal const val TAG = "HomeFragment"
    }
}
