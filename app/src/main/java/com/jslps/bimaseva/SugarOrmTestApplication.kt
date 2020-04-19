package com.jslps.bimaseva

import android.content.res.Configuration
import com.orm.SchemaGenerator
import com.orm.SugarApp
import com.orm.SugarContext
import com.orm.SugarDb


class SugarOrmTestApplication : SugarApp() {
    override fun onCreate() {
        super.onCreate()
        SugarContext.init(applicationContext)
        // create table if not exists
        val schemaGenerator = SchemaGenerator(this)
        schemaGenerator.createDatabase(SugarDb(this).db)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
        SugarContext.terminate()
    }
}