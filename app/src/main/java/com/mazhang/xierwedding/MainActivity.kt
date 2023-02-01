package com.mazhang.xierwedding

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.mazhang.xierwedding.Utils.BaseRequestHeader
import com.mazhang.xierwedding.Utils.DigestUtils
import com.mazhang.xierwedding.Utils.SignUtil
import com.mazhang.xierwedding.bean.ParkListBean
import com.mazhang.xierwedding.net.*
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var test: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test = findViewById(R.id.test)

        test.setOnClickListener {
            var map: HashMap<String, Any> = HashMap()
            val headerParams: HashMap<String, Any> = HashMap()
//            map["lon"] = "114.443236"
//            map["lat"] = "23.055491"
            map["appId"] = Api().APP_ID
            map["timestamp"] = System.currentTimeMillis().toString()
            map["vechicleSN"] = "7B81100372380640178MC020027"
            val builder = StringBuilder()
            for (`val` in map) {
                builder.append(`val`.value)
            }
            map["sign"] = DigestUtils.md5Hex(builder.toString().toByteArray()).toUpperCase()
//            headerParams["body"] = map
            //链式调用
            RetrofitManager.instance.createService(ApiService::class.java)
                .login(Api().getEatQuotations, map)
                .compose(NetScheduler.compose())
                .bindToLifecycle(it)
                .subscribe(object : NetResponseObserver<ParkListBean>(this) {
                    override fun success(data: ParkListBean) {
                        Timber.d("success:" + Gson().toJson(data.getData()))
                    }

                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        Timber.d("failure: " + Gson().toJson(apiErrorModel))
                    }
                })
        }

    }
}
