package woowacourse.movie

import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.feature.history.ReservationHistoryFragment
import woowacourse.movie.feature.home.HomeFragment
import woowacourse.movie.feature.setting.SettingFragment
import woowacourse.movie.utils.MovieUtils.makeToast

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main,
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            showNotificationGrantedStatus(isGranted)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        setBottomNavigationView()
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

    @SuppressLint("ResourceType")
    private fun FragmentManager.navigateToBottomMenu(
        @LayoutRes
        fragmentLayoutResource: Int,
        nextFragment: Fragment,
    ) {
        commit {
            setReorderingAllowed(true)
            replace(fragmentLayoutResource, nextFragment)
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        if (!shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) return
        requestPermissionLauncher.launch(POST_NOTIFICATIONS)
    }

    private fun showNotificationGrantedStatus(isGranted: Boolean) {
        if (isGranted) {
            makeToast(this, getString(R.string.main_notification_permission))
        } else {
            makeToast(this, getString(R.string.main_notification_rejection))
        }
    }
}
