package com.shagalalab.sozlik.shared.di

import com.shagalalab.sozlik.SozlikDatabase
import com.shagalalab.sozlik.shared.data.datastore.LocalDataStore
import com.shagalalab.sozlik.shared.data.datastore.SqldelightDriver
import com.shagalalab.sozlik.shared.data.repository.DictionaryRepositoryImpl
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(allModules)
    }

private val allModules: Module
    get() = module {
        includes(commonModule, platformModule)
    }

private val commonModule = module {
    single { SozlikDatabase(get<SqldelightDriver>().driver) }
    single { LocalDataStore(get()) }
    single<DictionaryRepository> { DictionaryRepositoryImpl(get()) }
}

internal expect val platformModule: Module
