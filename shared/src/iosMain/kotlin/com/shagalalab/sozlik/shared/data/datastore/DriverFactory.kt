package com.shagalalab.sozlik.shared.data.datastore

import com.shagalalab.sozlik.SozlikDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual fun createSqldelightDriver(): SqlDriver = NativeSqliteDriver(SozlikDatabase.Schema, "sozlik.db")
