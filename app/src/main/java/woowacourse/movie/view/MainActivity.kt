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
    private var activeTag: String? = null

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
        outState.putString(KEY_FRAGMENT_TAG, activeTag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        activeTag = savedInstanceState.getString(KEY_FRAGMENT_TAG)
    }

    private fun initBottomNavigation() {
        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_fragment_home -> {
                    HomeFragment::class.java.let { switchFragment(it, it.simpleName) }
                    return@setOnItemSelectedListener true
                }

                R.id.menu_fragment_history -> {
                    HistoryFragment::class.java.let { switchFragment(it, it.simpleName) }
                    return@setOnItemSelectedListener true
                }

                R.id.menu_fragment_settings -> {
                    SettingFragment::class.java.let { switchFragment(it, it.simpleName) }
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
    }

    private fun switchFragment(
        target: Class<out Fragment>,
        tag: String,
    ) {
        if (activeTag == tag) return

        val activeFragment = supportFragmentManager.findFragmentByTag(activeTag)
        val targetFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.commit {
            activeFragment?.let { hide(it) }

            if (targetFragment == null) {
                add(R.id.main_fragment_container, target, null, tag)
            } else {
                show(targetFragment)
            }
        }
        activeTag = tag
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val KEY_FRAGMENT_TAG = "fragment"
    }
}
