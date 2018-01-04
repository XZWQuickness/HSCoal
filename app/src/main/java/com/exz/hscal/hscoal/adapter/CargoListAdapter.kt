package com.exz.hscal.hscoal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.CargoListBean
import kotlinx.android.synthetic.main.item_cargo_list.view.*

/**
 * Created by pc on 2017/12/4.
 */

class CargoListAdapter() : BaseQuickAdapter<CargoListBean, BaseViewHolder>(R.layout.item_cargo_list, null) {

    override fun convert(helper: BaseViewHolder, item: CargoListBean) {
        var v = helper.itemView
        v.image.setImageURI(item.image)
        v.description.text = item.description
        v.provinceCity.text ="交货地:"+ item.provinceCity
        v.price.text = item.price

    }
}
