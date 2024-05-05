package woowacourse.movie.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val fragmentManagerHelper: FragmentManagerHelper by lazy {
        FragmentManagerHelper(
            this,
            R.id.fragment_container,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            setupBottomNavigationView()
            selectDefaultMenuItem()
        }
    }

    private fun selectDefaultMenuItem() {
        binding.bottomNavigationView.selectedItemId = R.id.action_home
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { menu ->
            fragmentManagerHelper.replace(menu.itemId)
            true
        }
    }
}
