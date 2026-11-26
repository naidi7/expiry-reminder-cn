package com.lorenzovainigli.foodexpirationdates.model

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lorenzovainigli.foodexpirationdates.model.dao.ExpirationDatesDao
import com.lorenzovainigli.foodexpirationdates.model.entity.ExpirationDate

@Database(
    entities = [ExpirationDate::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3)
    ],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract val expirationDatesDao: ExpirationDatesDao
}

/** 从原版 v3 升级到分类版 v4：新增 category 和 notify_days_before 两列 */
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE expiration_dates ADD COLUMN category TEXT NOT NULL DEFAULT 'food'")
        db.execSQL("ALTER TABLE expiration_dates ADD COLUMN notify_days_before INTEGER NOT NULL DEFAULT 3")
    }
}