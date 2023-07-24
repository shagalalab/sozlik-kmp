package com.shagalalab.sozlik.shared.di

import com.shagalalab.sozlik.shared.data.datastore.AndroidSqldelightDriver
import com.shagalalab.sozlik.shared.data.datastore.SqldelightDriver
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val platformModule: Module = module {
    single<SqldelightDriver> { AndroidSqldelightDriver(get()) }
}
