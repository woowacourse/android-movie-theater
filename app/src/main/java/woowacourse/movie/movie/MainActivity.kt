package woowacourse.movie.movie

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.movie.history.HistoryFragment
import woowacourse.movie.movie.movielist.HomeFragment
import woowacourse.movie.movie.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestNotificationPermission()
        binding.navigationBar?.let { bindNavigationBar(it) }
    }

    private fun bindNavigationBar(bottomNav: BottomNavigationView) {
        bottomNav.run {
            setOnItemSelectedListener {
                onNavigationItemSelected(it)
                true
            }
            selectedItemId = R.id.home
        }
    }

    private fun onNavigationItemSelected(item: MenuItem) {
        fragmentRepository[item.itemId]?.let { nowFragment ->
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container_view, nowFragment)
            }
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Toast.makeText(this@MainActivity, DENIED_PERMISSION_MESSAGE, Toast.LENGTH_SHORT).show()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                Toast.makeText(this@MainActivity, LOWER_VERSION_MESSAGE, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isCheck: Boolean ->
        if (isCheck) Toast.makeText(this@MainActivity, PERMIT_PERMISSION_MESSAGE, Toast.LENGTH_SHORT).show()
        else Toast.makeText(this@MainActivity, DENIED_PERMISSION_MESSAGE, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val DENIED_PERMISSION_MESSAGE = "알림 권한이 차단되었습니다"
        private const val PERMIT_PERMISSION_MESSAGE = "알림 권한이 허용되었습니다"
        private const val LOWER_VERSION_MESSAGE = "설정에 가서 알림 권한을 켜주세요"

        val fragmentRepository = mapOf(
            R.id.history to HistoryFragment(),
            R.id.home to HomeFragment(),
            R.id.setting to SettingFragment(),
        )
    }
}
