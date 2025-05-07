package woowacourse.movie.presentation.common.binding

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter

@BindingAdapter("items", "selectedItem")
fun <T> bindSpinnerItems(
    spinner: Spinner,
    items: List<T>?,
    selectedItem: T?,
) {
    if (items == null) return

    val adapter =
        ArrayAdapter(
            spinner.context,
            android.R.layout.simple_spinner_item,
            items,
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

    spinner.adapter = adapter

    selectedItem?.let {
        val position = adapter.getPosition(it)
        spinner.setSelection(position)
    }
}
