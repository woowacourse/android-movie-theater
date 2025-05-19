package woowacourse.movie.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.history.BookingHistoryFragment
import woowacourse.movie.view.home.movies.MovieListFragment
import woowacourse.movie.view.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView(savedInstanceState)
        initNotificationChannel()
    }

    private fun initView(savedInstanceState: Bundle?) {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val historyFragment = BookingHistoryFragment()
        val movieListFragment = MovieListFragment()
        val settingFragment = SettingFragment()

        if (savedInstanceState == null) {
            replaceFragment(movieListFragment)
            binding.navigation.selectedItemId = R.id.action_home
        }

        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_history -> {
                    replaceFragment(historyFragment)
                    true
                }

                R.id.action_home -> {
                    replaceFragment(movieListFragment)
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
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    private fun initNotificationChannel() {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "booking_notification_channel"
        private const val CHANNEL_NAME = "Notification Channel"
    }
}
