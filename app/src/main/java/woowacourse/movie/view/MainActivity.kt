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
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val homeFragment: Fragment by lazy {
        findOrCreateFragment(
            HomeFragment::class.java.simpleName,
            HomeFragment(),
        )
    }
    private val historyFragment: Fragment by lazy {
        findOrCreateFragment(
            HistoryFragment::class.java.simpleName,
            HistoryFragment(),
        )
    }
    private val settingFragment: Fragment by lazy {
        findOrCreateFragment(
            SettingFragment::class.java.simpleName,
            SettingFragment(),
        )
    }
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_FRAGMENT_TAG, activeFragment?.tag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.let {
            activeFragment =
                supportFragmentManager.findFragmentByTag(it.getString(KEY_FRAGMENT_TAG))
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_fragment_home -> {
                    switchFragment(homeFragment, HomeFragment::class.java.simpleName)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_fragment_history -> {
                    switchFragment(historyFragment, HistoryFragment::class.java.simpleName)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_fragment_settings -> {
                    switchFragment(settingFragment, SettingFragment::class.java.simpleName)
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
    }

    private fun switchFragment(
        target: Fragment,
        tag: String,
    ) {
        if (activeFragment == target) return

        supportFragmentManager.commit {
            activeFragment?.let { hide(it) }

            if (supportFragmentManager.findFragmentByTag(target.tag) == null) {
                add(R.id.main_fragment_container, target, tag)
                println("add가 되었다.")
            } else {
                show(target)
            }
        }
        activeFragment = target
    }

    private fun findOrCreateFragment(
        tag: String,
        fragment: Fragment,
    ): Fragment {
        return supportFragmentManager.findFragmentByTag(tag) ?: fragment
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val KEY_FRAGMENT_TAG = "fragment"
    }
}
