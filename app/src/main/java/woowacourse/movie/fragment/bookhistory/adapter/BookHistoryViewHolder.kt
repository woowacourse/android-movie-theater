package woowacourse.movie.fragment.bookhistory.adapter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemBookHistoryListBinding
import woowacourse.movie.model.BookingHistoryData

class BookHistoryViewHolder(
    private val binding: ItemBookHistoryListBinding,
    private val bookHistoryOnClickListener: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    val date: TextView = binding.tvBookHistoryDate
    val title: TextView = binding.tvBookHistoryTitle

    init {
        binding.root.setOnClickListener {
            bookHistoryOnClickListener(bindingAdapterPosition)
        }
    }

    fun bind(movieHistoryData: BookingHistoryData) {
        val dateAndTime = movieHistoryData.date
        val spannableStringBuilder = SpannableStringBuilder(dateAndTime)
        val grayColorSpan =
            ForegroundColorSpan(binding.root.context.getColor(R.color.book_history_separator))
        val separatorPosition = getSeparatorPosition(dateAndTime)
        spannableStringBuilder.setSpan(
            grayColorSpan,
            separatorPosition,
            separatorPosition + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        date.text = spannableStringBuilder
        title.text = movieHistoryData.title
    }

    private fun getSeparatorPosition(dateAndTime: String) = dateAndTime.length - TIME_LENGTH

    companion object {
        private const val TIME_LENGTH = 7
    }
}
