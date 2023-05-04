package woowacourse.movie.fragment.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.activity.MovieReservationActivity
import woowacourse.movie.fragment.movielist.adapter.MovieAdapter
import woowacourse.movie.view.data.MovieListViewData
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.MovieViewDatas

class MovieListFragment : Fragment(), MovieListContract.View {
    override lateinit var presenter: MovieListContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MovieListPresenter(this)
        presenter.makeMovieRecyclerView()
    }

    override fun makeMovieRecyclerView(movieViewDatas: MovieViewDatas) {
        val movieRecyclerView = requireView().findViewById<RecyclerView>(R.id.main_movie_list)
        movieRecyclerView.adapter = MovieAdapter(movieViewDatas) {
            presenter.setOnClickListener(it)
        }
    }

    override fun setOnMovieClickListener(data: MovieListViewData) {
        MovieReservationActivity.from(
            requireContext(), data as MovieViewData
        ).run {
            startActivity(this)
        }
    }
}
