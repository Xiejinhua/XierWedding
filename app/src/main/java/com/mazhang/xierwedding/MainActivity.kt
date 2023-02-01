package com.mazhang.xierwedding

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.mazhang.xierwedding.Utils.DigestUtils
import com.mazhang.xierwedding.bean.ParkListBean
import com.mazhang.xierwedding.databinding.ActivityMainBinding
import com.mazhang.xierwedding.net.*
import com.mazhang.xierwedding.viewModel.MainViewModel
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        var viewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java);
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

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
                .bindToLifecycle(binding.root)
                .subscribe(object : NetResponseObserver<ParkListBean>(this) {
                    override fun success(data: ParkListBean) {
                        Timber.d("success:" + Gson().toJson(data.getData()))
                        viewModel.getName().postValue(data.getData()?.get(0)?.quotationContent)
                    }

                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        Timber.d("failure: " + Gson().toJson(apiErrorModel))
                    }
                })
        }

    }
}
