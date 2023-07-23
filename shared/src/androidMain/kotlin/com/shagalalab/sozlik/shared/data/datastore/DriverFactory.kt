package com.shagalalab.sozlik.shared.data.datastore

import android.content.Context
import com.shagalalab.sozlik.SozlikDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

lateinit var appContext: Context

actual fun createSqldelightDriver(): SqlDriver = AndroidSqliteDriver(SozlikDatabase.Schema, appContext, "sozlik.db")
