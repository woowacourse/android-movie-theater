package woowacourse.movie.presentation.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.HistoryFragment
import woowacourse.movie.presentation.activities.main.fragments.HomeFragment
import woowacourse.movie.presentation.activities.main.fragments.SettingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.home

        bottomNavigationView.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.menu -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container_view, HistoryFragment.newInstance())
                    }
                }
                R.id.home -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container_view, HomeFragment.newInstance())
                    }
                }
                R.id.setting -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container_view, SettingFragment.newInstance())
                    }
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}
