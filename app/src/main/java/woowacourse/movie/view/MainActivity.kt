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
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val historyFragment: HistoryFragment by lazy { HistoryFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }

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
            displayAddFragment(homeFragment)
        }

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bottomNavMenu.selectedItemId = R.id.menu_fragment_home

        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_fragment_home -> {
                    displayReplaceFragment(homeFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_fragment_history -> {
                    displayReplaceFragment(historyFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_fragment_settings -> {
                    displayReplaceFragment(settingFragment)
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
    }

    private fun displayReplaceFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
        if (currentFragment?.javaClass == fragment.javaClass) return

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment_container, fragment)
        }
    }

    private fun displayAddFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment_container, fragment)
        }
    }
}
