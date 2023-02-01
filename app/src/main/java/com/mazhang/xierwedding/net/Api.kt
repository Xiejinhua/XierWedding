package com.mazhang.xierwedding.net

class Api {

    @Suppress("PropertyName")
    val BASE_URL: String = "https://apigw.sgmwcloud.com.cn"
    val apiKey = "812917FB04658E0ED281BE52519ADBCE75F13FF52C66256E8FFADAB5FB4BF8C2"

    /**
     * 外网APP_ID
     */
    val APP_ID = "v494e27w2likijujs8a1jgv1yi98t2u3"


    val getParkingList = "/vehicle/v1/getParkingList" //主题地图-查询附近停车场
    val getEatQuotations = "/vehicle/v1/getFoodJunkie"//吃货语录
    val fourShop = "/sv/dev/navigation/4sStoresList" //合创4s店--服务网点

}