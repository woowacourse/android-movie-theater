package woowacourse.movie.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieBookingBinding
import woowacourse.movie.ui.movielist.view.MovieListFragment

class MovieBookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding =
            DataBindingUtil.setContentView(
                this@MovieBookingActivity,
                R.layout.activity_movie_booking,
            )

        applyWindowInsets()

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.main_fragment_container_view, MovieListFragment())
            }
        }
        binding.navigation.selectedItemId = R.id.navigation_home

        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.main_fragment_container_view, MovieListFragment())
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
