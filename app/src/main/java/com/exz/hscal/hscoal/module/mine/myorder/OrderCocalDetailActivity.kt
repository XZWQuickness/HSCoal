package com.exz.hscal.hscoal.module.mine.myorder

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.coal.SeekCocalDetailActivity
import com.exz.hscal.hscoal.module.main.release.ReleaseCocalActivity
import com.exz.hscal.hscoal.module.mine.TAUserInfoActivity
import com.exz.hscal.hscoal.module.mine.goodsmanage.GoodsManageCocalDetailActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_order_cocal_detail.*

/**
 * Created by pc on 2017/12/13.
 */

class OrderCocalDetailActivity : BaseActivity() {
    var hisUserId = ""
    override fun initToolbar(): Boolean {
        mTitle.text = "煤炭详情"
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
        return R.layout.activity_order_cocal_detail
    }

    override fun init() {
        super.init()
        initData()
    }

    private fun initData() {
        DataCtrlClass.cocalOrderDetail(mContext, intent.getStringExtra(Intent_Order_detaile_Id), {
            if (it != null) {
                name.text = it.data?.name ?: ""
                coalVarietyName.text = "(" + it.data?.coalVarietyName + ")" ?: ""
                when (it.data?.coalVarietyName) {
                    "焦炭/焦粉/焦粒" -> {
                        llLayCocal2.visibility = View.GONE
                        llLayCocal3.visibility = View.GONE
                        place.text = String.format(mContext.getString(R.string.origin), it.data?.place ?: "")//产地
                        fixedCarbon.text = it.data?.fixedCarbon ?: ""//固定碳
                        calorificValue.text = it.data?.calorificValue + "(MJ/kg)" ?: ""//发热量
                        ashSpecification.text = it.data?.ashSpecification + "(%)" ?: "" //灰份
                        volatiles.text = it.data?.volatiles + "(%)" ?: "" //挥发份
                        inherentMoisture.text = it.data?.inherentMoisture + "(%)" ?: "" //内水
                        totalSulfurContent.text = it.data?.totalSulfurContent + "(%)" ?: "" //全硫份
                    }
                    "炼焦煤" -> {
                        llLayCocal1.visibility = View.GONE
                        llLayCocal3.visibility = View.GONE
                        ashSpecification2.text = it.data?.ashSpecification + "(%)" ?: "" //灰份
                        totalSulfurContent2.text = it.data?.totalSulfurContent + "(%)" ?: "" //全硫份
                        bond.text = it.data?.bond ?: "" //粘结
                        volatiles2.text = it.data?.volatiles + "(%)" ?: "" //挥发份
                        Y_Value.text = it.data?.yValue + "(mm)" ?: "" //v值
                        lithofacies.text = it.data?.lithofacies ?: "" //岩相（煤种：炼焦煤）
                        scr.text = it.data?.csr ?: ""
                    }
                    "动力煤" -> {
                        llLayCocal1.visibility = View.GONE
                        llLayCocal2.visibility = View.GONE

                        lowerCalorificValue.text = it.data?.lowerCalorificValue + "(kcal/kg)" ?: "" //低位热值
                        airDrySulfur.text = it.data?.airDrySulfur + "(%)" ?: "" //空干基硫分
                        airDryRadicalVolatiles.text = it.data?.airDryRadicalVolatiles + "(%)" ?: "" //空干基挥发分
                        inherentMoisture3.text = it.data?.inherentMoisture + "(%)" ?: "" //内水
                        fixedCarbon3.text = it.data?.fixedCarbon + "(%)" ?: ""//固定碳
                        baseVolatiles.text = it.data?.baseVolatiles + "(%)" ?: ""//收到基挥发分
                        totalMoisture.text = it.data?.totalMoisture + "(%)" ?: ""//全水分
                        ashSpecification3.text = it.data?.ashSpecification + "(%)" ?: "" //灰份
                        ashFusionPoint.text = it.data?.ashFusionPoint ?: "" //灰熔点
                        G_Value.text = it.data?.gValue ?: ""
                        Y_Value3.text = it.data?.yValue + "(mm)" ?: ""
                    }
                }
                place.text = String.format(mContext.getString(R.string.origin), it.data?.place ?: "")//产地
                price.text= mContext.getString(R.string.main_cocal_detail_unit_price)+": ￥"+it.data?.price//单价
                count.text = String.format(mContext.getString(R.string.my_order_buy_num), it.data?.count)//购买数量
                orderId.text = String.format(mContext.getString(R.string.my_order_num), intent.getStringExtra(Intent_Order_detaile_Id))//订单编号
                createDate.text = String.format(mContext.getString(R.string.my_order_create_time), it.data?.createDate)//创建时间
                payMoney.text = "合计: ￥" + it.data?.payMoney//合计
                paymentModeName.text = it.data?.paymentModeName //付款方式
                inspectonBody.text = it.data?.inspectonBody // 检验机构
                deliveryTime.text = it.data?.deliveryTime?.replace(",", "至") ?: "" //交货时间
                deliveryWayName.text = it.data?.deliveryWayName //交货方式
                provinceCity.text = it.data?.provinceCity //交货地点
                remark.text = it.data?.remark//备注
                hisUserId = it.data?.hisUserId!!
                if (!TextUtils.isEmpty(it.data?.mobile) && !TextUtils.isEmpty(it.data?.consignee) && !TextUtils.isEmpty(it.data?.address)) {
                    llConsignee.visibility = View.VISIBLE
                    consignee.text =mContext.getString(R.string.contact)+it.data?.consignee//联系人
                    mobile.text = "电话: " + it.data?.mobile
                    address.text = it.data?.address//地址
                } else {
                    llConsignee.visibility = View.GONE
                }
                orderId.text = String.format(mContext.getString(R.string.my_order_num), intent.getStringExtra(Intent_Order_detaile_Id))//订单编号
            }
        })
    }

    companion object {
        var Intent_Order_detaile_Id = "orderId"
    }
}
