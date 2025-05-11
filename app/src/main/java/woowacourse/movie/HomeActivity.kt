package woowacourse.movie

import android.app.AlarmManager
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
        setUpNotificationsPermission()

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

    private fun setUpNotificationsPermission() {
        val permission = "android.permission.POST_NOTIFICATIONS"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun showNotificationPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("알림 권한이 필요합니다")
            .setMessage("예매 알림을 받으시려면 알림 권한을 허용해주세요.")
            .setPositiveButton("설정으로 이동") { _, _ ->
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }
            .setNegativeButton("취소", null)
            .show()
    }
}
