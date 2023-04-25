package woowacourse.movie.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.domain.AdvertisementMock
import woowacourse.movie.domain.MovieMock
import woowacourse.movie.domain.advertismentPolicy.MovieAdvertisementPolicy
import woowacourse.movie.view.MovieAdapter
import woowacourse.movie.view.data.MovieListViewData
import woowacourse.movie.view.data.MovieListViewType
import woowacourse.movie.view.data.MovieViewData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeMovieRecyclerView()
        makeBottomNavigationView()
    }

    private fun makeMovieRecyclerView() {
        val movies = List(2500) { MovieMock.createMovies() }.flatten()
        val advertisementDatas = AdvertisementMock.createAdvertisements()
        val advertisementPolicy = MovieAdvertisementPolicy(MOVIE_COUNT, ADVERTISEMENT_COUNT)

        val movieRecyclerView = findViewById<RecyclerView>(R.id.main_movie_list)
        movieRecyclerView.adapter = MovieAdapter(
            movies, advertisementDatas, advertisementPolicy, ::onClickItem
        )
    }

    private fun onClickItem(data: MovieListViewData) {
        when (data.viewType) {
            MovieListViewType.MOVIE -> MovieReservationActivity.from(this, data as MovieViewData)
                .run {
                    startActivity(this)
                }
            MovieListViewType.ADVERTISEMENT -> Unit
        }
    }

    private fun makeBottomNavigationView() {
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.main_bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.action_home
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_list -> {

                    true
                }
                R.id.action_home -> {

                    true
                }
                R.id.action_setting -> {

                    true
                }
                else -> false
            }
        }
    }

    companion object {
        private const val MOVIE_COUNT = 3
        private const val ADVERTISEMENT_COUNT = 1
    }
}
