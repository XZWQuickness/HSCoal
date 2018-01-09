package com.exz.hscal.hscoal.adapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.CargoListBean
import com.exz.hscal.hscoal.bean.SeekGoodsEntity
import kotlinx.android.synthetic.main.item_seek_goods_list.view.*

/**
 * Created by pc on 2017/12/4.
 */

class SeekGoodsListAdapter() : BaseQuickAdapter<SeekGoodsEntity, BaseViewHolder>(R.layout.item_seek_goods_list, null) {

    @SuppressLint("StringFormatInvalid")
    override fun convert(helper: BaseViewHolder, item: SeekGoodsEntity) {
        val v = helper.itemView
        v.img.setImageURI(item.image)
        v.toAddress.text = item.toAddress
        v.fromAddress.text = item.fromAddress
        v.name.text=item.name
        v.sendTime.text="发货时间: "+item.sendTime
        v.count.text=String.format(mContext.getString(R.string.quantity,item.count))

        if(!TextUtils.isEmpty(item.toAddress)&&!TextUtils.isEmpty(item.fromAddress)){

            v.toAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,ContextCompat.getDrawable(mContext,R.mipmap. icon_arrow_seek_goods),null)
        }else{
            v.toAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null)
        }
    }
}