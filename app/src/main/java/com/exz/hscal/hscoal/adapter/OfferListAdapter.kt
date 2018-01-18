package com.exz.hscal.hscoal.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.OfferBean
import kotlinx.android.synthetic.main.item_offer_list.view.*
import java.util.*

/**
 * Created by pc on 2017/12/14.
 */

class OfferListAdapter<T : OfferBean> : BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_offer_list, ArrayList<T>()) {
    override fun convert(helper: BaseViewHolder, item: T) {
        val itemView = helper.itemView
        when (item.state) {
            "0" -> {//未接受
                itemView.tv_left.visibility = View.VISIBLE
                itemView.tv_mind.visibility = View.VISIBLE
                itemView.tv_right.visibility = View.GONE
            }
            "1" -> {//已接受
                itemView.tv_left.visibility = View.GONE
                itemView.tv_mind.visibility = View.GONE
                itemView.tv_right.visibility = View.VISIBLE
                itemView.tv_right.text = "已接受"
            }
            "2" -> {//已拒绝
                itemView.tv_left.visibility = View.GONE
                itemView.tv_mind.visibility = View.GONE
                itemView.tv_right.visibility = View.VISIBLE
                itemView.tv_right.text = "已拒绝"
            }
            "3" -> {//已过期
                itemView.tv_left.visibility = View.GONE
                itemView.tv_mind.visibility = View.GONE
                itemView.tv_right.visibility = View.VISIBLE
                itemView.tv_right.text = "已过期"
            }
        }

        helper.addOnClickListener(R.id.tv_left)
        helper.addOnClickListener(R.id.tv_mind)
        itemView.companyName.text = item.companyName
        itemView.price.text = "￥"+item.price
        itemView.tv_remark.text="备注: "+item.remark
    }

}
