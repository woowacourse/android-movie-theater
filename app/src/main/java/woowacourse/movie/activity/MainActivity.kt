package woowacourse.movie.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.SettingPreferenceManager
import woowacourse.movie.fragment.MoviesFragment
import woowacourse.movie.fragment.ReservationListFragment
import woowacourse.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    private val bottomNavigation: BottomNavigationView by lazy { findViewById(R.id.bottom_navigation) }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SettingPreferenceManager.inIt(this)
        replaceFragment(ReservationListFragment())
        setOnBottomNavigationClickListener()
        requestNotificationPermission()
    }

    private fun setOnBottomNavigationClickListener() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_bottom_navigation -> replaceFragment(MoviesFragment())
                R.id.setting_bottom_navigation -> replaceFragment(SettingFragment())
                R.id.reservation_list_bottom_navigation -> replaceFragment(ReservationListFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment, fragment)
        }
    }

    private fun requestNotificationPermission() {
        if (!isPermissionGranted()) {
            showNotificationPermissionDialog()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_ITEM_ID, bottomNavigation.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedItemId = savedInstanceState.getInt(KEY_SELECTED_ITEM_ID)
        bottomNavigation.selectedItemId = selectedItemId
    }

    private fun showNotificationPermissionDialog() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            Toast.makeText(this, getString(R.string.notification_refuse_two_time_warning_message), Toast.LENGTH_LONG).show()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    companion object {
        private const val KEY_SELECTED_ITEM_ID = "KEY_SELECTED_ITEM_ID"
    }
}
