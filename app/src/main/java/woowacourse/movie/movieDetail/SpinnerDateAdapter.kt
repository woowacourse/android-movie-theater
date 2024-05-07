package woowacourse.movie.movieDetail

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

class SpinnerDateAdapter(
    context: Context,
    private val onSelectItem: () -> Unit,
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item) {
    fun updateRunningDates(newItems: List<String>) {
        clear()
        addAll(newItems)
    }

    fun selectedListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long,
            ) {
                onSelectItem()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
