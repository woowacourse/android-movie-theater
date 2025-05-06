package woowacourse.movie.view.movie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMoviesBinding
import woowacourse.movie.view.ReservationListFragment
import woowacourse.movie.view.SettingFragment

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesBinding
    private val moviesFragment: MoviesFragment by lazy { MoviesFragment() }
    private val reservationListFragment: ReservationListFragment by lazy { ReservationListFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupBinding()
        setupWindowInsets()
        initFragment(savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBinding() {
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.activityMoviesRootLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.activity_movies_fragment_container, moviesFragment, FRAGMENT_MOVIES)
            }
        }
    }

    private fun setupBottomNavigation() {
        binding.activityMoviesBottomNavigation.selectedItemId = R.id.fragment_movies
        binding.activityMoviesBottomNavigation.setOnItemSelectedListener { menuItem ->
            val selectedFragment =
                when (menuItem.itemId) {
                    R.id.fragment_movies -> {
                        supportFragmentManager.findFragmentByTag(FRAGMENT_MOVIES) ?: moviesFragment
                    }
                    R.id.fragment_list -> {
                        supportFragmentManager.findFragmentByTag(FRAGMENT_RESERVATION_LIST) ?: reservationListFragment
                    }
                    R.id.fragment_setting -> {
                        supportFragmentManager.findFragmentByTag(FRAGMENT_SETTING) ?: settingFragment
                    }
                    else -> throw IllegalArgumentException(ERROR_INVALID_FRAGMENT)
                }
            replaceFragment(selectedFragment)
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.activity_movies_fragment_container, fragment, getFragmentTag(fragment))
        }
    }

    private fun getFragmentTag(fragment: Fragment): String =
        when (fragment) {
            is MoviesFragment -> FRAGMENT_MOVIES
            is ReservationListFragment -> FRAGMENT_RESERVATION_LIST
            is SettingFragment -> FRAGMENT_SETTING
            else -> throw IllegalArgumentException(ERROR_INVALID_FRAGMENT)
        }

    companion object {
        private const val ERROR_INVALID_FRAGMENT = "알 수 없는 프래그먼트 입니다"
        private const val FRAGMENT_MOVIES = "movies"
        private const val FRAGMENT_RESERVATION_LIST = "reservation_list"
        private const val FRAGMENT_SETTING = "setting"
    }
}
