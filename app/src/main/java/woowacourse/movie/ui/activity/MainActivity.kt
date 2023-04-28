package woowacourse.movie.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.ui.SinglePermissionRequester
import woowacourse.movie.ui.fragment.FragmentType
import woowacourse.movie.ui.fragment.movielist.HomeFragment
import woowacourse.movie.ui.fragment.reservationlist.ReservationListFragment
import woowacourse.movie.ui.fragment.settings.SettingsFragment
import woowacourse.movie.ui.storage.SettingsStorage
import woowacourse.movie.ui.utils.showSnack

class MainActivity : AppCompatActivity() {
    private val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.main_bottom_navigation) }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean -> SettingsStorage.editPushNotification(isGranted) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SinglePermissionRequester.requestPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
            Build.VERSION_CODES.TIRAMISU,
            ::actionGrantedPermission,
            ::actionDeniedPermission
        )

        val itemId = savedInstanceState?.getInt(KEY_INSTANCE_ITEM_ID) ?: R.id.bottom_item_home
        initFragment(itemId)

        bottomNavigationView.setOnItemSelectedListener {
            val type = getFragmentType(it.itemId)
            changeFragment(type)
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INSTANCE_ITEM_ID, bottomNavigationView.selectedItemId)
    }

    private fun actionGrantedPermission() {
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    private fun actionDeniedPermission() {
        val container = findViewById<ConstraintLayout>(R.id.main_container)
        container.showSnack(
            getString(R.string.notification_permission_snackbar_message),
            getString(R.string.notification_permission_snackbar_button),
            ::openAndroidSettings
        )
    }

    private fun openAndroidSettings() {
        val uri = Uri.fromParts("package", packageName, null)
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = uri
        }
        startActivity(intent)
    }

    private fun initFragment(itemId: Int) {
        bottomNavigationView.selectedItemId = itemId
        changeFragment(getFragmentType(itemId))
    }

    private fun getFragmentType(itemId: Int): FragmentType = when (itemId) {
        R.id.bottom_item_list -> FragmentType.RESERVATION_LIST
        R.id.bottom_item_home -> FragmentType.HOME
        R.id.bottom_item_settings -> FragmentType.SETTING
        else -> throw IllegalArgumentException(getString(R.string.error_not_existing_item_id))
    }

    private fun changeFragment(currentType: FragmentType) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)

            val fragment =
                supportFragmentManager.findFragmentByTag(currentType.tag)
                    ?: createFragment(currentType).apply {
                        add(R.id.main_fragment_container_view, this, currentType.tag)
                    }
            show(fragment)

            FragmentType.values()
                .filterNot { it == currentType }
                .forEach { type ->
                    supportFragmentManager.findFragmentByTag(type.tag)?.let(::hide)
                }
        }
    }

    private fun createFragment(currentType: FragmentType): Fragment {
        return when (currentType) {
            FragmentType.RESERVATION_LIST -> ReservationListFragment()
            FragmentType.HOME -> HomeFragment()
            FragmentType.SETTING -> SettingsFragment()
        }
    }

    companion object {
        private const val KEY_INSTANCE_ITEM_ID = "item_id"

        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
