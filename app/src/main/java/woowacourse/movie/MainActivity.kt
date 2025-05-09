package woowacourse.movie

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMovieBinding
import woowacourse.movie.movie.MovieFragment
import woowacourse.movie.reservation.ReservationListFragment
import woowacourse.movie.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        setUpUi()

        sharedPreference = getSharedPreferences("settings", MODE_PRIVATE)
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.menu_home
            supportFragmentManager.commit {
                add(R.id.fragment_view, MovieFragment())
            }
        }

        initBottomNav()
        requestNotificationPermission()
    }

    private fun setUpUi() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }

    private fun initBottomNav() {
        val homeFragment = MovieFragment()
        val settingFragment = SettingFragment()
        val reservationListFragment = ReservationListFragment()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    replaceFragment(homeFragment)
                }

                R.id.menu_setting -> {
                    replaceFragment(settingFragment)
                }

                R.id.menu_reserve_list -> {
                    replaceFragment(reservationListFragment)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_view, fragment)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    showAlarmPermissionDialog()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (!isGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    !shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                ) {
                    showAlarmPermissionDialog()
                }
            }
        }

    private fun moveToAppSetting() {
        val intent =
            Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }
        startActivity(intent)
    }

    private fun showAlarmPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("알림 권한 요청")
            .setMessage("Movie에서 알림 권한이 필요합니다.\n알림을 보내도록 허용하시겠습니까?")
            .setPositiveButton("허용") { _, _ ->
                moveToAppSetting()
            }
            .setNegativeButton("허용 안함") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}
