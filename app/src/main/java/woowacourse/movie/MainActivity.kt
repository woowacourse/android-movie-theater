package woowacourse.movie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMain2Binding

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
        setFrag(ItemId.HOME)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            ItemId.from(item.itemId)?.let { setFrag(it) } ?: setFrag(ItemId.HOME)
            true
        }
    }

    private fun setFrag(itemId: ItemId) {
        val fragment = ItemId.from(itemId)
        supportFragmentManager.commit {
            replace(R.id.main_frame, fragment.fragment)
        }
    }
}
