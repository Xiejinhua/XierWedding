package com.mazhang.xierwedding.bean


class ParkListBean : BaseBean() {

    private var data: List<DataBean?>? = null
    private var result = false

    fun isResult(): Boolean {
        return result
    }

    fun setResult(result: Boolean) {
        this.result = result
    }

    fun getData(): List<DataBean?>? {
        return data
    }

    fun setData(data: List<DataBean?>?) {
        this.data = data
    }

    class DataBean {
        var quotationId: String? = null
        var quotationContent: String? = null
        var modifyUser: String? = null
        var status: String? = null
    }
}