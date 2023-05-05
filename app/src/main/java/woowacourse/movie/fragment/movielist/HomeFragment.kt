package woowacourse.movie.fragment.movielist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.activity.MovieDetailActivity
import woowacourse.movie.fragment.movielist.adapter.MovieRecyclerViewAdapter
import woowacourse.movie.model.AdUIModel
import woowacourse.movie.movie.MovieMockData
import woowacourse.movie.movie.toPresentation

class HomeFragment : Fragment(), HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = HomePresenter(this)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setMovieRecyclerView(onClickMovie(), onClickAd())
    }

    private fun onClickAd() = { item: AdUIModel ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        this.startActivity(intent)
    }

    private fun onClickMovie() = { position: Int ->
        val intent =
            MovieDetailActivity.getIntent(requireContext(), MovieMockData.movies10000[position].toPresentation())
        this.startActivity(intent)
    }

    override fun setMovieRecyclerView(recyclerViewAdapter: MovieRecyclerViewAdapter) {
        val movieRecyclerView =
            requireView().findViewById<RecyclerView>(R.id.recyclerview_movie_list)
        movieRecyclerView.adapter = recyclerViewAdapter
    }
}
