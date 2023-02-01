package com.mazhang.xierwedding

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.mazhang.xierwedding.net.Api
import com.mazhang.xierwedding.net.RetrofitManager
import timber.log.Timber

class MyApplication : Application() {
    private var instance: MyApplication? = null

    /**
     * 获取Application
     *
     * @return App
     */
    @Synchronized
    fun getInstance(): MyApplication? {
        if (instance == null) {
            instance = MyApplication()
        }
        return instance
    }

    @Synchronized
    fun setInstance(instance: MyApplication) {
        this.instance = instance
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        setInstance(this)
        RetrofitManager.instance.init(Api().BASE_URL)
        Timber.plant(Timber.DebugTree())
        Timber.tag("XierWedding")
    }
}