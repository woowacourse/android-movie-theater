package woowacourse.movie.movielist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.movie.fragment.HistoryFragment
import woowacourse.movie.movie.fragment.HomeFragment
import woowacourse.movie.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.navigation_bar)
        bottomNav.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.history -> {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(R.id.fragment_container_view, HistoryFragment())
                        }
                    }

                    R.id.home -> {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(R.id.fragment_container_view, HomeFragment())
                        }
                    }
                    R.id.setting -> {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace(R.id.fragment_container_view, SettingFragment())
                        }
                    }
                }
                true
            }
            selectedItemId = R.id.history
        }
    }

    companion object {
        private const val MOVIE_KEY = "movie"
    }
}
