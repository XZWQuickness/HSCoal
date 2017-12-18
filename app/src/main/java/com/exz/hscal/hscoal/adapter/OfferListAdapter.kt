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
            "1" -> {//未接受
                itemView.tv_left.visibility=View.VISIBLE
                itemView.tv_mind.visibility=View.VISIBLE
                itemView.tv_right.visibility=View.GONE
            }
            "2" -> {//已接受
                itemView.tv_left.visibility=View.GONE
                itemView.tv_mind.visibility=View.GONE
                itemView.tv_right.visibility=View.VISIBLE
                itemView.tv_right.text="已接受"
            }
            "3"->{//已拒绝
                itemView.tv_left.visibility=View.GONE
                itemView.tv_mind.visibility=View.GONE
                itemView.tv_right.visibility=View.VISIBLE
                itemView.tv_right.text="已拒绝"
            }
        }

        helper.addOnClickListener(R.id.tv_left)
        helper.addOnClickListener(R.id.tv_mind)
        helper.addOnClickListener(R.id.tv_right)

    }

}
