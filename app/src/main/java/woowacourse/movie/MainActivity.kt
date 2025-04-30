package woowacourse.movie

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.movies.MoviesFragment
import woowacourse.movie.ui.BaseActivity

class MainActivity : BaseActivity() {
    override val layoutRes: Int
        get() = R.layout.activity_main

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setBottomNavigationView()

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.action_home
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_list -> {
                    supportFragmentManager.commit {
                    }
                    true
                }

                R.id.action_home -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.main_container, MoviesFragment())
                    }
                    true
                }

                R.id.action_settings -> {
                    true
                }

                else -> false
            }
        }
    }
}
