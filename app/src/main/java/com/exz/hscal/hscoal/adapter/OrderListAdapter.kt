package com.exz.hscal.hscoal.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.CargoListBean
import com.exz.hscal.hscoal.bean.OrderBean
import kotlinx.android.synthetic.main.item_order_list.view.*

/**
 * Created by pc on 2017/12/4.
 */

class OrderListAdapter() : BaseQuickAdapter<OrderBean, BaseViewHolder>(R.layout.item_order_list, null) {

    override fun convert(helper: BaseViewHolder, item: OrderBean) {
        var v = helper.itemView
        v.orderId.text = String.format(mContext.getString(R.string.my_order_num), item.orderId)
        if (item.state.equals("0")) {
            v.state.text = "未付款"
            v.state.setTextColor(ContextCompat.getColor(mContext, R.color.Red))
        } else {
            v.state.text = "已付款"
            v.state.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
        }

        v.image.setImageURI(item.image)

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
            v.tvSubTitle.text = "材质: " + item.description
            v.name.text=item.name
        }else{
            v.name.text=item.name+"("+item.coalVarietyName+")"
        }
        v.price.text="￥"+item.price
        v.count.text="×"+item.count
        v.createDate.text=String.format(mContext.getString(R.string.my_order_create_time), item.createDate)
        v.payMoney.text=String.format(mContext.getString(R.string.total), item.payMoney)
    }
}