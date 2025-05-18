package woowacourse.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.home.HomeFragment
import woowacourse.movie.view.reservationDetails.ReservationDetailsFragment
import woowacourse.movie.view.setting.SettingFragment

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

        setupListener()
        if (savedInstanceState == null) {
            binding.mainBottomNavigationBar.selectedItemId =
                R.id.bottom_navigation_reservation_details
        }
    }

    private fun setupListener() {
        binding.mainBottomNavigationBar.setOnItemSelectedListener { item ->
            return@setOnItemSelectedListener when (item.itemId) {
                R.id.bottom_navigation_reservation_details -> {
                    replaceFragment<ReservationDetailsFragment>()
                    true
                }

                R.id.bottom_navigation_home -> {
                    replaceFragment<HomeFragment>()
                    true
                }

                R.id.bottom_navigation_setting -> {
                    replaceFragment<SettingFragment>()
                    true
                }

                else -> false
            }
        }
    }

    private inline fun <reified T : Fragment> replaceFragment() {
        val tag: String = T::class.java.name
        val fragment =
            supportFragmentManager.findFragmentByTag(tag) as? T ?: T::class.java.newInstance()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            supportFragmentManager.fragments.forEach { hide(it) }
            if (fragment.isAdded) {
                show(fragment)
            } else {
                add(binding.mainFragmentContainer.id, fragment, tag)
            }
        }
    }

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
    }
}
