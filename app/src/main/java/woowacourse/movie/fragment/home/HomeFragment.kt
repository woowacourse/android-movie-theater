package woowacourse.movie.fragment.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.Ad
import woowacourse.movie.BundleKeys
import woowacourse.movie.R
import woowacourse.movie.activity.MovieDetailActivity
import woowacourse.movie.movielist.MovieRecyclerViewAdapter

class HomeFragment : Fragment(), HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HomePresenter(this)
        setMovieRecyclerView()
    }

    private fun setMovieRecyclerView() {
        val movieRecyclerView: RecyclerView = requireView().findViewById(R.id.rv_movie_list)
        val movieRecyclerViewAdapter = MovieRecyclerViewAdapter(
            presenter.fetchMovieList(),
            presenter.fetchAd(),
            presenter.onMovieClicked(),
            presenter.onAdClicked(),
        )
        movieRecyclerView.adapter = movieRecyclerViewAdapter
        movieRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun startMovieDetailPage(): (Int) -> Unit = { position: Int ->
        val intent = MovieDetailActivity.intent(requireView().context)
        intent.putExtra(BundleKeys.MOVIE_DATA_KEY, presenter.fetchMovieList()[position])
        startActivity(intent)
    }

    override fun startAdDetailPage(): (ad: Ad) -> Unit = { ad: Ad ->
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ad.url)))
    }
}
