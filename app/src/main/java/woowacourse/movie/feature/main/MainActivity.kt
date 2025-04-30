package woowacourse.movie.feature.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import woowacourse.movie.R
import woowacourse.movie.feature.BookingHistoryFragment
import woowacourse.movie.feature.SettingFragment
import woowacourse.movie.feature.home.view.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fcv_main, HomeFragment())
            }
        }

        findViewById<NavigationBarView>(R.id.bottom_nav_main).selectedItemId = R.id.item_home
        findViewById<NavigationBarView>(R.id.bottom_nav_main).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_booking_history -> {
                    replaceFragment(BookingHistoryFragment())
                    true
                }
                R.id.item_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.item_setting -> {
                    replaceFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .commit()
    }
}
