package woowacourse.movie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMain2Binding
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.bottomNavigation.selectedItemId = R.id.navigation_home
        setFrag(TabFragmentId.HOME)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            TabFragmentId.from(item.itemId)?.let { setFrag(it) } ?: setFrag(TabFragmentId.HOME)
            true
        }
        NotificationHelper.createReservationChannel(this)

        val launcher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            SharedPreferences.saveData(applicationContext, isGranted)
        }

        Notification.askNotificationPermission(this, launcher)
    }

    private fun setFrag(tabFragmentId: TabFragmentId) {
        val fragment = TabFragmentId.from(tabFragmentId)
        supportFragmentManager.commit {
            replace(R.id.main_frame, fragment.fragment)
        }
    }


}

