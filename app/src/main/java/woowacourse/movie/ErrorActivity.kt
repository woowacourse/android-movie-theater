package woowacourse.movie

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val message = intent.getStringExtra("ERROR_MESSAGE") ?: "알 수 없는 오류가 발생했습니다."
        findViewById<TextView>(R.id.tvErrorMessage).text = message

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
