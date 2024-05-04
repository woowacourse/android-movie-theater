package woowacourse.movie.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieMainBinding

class MovieMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_main)

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.home_item
            replaceFragment(NavigationFragmentType.MOVIE_HOME)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragmentType = NavigationFragmentType.from(item.itemId)
            replaceFragment(fragmentType)
            true
        }
    }

    private fun replaceFragment(fragmentType: NavigationFragmentType) {
        val targetFragment =
            supportFragmentManager.findFragmentByTag(fragmentType.tag) ?: fragmentType.newInstance()
                .also { addFragment(it, fragmentType.tag) }

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            show(targetFragment)
        }

        hideOtherFragments(fragmentType.tag)
    }

    private fun addFragment(
        fragment: Fragment,
        tag: String,
    ) {
        supportFragmentManager.commit {
            add(R.id.fragment_container_view, fragment, tag)
        }
    }

    private fun hideOtherFragments(targetFragmentTag: String) {
        supportFragmentManager.commit {
            NavigationFragmentType.entries
                .filterNot { it.tag == targetFragmentTag }
                .mapNotNull { supportFragmentManager.findFragmentByTag(it.tag) }
                .forEach { hide(it) }
        }
    }
}
