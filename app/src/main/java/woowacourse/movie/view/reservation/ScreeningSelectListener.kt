package woowacourse.movie.view.reservation

import android.view.View
import android.widget.AdapterView

class ScreeningSelectListener<T>(
    private val items: List<T>,
    private val update: (T) -> Unit,
) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long,
    ) {
        val selectedItem = items.getOrNull(position) ?: return
        update(selectedItem)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) = Unit
}
