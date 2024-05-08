package woowacourse.movie.presentation.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import woowacourse.movie.R

abstract class BaseActivity<T : ViewDataBinding>(private val resId: Int) : AppCompatActivity() {
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            initialize()
        } catch (e: Exception) {
            navigateToErrorPage()
        }
    }

    private fun initialize() {
        setTheme(R.style.Theme_Movie)
        binding = DataBindingUtil.setContentView(this, resId)
        binding.lifecycleOwner = this
        onCreateSetup()
    }

    abstract fun onCreateSetup()

    private fun navigateToErrorPage() {
        val intent = Intent(this, ErrorActivity::class.java)
        startActivity(intent)
        finish()
    }
}
