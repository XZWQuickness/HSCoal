package com.exz.hscal.hscoal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.CargoListBean
import com.exz.hscal.hscoal.bean.DemandBean
import kotlinx.android.synthetic.main.item_demand_list.view.*

/**
 * Created by pc on 2017/12/4.
 */

class DemandAdapter() : BaseQuickAdapter<DemandBean, BaseViewHolder>(R.layout.item_demand_list, null) {

    override fun convert(helper: BaseViewHolder, item: DemandBean) {
        var v = helper.itemView
        v.coalVarietyName.text = item.name + "(" + item.coalVarietyName + ")"//煤种
        v.purchaseQuantity.text = item.purchaseQuantity + "吨"
        when (item.coalVarietyName) {

            "焦炭/焦粉/焦粒" -> {
                v.tvSubTitle.text = "固定碳: " + item.fixedCarbon + " 发热量:" + item.calorificValue + "MJ/kg"
            }

            "炼焦煤" -> {
                v.tvSubTitle.text = "灰份: " + item.ashSpecification+"%" + " 全硫份:" + item.totalSulfurContent + "%"
            }

            "动力煤" -> {
                v.tvSubTitle.text = "低位热值: " + item.lowerCalorificValue+"kcal/kg"
            }
        }

        if(item.coalVarietyName==null){
            v.coalVarietyName.text = item.name
            v.tvSubTitle.text = item.description
            v.purchaseQuantity.text = item.purchaseQuantity + "件"
        }
        v.provinceCity.text = String.format(mContext.getString(R.string.ship_to), item.provinceCity)//收货地
        v.plannedDeliveryTime.text = String.format(mContext.getString(R.string.take_goods_date), item.plannedDeliveryTime)//计划收货时间

    }
}