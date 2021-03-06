package com.exz.hscal.hscoal.module.mine.myorder

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.steel.SeekSteelDetailActivity
import com.exz.hscal.hscoal.module.mine.TAUserInfoActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_order_steel_detail.*

/**
 * Created by pc on 2017/12/13.
 *
 */

class OrderSteelDetailActivity : BaseActivity() {
    var hisUserId=""
    override fun initToolbar(): Boolean {
        mTitle.text ="金属详情"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, scrollView)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.inflateMenu(R.menu.menu_seek_cocal_detail_text)
        var actionView = toolbar.menu.getItem(0).actionView
        (actionView as TextView).text = "卖家信息"
        actionView.setOnClickListener {
            if (!TextUtils.isEmpty(hisUserId)) {
                startActivity(Intent(mContext, TAUserInfoActivity::class.java).
                        putExtra(TAUserInfoActivity.Intent_TA_Id, hisUserId)
                        .putExtra(TAUserInfoActivity.Intent_ClassName,"卖家信息"))
            }

        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_order_steel_detail
    }

    override fun init() {
        super.init()
        initData()
    }

    private fun initData() {
        DataCtrlClass.getSteelOrderInfo(mContext, intent.getStringExtra(Intent_Id), {
            if (it != null) {
                name.text = it.data?.name ?: ""
                className.text = "(" + it.data?.className + ")" ?: ""
                weight.text = String.format(mContext.getString(R.string.heavy), it.data?.weight ?: "")//件重
                specification.text = it.data?.specification ?: "" //规格
                materialQuality.text = it.data?.materialQuality ?: "" //材质
                warehouse.text = it.data?.warehouse ?: "" //仓库
                paymentModeName.text = it.data?.paymentModeName ?: "" //付款方式
                inspectonBody.text = it.data?.inspectonBody ?: "" //检验机构
                deliveryTime.text = it.data?.deliveryTime?.replace(",", "至") ?: ""//交货时间
                deliveryWayName.text = it.data?.deliveryWayName ?: ""//交货方式
                provinceCity.text = it.data?.provinceCity ?: ""//交货地点
                remark.text = it.data?.remark ?: ""//备注

                price.text= mContext.getString(R.string.main_cocal_detail_unit_price)+": ￥"+it.data?.price//单价
                count.text= String.format(mContext.getString(R.string.my_order_buy_num),it.data?.count )//购买数量
                orderId.text= String.format(mContext.getString(R.string.my_order_num),intent.getStringExtra(OrderCocalDetailActivity.Intent_Order_detaile_Id))//订单编号
                createDate.text= String.format(mContext.getString(R.string.my_order_create_time),it.data?.createDate)//创建时间
                payMoney.text="合计: ￥"+it.data?.payMoney//合计
                paymentModeName.text=it.data?.paymentModeName //付款方式
                inspectonBody.text=it.data?.inspectonBody // 检验机构
                deliveryTime.text=it.data?.deliveryTime?.replace(",", "至") ?: "" //交货时间
                deliveryWayName.text=it.data?.deliveryWayName //交货方式
                provinceCity.text=it.data?.provinceCity //交货地点
                remark.text=it.data?.remark//备注
                hisUserId= it.data?.hisUserId.toString()
                if(!TextUtils.isEmpty(it.data?.mobile)&&!TextUtils.isEmpty(it.data?.consignee)&& !TextUtils.isEmpty(it.data?.address)){
                    llConsignee.visibility=View.VISIBLE
                    consignee.text=mContext.getString(R.string.contact)+it.data?.consignee//联系人
                    mobile.text="电话: "+it.data?.mobile
                    address.text=it.data?.address//地址
                }else{
                    llConsignee.visibility=View.GONE
                }
                orderId.text= String.format(mContext.getString(R.string.my_order_num),intent.getStringExtra(OrderCocalDetailActivity.Intent_Order_detaile_Id))//订单编号
            }
        })
    }
    companion object {
        var Intent_Id = "orderId"
    }
}
