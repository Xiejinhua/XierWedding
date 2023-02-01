package com.mazhang.xierwedding.net

import com.mazhang.xierwedding.bean.BaseBean
import com.mazhang.xierwedding.bean.ParkListBean
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

interface ApiService {
    /**
     * 登录
     */
    @POST
    fun login(@Url string: String, @Body map: HashMap<String, Any>): Observable<ParkListBean>
    /**
     * 登录
     */
    @POST
    @FormUrlEncoded
    fun login(@Url string: String, @Field("phone") mobile:String): Observable<ParkListBean>

}