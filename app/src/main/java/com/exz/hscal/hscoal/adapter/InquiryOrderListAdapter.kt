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
var v=helper.itemView
        when (item.state) {
            "1" -> {//报价中
                v.state.text="报价中"
                v.state.delegate.backgroundColor=ContextCompat.getColor(mContext,R.color.colorPrimary)
            }
            "2" -> {//已确认
                v.state.text="已确认"
                v.state.delegate.backgroundColor=ContextCompat.getColor(mContext,R.color.MaterialGreen400)
            }
            "3" -> {//已过期
                v.state.text="已过期"
                v.state.delegate.backgroundColor=ContextCompat.getColor(mContext,R.color.Red)
            }
            "4" -> {//已过期
                v.state.text="已过期"
                v.state.delegate.backgroundColor=ContextCompat.getColor(mContext,R.color.MaterialGrey500)
            }
        }
    }
}