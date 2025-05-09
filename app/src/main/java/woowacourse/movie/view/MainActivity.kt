package woowacourse.movie.view

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.core.ext.showPermissionSnackBar
import woowacourse.movie.view.history.BookingHistoryFragment
import woowacourse.movie.view.movies.MovieListFragment
import woowacourse.movie.view.prefes.SharedPreferencesManager
import woowacourse.movie.view.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefsManager: SharedPreferencesManager

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            prefsManager.saveNotificationPermissionResult(isGranted)
            if (isGranted) {
                // 권한 허용됨
                return@registerForActivityResult
            } else {
                // 권한 거부됨
                val permission = Manifest.permission.POST_NOTIFICATIONS

                if (!shouldShowRequestPermissionRationale(permission)) {
                    // 다시 묻지 않음 선택
                    showPermissionSnackBar(binding.root)
                } else {
                    // 일반 거부 상황
                    showPermissionSnackBar(binding.root)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        prefsManager = SharedPreferencesManager(this)
        requestNotificationPermission()
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val historyFragment = BookingHistoryFragment()
        val movieListFragment = MovieListFragment()
        val settingFragment = SettingFragment()

        if (savedInstanceState == null) {
            binding.navigation.selectedItemId = R.id.action_home
        }
        setNavigationListener(historyFragment, movieListFragment, settingFragment)
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            val permissionCheck = ContextCompat.checkSelfPermission(this, permission)

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                // 이미 권한이 허용됨
                if (!prefsManager.getNotificationPermissionResult()) {
                    prefsManager.saveNotificationPermissionResult(true)
                }
                return
            }

            if (shouldShowRequestPermissionRationale(permission)) {
                showNotificationPermissionDialog()
            } else {
                // 처음 요청이거나 '다시 묻지 않음' 선택 후 재요청 시도
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun setNavigationListener(
        historyFragment: BookingHistoryFragment,
        movieListFragment: MovieListFragment,
        settingFragment: SettingFragment,
    ) {
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_history -> {
                    replaceFragment(historyFragment)
                    true
                }

                R.id.action_home -> {
                    replaceFragment(movieListFragment)
                    true
                }

                R.id.action_setting -> {
                    replaceFragment(settingFragment)
                    true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showNotificationPermissionDialog() {
        val title = getString(R.string.text_notification_request_title)
        val content = getString(R.string.text_notification_request_content)
        val positiveTitle = getString(R.string.text_allow)
        val negativeTitle = getString(R.string.text_cancel)

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(content)
            .setPositiveButton(
                positiveTitle,
            ) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton(negativeTitle, null)
            .show()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, fragment)
        }
    }
}
