package com.shagalalab.sozlik.shared.domain.mvi.model

data class Dictionary(
    val id: Long,
    val type: DictionaryType,
    val word: String,
    val translation: String,
    val rawWord: String? = null,
    val isFavorite: Boolean? = null,
    val lastAccessed: Long? = null
)

enum class DictionaryType(val index: Int) {
    UNKNOWN(0),
    QQ_EN(1),
    RU_QQ(2);

    companion object {
        fun from(index: Int): DictionaryType {
            return values().firstOrNull { it.index == index } ?: UNKNOWN
        }
    }
}
