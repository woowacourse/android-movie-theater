package woowacourse.movie.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.contract.MainContract
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presenter.MainPresenter
import woowacourse.movie.view.fragment.MoviesFragment
import woowacourse.movie.view.fragment.ReservationListFragment
import woowacourse.movie.view.fragment.SettingFragment

class MainActivity : AppCompatActivity(), MainContract.View {

    override val presenter: MainContract.Presenter by lazy { MainPresenter(this) }
    private lateinit var binding: ActivityMainBinding
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
    private var saveState: Bundle? = null
    private val moviesFragment = MoviesFragment()
    private val settingFragment = SettingFragment()
    private val reservationListFragment = ReservationListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        saveState = savedInstanceState
        initView()
    }

    fun initView(){
        binding.bottomNavigation.setOnItemSelectedListener {
            presenter.onClickBottomNavigationItem(it.itemId)
        }
        presenter.updateFragmentView()
        requestNotificationPermission()
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

    private fun showNotificationPermissionDialog() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            Toast.makeText(
                this,
                getString(R.string.notification_refuse_two_time_warning_message),
                Toast.LENGTH_LONG
            ).show()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun changeFragmentByItemID(itemId: Int) {
        when (itemId) {
            R.id.home_bottom_navigation -> replaceFragment(moviesFragment)
            R.id.setting_bottom_navigation -> replaceFragment(settingFragment)
            R.id.reservation_list_bottom_navigation -> replaceFragment(reservationListFragment)
        }
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment, fragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_ITEM_ID, binding.bottomNavigation.selectedItemId)
    }

    override fun setSelectedFragmentView(selectedItemId: Int) {
        binding.bottomNavigation.selectedItemId = selectedItemId
    }

    override fun getSavedNavigationItemId(): Int {
        return saveState?.getInt(KEY_SELECTED_ITEM_ID) ?: R.id.home_bottom_navigation
    }

    companion object {
        private const val KEY_SELECTED_ITEM_ID = "KEY_SELECTED_ITEM_ID"
    }
}
