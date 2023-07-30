package com.shagalalab.sozlik.shared.data.repository

import com.shagalalab.sozlik.shared.data.asResult
import com.shagalalab.sozlik.shared.data.datastore.LocalDataStore
import com.shagalalab.sozlik.shared.data.datastore.converter.toDb
import com.shagalalab.sozlik.shared.data.datastore.converter.toDomain
import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository

class DictionaryRepositoryImpl(private val dataStore: LocalDataStore): DictionaryRepository {
    override suspend fun getTranslation(word: String): Result<Dictionary> {
        return asResult { dataStore.getItem(word).toDomain() }
    }

    override suspend fun getTranslation(id: Long): Result<Dictionary> {
        return asResult { dataStore.getItem(id).toDomain() }
    }

    override suspend fun getSuggestions(word: String): Result<List<Dictionary>> {
        return if (word.isEmpty()) {
            asResult { listOf() }
        } else {
            asResult { dataStore.getItemsLike(word).map { it.toDomain() } }
        }
    }

    override suspend fun getFavorites(): Result<List<Dictionary>> {
        return asResult { dataStore.getFavorites().map { it.toDomain() } }
    }

    override suspend fun getHistory(): Result<List<Dictionary>> {
        return asResult { dataStore.getLastAccessed().map { it.toDomain() } }
    }

    override suspend fun updateFavorite(id: Long, isFavorite: Boolean) {
        dataStore.updateFavorite(id, isFavorite)
    }

    override suspend fun updateLastAccessTime(id: Long, lastAccessed: Long) {
        dataStore.updateLastAccessTime(id, lastAccessed)
    }

    override suspend fun save(data: List<Dictionary>) {
        dataStore.insertData(data.map { it.toDb() })
    }
}
