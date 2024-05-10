package woowacourse.movie.data.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonUtil {
    val gson: Gson = GsonBuilder().setPrettyPrinting().create()
}
