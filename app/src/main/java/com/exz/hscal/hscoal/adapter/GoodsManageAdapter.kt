package com.exz.hscal.hscoal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.CargoListBean
import com.exz.hscal.hscoal.bean.GoodsManagEntity
import kotlinx.android.synthetic.main.item_cargo_list.view.*

/**
 * Created by pc on 2017/12/4.
 */

class GoodsManageAdapter(var classType: Int) : BaseQuickAdapter<GoodsManagEntity, BaseViewHolder>(R.layout.item_cargo_list, null) {

    override fun convert(helper: BaseViewHolder, item: GoodsManagEntity) {
        var v = helper.itemView
        v.image.setImageURI(item.image)
        v.price.text = "￥" + item.price
        v.description.text = item.description
        if (classType == 1) {
            v.provinceCity.text = "产地:" + item.place
        } else {
            v.provinceCity.text = "仓库:" + item.warehouse
        }


    }
}