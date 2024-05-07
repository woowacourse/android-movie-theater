package woowacourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.movielist.MovieListFragment
import woowacourse.movie.reservationlist.ReservationListFragment
import woowacourse.movie.reservationlist.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var movieListFragment: MovieListFragment
    private lateinit var settingMovieFragment: SettingFragment
    private lateinit var reservationListFragment: ReservationListFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieListFragment = MovieListFragment()
        settingMovieFragment = SettingFragment()
        reservationListFragment = ReservationListFragment()

        binding.bnvMain.selectedItemId = R.id.menu_home
        supportFragmentManager.findFragmentById(R.id.fcv_main) ?: changeFragment(
            movieListFragment,
        )
        setNavigation()
    }

    private fun setNavigation() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.menu_home ->
                        movieListFragment

                    R.id.menu_setting ->
                        settingMovieFragment

                    R.id.menu_reservation_list ->
                        reservationListFragment

                    else -> movieListFragment
                },
            )
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_main, fragment)
            addToBackStack(null)
        }
    }
}
