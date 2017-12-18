package com.exz.hscal.hscoal.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.AreaBean
import kotlinx.android.synthetic.main.action_bar_custom.view.*
import org.jetbrains.anko.textColor

/**
 * Created by pc on 2017/12/6.
 * 二级列表
 */
class AreaTwoAdapter() : BaseQuickAdapter<AreaBean.CitiesBean, BaseViewHolder>(R.layout.item_pop_stir_list, null) {
    override fun convert(helper: BaseViewHolder, item: AreaBean.CitiesBean) {
        var v = helper.itemView
        v.mTitle.text = item.areaName
        if (item.isCheck) {
            v.mTitle.textColor = ContextCompat.getColor(mContext, R.color.colorPrimary)
            v.mTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mContext, R.mipmap.icon_stair_list_check), null)
        } else {
            v.mTitle.textColor = ContextCompat.getColor(mContext, R.color.MaterialGrey800)
            v.mTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
        }
    }
}