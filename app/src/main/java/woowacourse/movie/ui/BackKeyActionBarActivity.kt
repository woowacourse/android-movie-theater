package woowacourse.movie.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BackKeyActionBarActivity : AppCompatActivity() {
    protected abstract val presenter: BaseContract.Presenter
    protected abstract val binding: ViewDataBinding

    @Deprecated("deprecated", ReplaceWith("onCreateView"), DeprecationLevel.WARNING)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initBinding()
        initPresenter()
        onCreateView(savedInstanceState)
        setContentView(binding.root)
    }

    protected abstract fun initPresenter()
    protected abstract fun initBinding()

    abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
