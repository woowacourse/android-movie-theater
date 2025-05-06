package woowacourse.movie.feature.bookingcomplete.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityBookingCompleteBinding
import woowacourse.movie.feature.bookingcomplete.contract.BookingCompleteContract
import woowacourse.movie.feature.bookingcomplete.presenter.BookingCompletePresenter
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.util.getExtra

class BookingCompleteActivity :
    AppCompatActivity(),
    BookingCompleteContract.View {
    private val presenter: BookingCompleteContract.Presenter by lazy { BookingCompletePresenter(this) }
    private val binding: ActivityBookingCompleteBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_booking_complete) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.prepareBookingInfo(bookingInfo = intent.getExtra(BOOKING_INFO_KEY) ?: BookingInfoUiModel())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) presenter.quitBookingInfo()
        return super.onOptionsItemSelected(item)
    }

    override fun showBookingResult(bookingInfo: BookingInfoUiModel) {
        binding.bookingInfo = bookingInfo
    }

    override fun navigateToBack() {
        finish()
    }

    companion object {
        private const val BOOKING_INFO_KEY = "BOOKING_INFO"

        fun newIntent(
            context: Context,
            bookingInfo: BookingInfoUiModel,
        ): Intent =
            Intent(context, BookingCompleteActivity::class.java).apply {
                putExtra(BOOKING_INFO_KEY, bookingInfo)
            }
    }
}
