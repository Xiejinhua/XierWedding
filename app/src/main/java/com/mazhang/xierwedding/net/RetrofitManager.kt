package com.mazhang.xierwedding.net

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.connection.ConnectInterceptor.intercept
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor() {

    //    lateinit var apiService: ApiService
    lateinit var retrofit: Retrofit

    /**
     * 单例模式
     */
    companion object {
        val instance: RetrofitManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {

            RetrofitManager()
        }
    }

    fun init(string: String) {
        val okHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(Interceptor.invoke { chain: Interceptor.Chain ->
                    chain.proceed(
                        request = chain.request().newBuilder().addHeader("apiKey", Api().apiKey)
                            .build()
                    )
                })
                .addInterceptor(LogInterceptor())
                .connectTimeout(8000, TimeUnit.MILLISECONDS)
                .readTimeout(8000, TimeUnit.MILLISECONDS)
                .writeTimeout(8000, TimeUnit.MILLISECONDS)
                .build()
        retrofit = Retrofit.Builder()
            .baseUrl(string)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

//        apiService = retrofit.create(ApiService::class.java)

    }

    /**
     * 动态代理模式，创建请求接口类
     * @param tClass
     * @param <T>
     * @return
    </T> */
    fun <T> createService(tClass: Class<T>): T {

        return retrofit.create(tClass)
    }
}