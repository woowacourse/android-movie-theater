package woowacourse.movie.list.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.TheaterFragment
import woowacourse.movie.list.adapter.MovieAdapter
import woowacourse.movie.list.contract.MovieListContract
import woowacourse.movie.list.model.Advertisement
import woowacourse.movie.list.model.Movie
import woowacourse.movie.list.presenter.MovieListPresenter

class MovieListActivity : AppCompatActivity(), MovieListContract.View {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

//    private lateinit var binding: ActivityMovieListBinding
//    lateinit var darkView: TextView
    override val presenter = MovieListPresenter(this)
    lateinit var theaterFragmentLayout: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

//        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
//        binding.movieRecyclerView
        recyclerView = findViewById(R.id.movie_recycler_view)
        theaterFragmentLayout = findViewById(R.id.fragment_container_view)
        presenter.setMoviesInfo()
        presenter.setListViewClickListenerInfo()
//        darkView = findViewById(R.id.dark_view)
    }

//    fun showDarkLayer() {
//        binding.dark.visibility = View.VISIBLE
//    }

    override fun showMoviesInfo(
        movies: List<Movie>,
        advertisements: List<Advertisement>,
    ) {
        movieAdapter = MovieAdapter(movies, advertisements)

        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun setOnListViewClickListener() {
        movieAdapter.setItemClickListener(
            object : MovieAdapter.OnItemClickListener {
                override fun onClick(movieId: Long) {
                    val theaterFragment = TheaterFragment()
                    val bundle = Bundle()
                    bundle.putLong(EXTRA_MOVIE_ID_KEY_TO_FRAGMENT, movieId)
                    theaterFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, theaterFragment)
                        .commit()
                    theaterFragmentLayout.visibility = View.VISIBLE
//                    darkView.visibility = View.VISIBLE
                }
            },
        )
    }

    companion object {
        const val EXTRA_MOVIE_ID_KEY = "movie_id_key"
        const val EXTRA_MOVIE_ID_KEY_TO_FRAGMENT = "movie_id_key_to_fragment"
    }
}
