package woowacourse.movie.list.contract

interface HomeContract {
    interface View

    interface Presenter {
        fun saveInSharedPreference(isGranted: Boolean)
    }
}
