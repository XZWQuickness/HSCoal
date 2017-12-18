package com.exz.hscal.hscoal.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.AreaBean
import kotlinx.android.synthetic.main.item_pop_stir_list.view.*
import org.jetbrains.anko.backgroundColor

/**
 * Created by pc on 2017/12/6.
 * 一级列表
 */
class AreaOneAdapter() : BaseQuickAdapter<AreaBean, BaseViewHolder>(R.layout.item_pop_stir_list, null) {
    override fun convert(helper: BaseViewHolder, item: AreaBean) {
        var v = helper.itemView
        v.mTitle.text = item.areaName
        if (item.isCheck) {
            v.mTitle.backgroundColor = ContextCompat.getColor(mContext, R.color.White)
        } else {
            v.mTitle.backgroundColor = ContextCompat.getColor(mContext, R.color.app_bg)
        }
    }
}