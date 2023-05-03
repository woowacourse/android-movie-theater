package woowacourse.movie.view.moviemain.movielist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.model.MovieListModel
import woowacourse.movie.view.reservation.ReservationActivity

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val presenter = MovieListPresenter()
        val dataList = presenter.getMovieListData()

        val movieAdapter = MovieListAdapter(dataList, ::onClick)
        val movieListView = view.findViewById<RecyclerView>(R.id.movie_recyclerview)
        movieListView.adapter = movieAdapter
    }

    private fun onClick(item: MovieListModel) {
        when (item) {
            is MovieListModel.MovieUiModel -> {
                val intent = ReservationActivity.newIntent(requireContext(), item)
                startActivity(intent)
            }
            is MovieListModel.MovieAdModel -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                startActivity(intent)
            }
        }
    }

    companion object {
        const val TAG_MOVIE_LIST = "MOVIE_LIST"
    }
}
