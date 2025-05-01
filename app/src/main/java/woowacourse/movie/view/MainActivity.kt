package woowacourse.movie.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            binding.navigation.selectedItemId = R.id.fragment_container_view
        }

        val historyFragment = BookingHistoryFragment()
        val homeFragment = HomeFragment()
        val settingFragment = SettingFragment()

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

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)
        fragmentTransaction.commit()
    }
}
