package woowacourse.movie.list.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.TheaterFragment
import woowacourse.movie.list.adapter.MovieAdapter
import woowacourse.movie.list.contract.MovieListContract
import woowacourse.movie.list.model.Advertisement
import woowacourse.movie.list.model.Movie
import woowacourse.movie.list.presenter.MovieListPresenter
import woowacourse.movie.reservation.view.MovieReservationActivity

class MovieListActivity : AppCompatActivity(), MovieListContract.View {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    override val presenter = MovieListPresenter(this)
    lateinit var movieIntent: Intent
    lateinit var theaterFragmentLayout: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        movieIntent = Intent(this, MovieReservationActivity::class.java)
        recyclerView = findViewById(R.id.movie_recycler_view)
        theaterFragmentLayout = findViewById(R.id.fragment_container_view)
        presenter.setMoviesInfo()
        presenter.setListViewClickListenerInfo(savedInstanceState)

//        if (savedInstanceState == null) { // 처음 만들 때만 추가해라
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add(R.id.fragment_container_view, TheaterFragment())
//            }
//        }
    }

    override fun showMoviesInfo(
        movies: List<Movie>,
        advertisements: List<Advertisement>,
    ) {
        movieAdapter = MovieAdapter(movies, advertisements)

        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun setOnListViewClickListener(savedInstanceState: Bundle?) {
        movieAdapter.setItemClickListener(
            object : MovieAdapter.OnItemClickListener {
                override fun onClick(movieId: Int) {
                    // if (savedInstanceState == null) {
//                    movieIntent.putExtra(EXTRA_MOVIE_ID_KEY, position.toLong())
//                    this@MovieListActivity.startActivity(movieIntent)
                    val theaterFragment = TheaterFragment()
                    val bundle = Bundle()
                    bundle.putInt("key", movieId)
                    theaterFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, theaterFragment)
                        .commit()
                    theaterFragmentLayout.visibility = View.VISIBLE
                }
            },
        )
    }

    companion object {
        const val EXTRA_MOVIE_ID_KEY = "movie_id_key"
    }
}
