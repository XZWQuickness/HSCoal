package com.exz.hscal.hscoal.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.InquiryOrderBean
import kotlinx.android.synthetic.main.item_inquiry_order_list.view.*

/**
 * Created by pc on 2017/12/4.
 */

class InquiryOrderListAdapter() : BaseQuickAdapter<InquiryOrderBean, BaseViewHolder>(R.layout.item_inquiry_order_list, null) {

    override fun convert(helper: BaseViewHolder, item: InquiryOrderBean) {
        var v = helper.itemView
        v.name.text = item.name + "(" + item.coalVarietyName + ")"//煤种
        v.purchaseQuantity.text = item.purchaseQuantity + "吨"
        when (item.state) {
            "0" -> {//报价中
                v.state.text = "报价中"
                v.state.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.colorPrimary)
            }
            "1" -> {//已确认
                v.state.text = "已确认"
                v.state.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.MaterialGreen400)
            }
            "2" -> {//已过期
                v.state.text = "已拒绝"
                v.state.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.Red)
            }
            "3" -> {//已过期
                v.state.text = "已过期"
                v.state.delegate.backgroundColor = ContextCompat.getColor(mContext, R.color.MaterialGrey500)
            }
        }
        when (item.coalVarietyName) {

            "焦炭/焦粉/焦粒" -> {
                v.tvSubTitle.text = "固定碳: " + item.fixedCarbon + " 发热量:" + item.calorificValue + "MJ/kg"
            }

            "炼焦煤" -> {
                v.tvSubTitle.text = "灰份: " + item.ashSpecification + "%" + " 全硫份:" + item.totalSulfurContent + "%"
            }

            "动力煤" -> {
                v.tvSubTitle.text = "低位热值: " + item.lowerCalorificValue + "kcal/kg"
            }
        }

        if (item.coalVarietyName == null|| item.coalVarietyName == "") {
            v.name.text = item.name
            v.tvSubTitle.text =  item.description
            v.purchaseQuantity.text = item.purchaseQuantity + "件"
        }
        v.createDate.text = String.format(mContext.getString(R.string.Offer_time), item.createDate)
        v.price.text = String.format(mContext.getString(R.string.money), "￥"+item.price)

        v.provinceCity.text = String.format(mContext.getString(R.string.ship_to), item.provinceCity)//收货地
        v.plannedDeliveryTime.text = String.format(mContext.getString(R.string.take_goods_date), item.plannedDeliveryTime)//计划收货时间

    }
}