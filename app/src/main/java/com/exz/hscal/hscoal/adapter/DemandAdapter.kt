package com.exz.hscal.hscoal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.CargoListBean

/**
 * Created by pc on 2017/12/4.
 */

class DemandAdapter() : BaseQuickAdapter<CargoListBean, BaseViewHolder>(R.layout.item_demand_list, null) {

    override fun convert(helper: BaseViewHolder, item: CargoListBean) {

    }
}