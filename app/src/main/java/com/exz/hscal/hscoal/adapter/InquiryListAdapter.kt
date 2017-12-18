package com.exz.hscal.hscoal.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.InquiryOrderBean

/**
 * Created by pc on 2017/12/4.
 */

class InquiryListAdapter() : BaseQuickAdapter<InquiryOrderBean, BaseViewHolder>(R.layout.item_inquiry_list, null) {

    override fun convert(helper: BaseViewHolder, item: InquiryOrderBean) {

        var v=helper.itemView

    }
}