package woowacourse.movie.feature.bookingcomplete.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.domain.model.NavigateType
import woowacourse.movie.feature.bookingcomplete.contract.BookingCompleteContract
import woowacourse.movie.feature.bookingcomplete.presenter.BookingCompletePresenter
import woowacourse.movie.feature.main.MainActivity
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.util.getExtra

class BookingCompleteActivity :
    AppCompatActivity(),
    BookingCompleteContract.View {
    private val presenter: BookingCompleteContract.Presenter by lazy { BookingCompletePresenter(this) }
    private val binding: ActivityBookingCompleteBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_booking_complete,
        )
    }
    private lateinit var navigateType: NavigateType
    private val callback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToBack()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigateType = intent.getSerializableExtra(BOOKING_TYPE_KEY) as NavigateType
        presenter.prepareBookingInfo(
            bookingInfo = intent.getExtra(BOOKING_INFO_KEY) ?: BookingInfoUiModel(),
        )
        onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) presenter.quitBookingInfo()
        return super.onOptionsItemSelected(item)
    }

    override fun showBookingResult(bookingInfo: BookingInfoUiModel) {
        binding.bookingInfo = bookingInfo
    }

    override fun navigateToBack() {
        if (navigateType == NavigateType.NAVIGATE_TO_MAIN) {
            val intent = MainActivity.newIntent(this, singleTop = true)
            startActivity(intent)
            finish()
        } else {
            finish()
        }
    }

    companion object {
        private const val BOOKING_INFO_KEY = "BOOKING_INFO"
        private const val BOOKING_TYPE_KEY = "BOOKING_TYPE"

        fun newIntent(
            context: Context,
            bookingInfo: BookingInfoUiModel,
            navigateType: NavigateType,
        ): Intent =
            Intent(context, BookingCompleteActivity::class.java).apply {
                putExtra(BOOKING_INFO_KEY, bookingInfo)
                putExtra(BOOKING_TYPE_KEY, navigateType)
            }
    }
}
