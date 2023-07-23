package com.shagalalab.sozlik.shared.data.datastore

import com.shagalalab.sozlik.SozlikDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

actual fun createSqldelightDriver(): SqlDriver {
    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    SozlikDatabase.Schema.create(driver)
    return driver
}
