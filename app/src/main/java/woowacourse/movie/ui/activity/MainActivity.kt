package woowacourse.movie.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.contract.main.MainContract
import woowacourse.movie.permission.SinglePermissionRequester
import woowacourse.movie.presenter.main.MainPresenter
import woowacourse.movie.ui.fragment.FragmentType
import woowacourse.movie.ui.fragment.reservationlist.ReservationListFragment.Companion.KEY_UPDATE_RESERVATION_ITEM
import woowacourse.movie.ui.storage.SettingsStorage
import woowacourse.movie.ui.utils.showSnack

class MainActivity : AppCompatActivity(), MainContract.View {
    private val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.main_bottom_navigation) }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean -> SettingsStorage.editPushNotification(isGranted) }
    private val selfPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            setSelfRequestPermission()
        }

    override lateinit var presenter: MainContract.Present

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)

        requestPermission()

        val itemId = savedInstanceState?.getInt(KEY_INSTANCE_ITEM_ID) ?: R.id.bottom_item_home
        initFragment(itemId)

        bottomNavigationView.setOnItemSelectedListener {
            val type = getFragmentType(it.itemId)
            presenter.setFragment(type)
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INSTANCE_ITEM_ID, bottomNavigationView.selectedItemId)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent?.type == MovieTicketActivity.TYPE_MOVIE_TICKET) {
            supportFragmentManager.setFragmentResult(
                KEY_UPDATE_RESERVATION_ITEM,
                bundleOf()
            )
        }
    }

    internal fun requestPermission() {
        SinglePermissionRequester.requestPermission(
            this,
            SinglePermissionRequester.NOTIFICATION_PERMISSION,
            Build.VERSION_CODES.TIRAMISU,
            ::actionGrantedPermission,
            ::actionDeniedPermission
        )
    }

    private fun actionGrantedPermission() {
        requestPermissionLauncher.launch(SinglePermissionRequester.NOTIFICATION_PERMISSION)
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
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)

        selfPermissionLauncher.launch(intent)
    }

    private fun setSelfRequestPermission() {
        if (!SinglePermissionRequester.checkDeniedPermission(
                this,
                SinglePermissionRequester.NOTIFICATION_PERMISSION
            )
        )
            SettingsStorage.editPushNotification(true)
        else
            SettingsStorage.editPushNotification(false)
    }

    private fun initFragment(itemId: Int) {
        bottomNavigationView.selectedItemId = itemId
        val type = getFragmentType(itemId)
        presenter.setFragment(type)
    }

    private fun getFragmentType(itemId: Int): FragmentType =
        when (itemId) {
            R.id.bottom_item_list -> FragmentType.RESERVATION_LIST
            R.id.bottom_item_home -> FragmentType.HOME
            R.id.bottom_item_settings -> FragmentType.SETTING
            else -> throw IllegalArgumentException(getString(R.string.error_not_existing_item_id))
        }

    override fun showFragment(type: FragmentType) {
        supportFragmentManager.findFragmentByTag(type.tag)?.let {
            changeFragment(it, false, type)
            return
        }
        changeFragment(presenter.createFragment(type), true, type)
    }

    private fun changeFragment(fragment: Fragment, isCreated: Boolean, type: FragmentType) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)

            if (isCreated) add(R.id.main_fragment_container_view, fragment, type.tag)

            supportFragmentManager.fragments.forEach(::hide)
            show(fragment)
        }
    }

    companion object {
        private const val KEY_INSTANCE_ITEM_ID = "item_id"

        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
