package com.car.car

import java.io.Serializable

/**
 * 车辆类
 */
class CarBean(id: Int, type: String, name: String, price: String, describe: String, pic: Int) : Serializable {
    var id = id //车辆ID
    var type: String? = type  //车辆类型
    var name: String? = name  //车辆名
    var price: String? = price //车辆价格
    var describe: String? = describe //公里数描述
    var img = pic //车辆图片
}