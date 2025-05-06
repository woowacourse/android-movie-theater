package woowacourse.movie.domain.model.rule

interface AdInsertionPolicy<ITEM, AD> {
    fun insert(
        items: List<ITEM>,
        adFactory: () -> AD,
    ): List<ITEM>
}
