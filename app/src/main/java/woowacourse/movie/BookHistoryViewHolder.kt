package woowacourse.movie

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.movie.MovieBookingSeatInfo

class BookHistoryViewHolder(
    private val view: View,
    private val bookHistoryOnClickListener: (Int) -> Unit,
) : RecyclerView.ViewHolder(view) {

    val date: TextView = view.findViewById(R.id.tv_book_history_date)
    val title: TextView = view.findViewById(R.id.tv_book_history_title)

    init {
        view.setOnClickListener {
            bookHistoryOnClickListener(bindingAdapterPosition)
        }
    }

    fun bind(movieBookingSeatInfo: MovieBookingSeatInfo) {
        val dateAndTime = movieBookingSeatInfo.movieBookingInfo.formatBookHistoryDate()
        val spannableStringBuilder = SpannableStringBuilder(dateAndTime)
        val grayColorSpan =
            ForegroundColorSpan(view.context.getColor(R.color.book_history_separator))
        val separatorPosition = getSeparatorPosition(dateAndTime)
        spannableStringBuilder.setSpan(
            grayColorSpan,
            separatorPosition,
            separatorPosition + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        date.text = spannableStringBuilder
        title.text = movieBookingSeatInfo.movieBookingInfo.title
    }

    private fun getSeparatorPosition(dateAndTime: String) = dateAndTime.length - TIME_LENGTH

    companion object {
        private const val TIME_LENGTH = 7
    }
}
