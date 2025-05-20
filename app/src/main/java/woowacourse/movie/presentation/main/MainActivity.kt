package woowacourse.movie.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.DataBindingBaseActivity
import woowacourse.movie.presentation.bookinghistory.BookingHistoryFragment
import woowacourse.movie.presentation.movies.MoviesFragment
import woowacourse.movie.presentation.notification.AlarmPermissionHandler
import woowacourse.movie.presentation.notification.NotificationPermissionHandler
import woowacourse.movie.presentation.setting.SettingFragment

class MainActivity : DataBindingBaseActivity() {
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
    private lateinit var alarmPermissionHandler: AlarmPermissionHandler
    private lateinit var notificationPermissionHandler: NotificationPermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScreen(binding.root)
        setBottomNavigationView()

        alarmPermissionHandler = AlarmPermissionHandler(this)
        notificationPermissionHandler = NotificationPermissionHandler(this, requestPermissionLauncher)

        notificationPermissionHandler.checkAndRequest()
        alarmPermissionHandler.checkAndRequestPermission(
            onGranted = { showPermissionMessage() },
        )

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.action_home
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_list -> showFragment(BookingHistoryFragment::class.java)
                    R.id.action_home -> showFragment(MoviesFragment::class.java)
                    R.id.action_settings -> showFragment(SettingFragment::class.java)
                }
                true
            }
        }
    }

    private fun showFragment(clazz: Class<out Fragment>) {
        val findFragment = supportFragmentManager.findFragmentByTag(clazz.name)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        supportFragmentManager.fragments.forEach {
            fragmentTransaction.hide(it)
        }

        findFragment?.let {
            fragmentTransaction
                .show(it)
                .commit()
        } ?: run {
            fragmentTransaction
                .add(R.id.main_container, clazz, null, clazz.name)
                .commit()
        }
    }

    private fun showPermissionMessage() {
        Toast.makeText(this, getString(R.string.permission_confirmed), Toast.LENGTH_SHORT).show()
    }
}
