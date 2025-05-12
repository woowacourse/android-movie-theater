package woowacourse.movie.view

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
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
    private lateinit var binding: ActivityMainBinding

    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initBottomNavigation()

        if (savedInstanceState == null) {
            switchFragment(HomeFragment::class.java)
            binding.bottomNavMenu.selectedItemId = R.id.menu_fragment_home
        }

        checkExactAlarmPermission()
    }

    private fun checkExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (!alarmManager.canScheduleExactAlarms()) {
                showExactAlarmPermissionDialog()
            }
        }
    }

    private fun showExactAlarmPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("정확한 알람 권한 필요")
            .setMessage("예매 알림을 받으려면 '정확한 알람' 권한이 필요합니다.\n설정 화면으로 이동하시겠습니까?")
            .setPositiveButton("설정으로 이동") { _, _ ->
                val intent =
                    Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                        data = "package:$packageName".toUri()
                    }
                startActivity(intent)
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun initBottomNavigation() {
        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            val fragment =
                when (item.itemId) {
                    R.id.menu_fragment_home -> HomeFragment::class.java
                    R.id.menu_fragment_history -> HistoryFragment::class.java
                    R.id.menu_fragment_settings -> SettingFragment::class.java
                    else -> return@setOnItemSelectedListener false
                }

            switchFragment(fragment)
            return@setOnItemSelectedListener true
        }
    }

    private fun switchFragment(classType: Class<out Fragment>) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)

            supportFragmentManager.fragments.forEach { fragment ->
                hide(fragment)
            }

            val targetFragment = supportFragmentManager.findFragmentByTag(classType.simpleName)

            if (targetFragment == null) {
                add(R.id.main_fragment_container, classType, null, classType.simpleName)
            } else {
                show(targetFragment)
            }
            currentFragmentTag = classType.simpleName
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentFragmentTag", currentFragmentTag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentFragmentTag = savedInstanceState.getString("currentFragmentTag")
    }
}
