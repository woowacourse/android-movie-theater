package woowacourse.movie.view.movie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMoviesBinding
import woowacourse.movie.view.ReservationListFragment
import woowacourse.movie.view.SettingFragment

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesBinding
    private val movieListFragment: ReservationListFragment by lazy { ReservationListFragment() }
    private val moviesFragment: MoviesFragment by lazy { MoviesFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupBinding()
        setupWindowInsets()
        setupBottomNavigation()
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_movies
        }
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies)
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.clMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            val fragment =
                when (it.itemId) {
                    R.id.fragment_movies -> moviesFragment
                    R.id.fragment_list -> movieListFragment
                    R.id.fragment_setting -> settingFragment
                    else -> throw IllegalArgumentException(ERROR_INVALID_FRAGMENT)
                }
            replaceFragment(fragment)
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_main, fragment)
        }
    }

    companion object {
        private const val ERROR_INVALID_FRAGMENT = "알 수 없는 프래그먼트 입니다"
    }
}
