package com.shagalalab.sozlik.shared.domain.mvi.model

data class Dictionary(
    val id: Long,
    val type: DictionaryType,
    val word: String,
    val rawWord: String,
    val translation: String,
    val isFavorite: Boolean,
    val lastAccessed: Long
)

enum class DictionaryType(val index: Long) {
    UNKNOWN(0),
    QQ_EN(1),
    RU_QQ(2);

    companion object {
        fun from(index: Long): DictionaryType {
            return values().firstOrNull { it.index == index } ?: UNKNOWN
        }
    }
}
