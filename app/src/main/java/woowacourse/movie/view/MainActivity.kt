package woowacourse.movie.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.BookingHistoryFragment
import woowacourse.movie.HomeFragment
import woowacourse.movie.R
import woowacourse.movie.SettingFragment
import woowacourse.movie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val historyFragment = BookingHistoryFragment()
        val homeFragment = HomeFragment()
        val settingFragment = SettingFragment()

        if (savedInstanceState == null) {
            replaceFragment(homeFragment)
            binding.navigation.selectedItemId = R.id.action_home
        }

        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_history -> {
                    replaceFragment(historyFragment)
                    true
                }

                R.id.action_home -> {
                    replaceFragment(homeFragment)
                    true
                }

                R.id.action_setting -> {
                    replaceFragment(settingFragment)
                    true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, fragment)
        }
    }
}
