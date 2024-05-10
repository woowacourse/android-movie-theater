package woowacourse.movie

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import woowacourse.movie.base.BindingActivity
import woowacourse.movie.common.ui.replaceTo
import woowacourse.movie.databinding.ActivityHomeBinding
import woowacourse.movie.movieList.MovieListFragment
import woowacourse.movie.reservation.ReservationFragment
import woowacourse.movie.setting.SettingFragment

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            binding.botNavMain.selectedItemId = R.id.movieListFragment
            replaceTo<MovieListFragment>(R.id.fragment_container_home)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        }
        initClickListener()
    }

    private fun initClickListener() {
        binding.botNavMain.setOnItemSelectedListener {
            onNavigationItemSelected(it.itemId)
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

    private fun requestNotificationPermission() {
        if (!isPermissionGranted()) {
            if (shouldShowRequestPermissionRationale()) {
                explainPermissionNeed()
            } else {
                requestPermission()
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequestPermissionRationale(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
        )
    }

    private fun explainPermissionNeed() {
        Toast.makeText(this, "알람 설정을 해야 영화 예매 알림을 줄 수 있습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_NOTIFICATION_PERMISSION,
        )
    }

    companion object {
        private const val REQUEST_NOTIFICATION_PERMISSION = 101
    }
}
