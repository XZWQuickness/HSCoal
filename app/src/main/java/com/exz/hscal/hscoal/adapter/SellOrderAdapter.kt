package com.exz.hscal.hscoal.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.CargoListBean
import kotlinx.android.synthetic.main.item_sell_order.view.*

/**
 * Created by pc on 2017/12/4.
 */

class SellOrderAdapter(var type: Int) : BaseQuickAdapter<CargoListBean, BaseViewHolder>(R.layout.item_sell_order, null) {

    override fun convert(helper: BaseViewHolder, item: CargoListBean) {
        var v = helper.itemView
        if (type == 1) {
            v.tv_left.visibility = View.VISIBLE
        } else {
            v.tv_left.visibility = View.GONE
        }
    }
}