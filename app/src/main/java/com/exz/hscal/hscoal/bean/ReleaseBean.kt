package com.exz.hscal.hscoal.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by pc on 2017/12/7.
 */

class ReleaseBean(var lay: Int,//1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
                  var k: String = "",
                  var v: String,
                  var check: String = "",//check = 0 默认值 1 审核通过 2 审核被拒 3 修改值
                  var inputType: Int, var line: Int) : MultiItemEntity { // 1 text 2
    override fun getItemType(): Int = lay
    var left = ""
    var right = ""
}
