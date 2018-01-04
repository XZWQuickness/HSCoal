package com.exz.hscal.hscoal.bean

import java.io.Serializable

/**
 * Created by pc on 2017/12/5.
 */

open class AddressBean(var state: String = "1") : Serializable {
    var id="1"
    var userName="鱼干"
    var mobile="18888888878"
    var zipCode=""
    var provinceId=""
    var cityId=""
    var areaId=""
    var address=""
    var provinceCity=""
    var detail="吼吼吼吼"
    fun isDefault():Boolean = state=="1"




}
