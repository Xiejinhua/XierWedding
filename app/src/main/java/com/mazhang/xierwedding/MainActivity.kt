package com.mazhang.xierwedding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.mazhang.xierwedding.Utils.DigestUtils
import com.mazhang.xierwedding.bean.ParkListBean
import com.mazhang.xierwedding.databinding.ActivityMainBinding
import com.mazhang.xierwedding.net.*
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.test.setOnClickListener {
            var map: HashMap<String, Any> = HashMap()
            map["appId"] = Api().APP_ID
            map["timestamp"] = System.currentTimeMillis().toString()
            map["vechicleSN"] = "7B81100372380640178MC020027"
            val builder = StringBuilder()
            for (`val` in map) {
                builder.append(`val`.value)
            }
            map["sign"] = DigestUtils.md5Hex(builder.toString().toByteArray()).toUpperCase()
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
