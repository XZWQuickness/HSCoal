package com.exz.hscal.hscoal.bean

import java.io.Serializable

/**
 * Created by pc on 2017/12/5.
 */

open class AddressBean(var state: String = "1") : Serializable {
    var id=""
    var userName=""
    var mobile=""
    var zipCode=""
    var provinceId=""
    var cityId=""
    var areaId=""
    var address=""
    var provinceCity=""
    var detail=""
    var  latitude=""
    var  longitude=""
    fun isDefault():Boolean = state=="1"




}
