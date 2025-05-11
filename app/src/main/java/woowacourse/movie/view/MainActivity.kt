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

    private var currentFragmentTag: String? = null

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

        initBottomNavigation()

        if (savedInstanceState == null) {
            binding.bottomNavMenu.selectedItemId = R.id.menu_fragment_home
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            val fragment =
                when (item.itemId) {
                    R.id.menu_fragment_home -> HomeFragment::class.java
                    R.id.menu_fragment_history -> HistoryFragment::class.java
                    R.id.menu_fragment_settings -> SettingFragment::class.java
                    else -> return@setOnItemSelectedListener false
                }

            switchFragment(fragment)
            return@setOnItemSelectedListener true
        }
    }

    private fun switchFragment(classType: Class<out Fragment>) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)

            supportFragmentManager.findFragmentByTag(currentFragmentTag)?.let {
                hide(it)
            }

            val targetFragment = supportFragmentManager.findFragmentByTag(classType.simpleName)

            if (targetFragment == null) {
                add(R.id.main_fragment_container, classType, null, classType.simpleName)
            } else {
                show(targetFragment)
            }
            currentFragmentTag = classType.simpleName
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentFragmentTag", currentFragmentTag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentFragmentTag = savedInstanceState.getString("currentFragmentTag")
    }
}
