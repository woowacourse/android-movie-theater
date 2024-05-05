@file:JvmName("MoneyUi")

package woowacourse.movie.ui

import java.util.Locale

fun currency(price: Int): String = Currency.of(Locale.getDefault().country).format(price)
