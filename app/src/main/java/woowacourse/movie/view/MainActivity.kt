package woowacourse.movie.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.home.HomeFragment
import woowacourse.movie.view.reservation.history.HistoryFragment
import woowacourse.movie.view.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.main_fragment_container, HomeFragment())
            }
        }

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bottomNavMenu.selectedItemId = R.id.menu_fragment_home

        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            val fragment =
                when (item.itemId) {
                    R.id.menu_fragment_home -> HomeFragment()
                    R.id.menu_fragment_history -> HistoryFragment()
                    R.id.menu_fragment_settings -> SettingFragment()
                    else -> return@setOnItemSelectedListener false
                }

            replaceFragment(fragment)
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment_container, fragment)
        }
    }
}
