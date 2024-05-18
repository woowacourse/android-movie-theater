package woowacourse.movie.presentation.view.bottomNavigationBar

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R

class NavigationActivity : AppCompatActivity() {
    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.navigationView)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showToastMessage(MESSAGE_PUSH_STATE_ON)
            } else {
                showToastMessage(MESSAGE_PUSH_STATE_OFF)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setBottomNavigationView()
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.home_fragment
        }
        requestNotificationPermission()
    }

    private fun setBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.selected_fragment, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                R.id.setting_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.selected_fragment, SettingFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                R.id.reservation_list_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.selected_fragment, ReservationDetailsFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }

                else -> false
            }
        }
    }

    private fun requestNotificationPermission() {
        val isPermissionNotGranted =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED

        if (isPermissionNotGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    showToastMessage(MESSAGE_PUSH_STATE_OFF)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun showToastMessage(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val MESSAGE_PUSH_STATE_ON = "Push 알림이 On 상태입니다"
        const val MESSAGE_PUSH_STATE_OFF = "Push 알림이 Off 상태입니다"
    }
}
