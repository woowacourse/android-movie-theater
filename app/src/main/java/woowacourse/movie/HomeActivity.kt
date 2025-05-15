package woowacourse.movie

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityHomeBinding
import woowacourse.movie.helper.PermissionHelper
import woowacourse.movie.movie.MovieFragment
import woowacourse.movie.reservation.ReservationFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                showNotificationPermissionDeniedDialog()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initBinding()
        applyWindowInserts()
        setUpBottomNavigation()
        setUpNotificationsPermissions()
        createNotificationChannel()
    }

    private fun setUpBottomNavigation() {
        binding.navigationView.selectedItemId = R.id.navigation_home
        setFrag(R.id.navigation_home)

        binding.navigationView.setOnItemSelectedListener { item ->
            setFrag(item.itemId)
            true
        }
    }

    private fun applyWindowInserts() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
    }

    private fun setFrag(itemId: Int) {
        val fragment =
            when (itemId) {
                R.id.navigation_booking -> ReservationFragment()
                R.id.navigation_home -> MovieFragment()
                R.id.navigation_settings -> SettingFragment()
                else -> throw IllegalStateException()
            }
        supportFragmentManager.commit {
            replace(R.id.main_frame, fragment)
        }
    }

    private fun setUpNotificationsPermissions() {
        PermissionHelper.checkPermission(
            Build.VERSION_CODES.TIRAMISU,
            android.Manifest.permission.POST_NOTIFICATIONS,
            this,
            requestPermissionLauncher)
        checkScheduleExactAlarmPermission()
    }

    private fun checkScheduleExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
        }
    }

    private fun showNotificationPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.notification_push_title)
            .setMessage(R.string.notification_push_message)
            .setPositiveButton(R.string.notification_push_positive_button) { _, _ ->
                val intent =
                    Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    }
                startActivity(intent)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "예약 알림"
            val descriptionText = "영화 예약 30분 전 알림 채널"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel("channel_id", name, importance).apply {
                    description = descriptionText
                }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
