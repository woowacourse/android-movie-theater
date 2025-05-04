package woowacourse.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.home.HomeFragment
import woowacourse.movie.view.reservationDetails.ReservationDetailsFragment
import woowacourse.movie.view.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()
    private val settingFragment = SettingFragment()
    private val reservationDetailsFragment = ReservationDetailsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.mainBottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_navigation_reservation_details ->
                    return@setOnItemSelectedListener replaceFragment(reservationDetailsFragment)

                R.id.bottom_navigation_home ->
                    return@setOnItemSelectedListener replaceFragment(homeFragment)

                R.id.bottom_navigation_setting ->
                    return@setOnItemSelectedListener replaceFragment(settingFragment)
            }
            false
        }
    }

    private fun replaceFragment(selectedFragment: Fragment): Boolean {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(binding.mainFragmentContainer.id, selectedFragment)
        }
        return true
    }

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
    }
}
