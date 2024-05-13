package woowacourse.movie.presentation

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.data.datastore.DefaultNotificationDataStore
import woowacourse.movie.data.datastore.NotificationDataStore
import woowacourse.movie.databinding.ActivityHomeBinding
import woowacourse.movie.presentation.base.BindingActivity
import woowacourse.movie.presentation.common.replaceTo
import woowacourse.movie.presentation.movieList.MovieListFragment
import woowacourse.movie.presentation.reservation.ReservationFragment
import woowacourse.movie.presentation.setting.SettingFragment

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val notificationDataStore: NotificationDataStore by lazy {
        DefaultNotificationDataStore.instance(applicationContext)
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) notificationDataStore.acceptedPushAlarm = true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replaceTo<MovieListFragment>(R.id.fragment_container_home)
            }
            binding.botNavMain.selectedItemId = R.id.movieListFragment
            replaceTo<MovieListFragment>(R.id.fragment_container_home)
        }
        initClickListener()
        requestAlarmPermission()
    }

    private fun initClickListener() {
        binding.botNavMain.setOnItemSelectedListener {
            onNavigationItemSelected(it.itemId)
        }
    }

    private fun requestAlarmPermission() {
        when {
            (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) -> return
            notificationDataStore.acceptedPushAlarm -> return
            isFirstRequest() -> requestPermissionLauncher.launch(POST_NOTIFICATIONS)
            else -> return
        }
    }

    private fun onNavigationItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.reserveFragment -> replaceTo<ReservationFragment>(R.id.fragment_container_home)
            R.id.movieListFragment -> replaceTo<MovieListFragment>(R.id.fragment_container_home)
            R.id.settingFragment -> replaceTo<SettingFragment>(R.id.fragment_container_home)
            else -> error("Invalid itemId: $itemId")
        }
        return true
    }

    private fun isFirstRequest(): Boolean =
        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) &&
                hasAccessPermission().not() &&
                shouldShowRequestPermissionRationale(POST_NOTIFICATIONS).not() &&
                notificationDataStore.hasBeenDeniedPermission.not()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun hasAccessPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        fun start(context: Context, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, HomeActivity::class.java).apply(action)
            context.startActivity(intent)
        }
    }
}
