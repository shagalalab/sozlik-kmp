package com.shagalalab.sozlik.shared.domain.repository

import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary

interface DictionaryRepository {
    suspend fun getTranslation(word: String): Result<Dictionary>
    suspend fun getTranslation(id: Long): Result<Dictionary>
    suspend fun getSuggestions(word: String): Result<List<Dictionary>>
    suspend fun getFavorites(): Result<List<Dictionary>>
    suspend fun getHistory(): Result<List<Dictionary>>
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)
    suspend fun updateLastAccessTime(id: Long, lastAccessed: Long)
    suspend fun save(data: List<Dictionary>)
}
