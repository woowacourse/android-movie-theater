package woowacourse.movie.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.utils.MovieUtils.navigateToBottomMenu
import woowacourse.movie.view.history.ReservationHistoryFragment
import woowacourse.movie.view.home.HomeFragment
import woowacourse.movie.view.result.ReservationResultActivity
import woowacourse.movie.view.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.main = this
        setBottomNavigationView()
        requestNotificationPermission()
    }

    @SuppressLint("ResourceType")
    private fun setBottomNavigationView() {
        val fragments =
            mapOf(
                R.id.fragment_home to HomeFragment(),
                R.id.fragment_list to ReservationHistoryFragment(),
                R.id.fragment_setting to SettingFragment(),
            )
        binding.bottomNavigationMain.setOnItemSelectedListener { item ->
            fragments[item.itemId]?.let { currentFragment ->
                supportFragmentManager.navigateToBottomMenu(
                    R.id.fragment_container_main,
                    currentFragment,
                )
                true
            } ?: false
        }
        binding.bottomNavigationMain.selectedItemId = R.id.fragment_home
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Toast.makeText(this, "알림 권한 요청을 거부하였습니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { _: Boolean -> }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ReservationResultActivity::class.java)
        }
    }
}
