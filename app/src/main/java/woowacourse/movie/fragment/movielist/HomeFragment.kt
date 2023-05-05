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
import woowacourse.movie.model.Ad
import woowacourse.movie.movie.MovieMockData

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMovieRecyclerView(view)
    }

    private fun setMovieRecyclerView(view: View) {
        val movieRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_movie_list)

        val movieRecyclerViewAdapter = MovieRecyclerViewAdapter(
            MovieMockData.movies10000,
            Ad.dummyAd,
            onClickMovie(view),
            onClickAd(),
        )
        movieRecyclerView.adapter = movieRecyclerViewAdapter
    }

    private fun onClickAd() = { item: Ad ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        this.startActivity(intent)
    }

    private fun onClickMovie(view: View) = { position: Int ->
        val intent = MovieDetailActivity.getIntent(view.context, MovieMockData.movies10000[position])
        this.startActivity(intent)
    }
}
