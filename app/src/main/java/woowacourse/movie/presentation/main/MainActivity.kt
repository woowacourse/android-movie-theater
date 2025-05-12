package woowacourse.movie.presentation.main

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.DataBindingBaseActivity
import woowacourse.movie.presentation.bookinghistory.BookingHistoryFragment
import woowacourse.movie.presentation.movies.MoviesFragment
import woowacourse.movie.presentation.setting.SettingFragment

class MainActivity : DataBindingBaseActivity() {
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScreen(binding.root)
        setBottomNavigationView()
        requestNotificationPermission()
        requestExactAlarmPermission()

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

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                showAlarmPermissionDialog()
            }
        }
    }

    private fun showAlarmPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_title))
            .setMessage(getString(R.string.permission_description))
            .setPositiveButton(getString(R.string.permission_confirm)) { _, _ -> openAlarmSettings() }
            .setNegativeButton(getString(R.string.permission_cancel), null)
            .setCancelable(false)
            .show()
    }

    private fun openAlarmSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent =
                Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    data = "package:$packageName".toUri()
                }
            startActivity(intent)
        }
    }
}
