package woowacourse.movie.presentation.movieDetail

import android.content.Context
import android.widget.ArrayAdapter

class SpinnerTimeAdapter(
    context: Context,
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item) {
    fun updateRunningTimes(newItems: List<String>) {
        clear()
        addAll(newItems)
    }
}
