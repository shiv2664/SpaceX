package com.shivam.spacex.utility.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shivam.spacex.fragments.listing.model.BookMarkModel
import com.shivam.spacex.utility.local.typeconverters.StringListConverter
import com.shivam.spacex.fragments.listing.model.XModel
import com.shivam.spacex.utility.local.typeconverters.LaunchFailureConverter
import com.shivam.spacex.utility.local.typeconverters.LaunchSiteConverter
import com.shivam.spacex.utility.local.typeconverters.LinksConverter
import com.shivam.spacex.utility.local.typeconverters.RocketConverter
import com.shivam.spacex.utility.local.typeconverters.TelemetryConverter
import com.shivam.spacex.utility.local.typeconverters.TimelineConverter

@Database(
    entities = [
        XModel::class,
        BookMarkModel::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    LaunchFailureConverter::class,
    LinksConverter::class,
    LaunchSiteConverter::class,
    StringListConverter::class,
    RocketConverter::class,
    TelemetryConverter::class,
    TimelineConverter::class

)
abstract class MyDatabase : RoomDatabase() {
    abstract fun xDao(): XDao
}