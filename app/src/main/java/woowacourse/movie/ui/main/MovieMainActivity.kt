package woowacourse.movie.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieMainBinding
import woowacourse.movie.ui.home.MovieHomeFragment

class MovieMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_main)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment_container, MovieHomeFragment())
        }
        binding.mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_booking_history -> {
                    replace(MovieHomeFragment())
                }
                R.id.menu_home -> {
                    replace(MovieHomeFragment())
                }
                R.id.menu_setting -> {
                    replace(MovieHomeFragment())
                }
                else -> replace(MovieHomeFragment())
            }
            true
        }
    }

    private fun replace(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment_container, fragment)
        }
    }
}
