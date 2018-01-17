package com.exz.hscal.hscoal.module.mine.sellorder

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.bigkoo.pickerview.TimePickerView
import com.blankj.utilcode.util.KeyboardUtils
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.release.ReleaseCocalActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.DialogUtils
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_sell_order_cocal_detail.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pc on 2017/12/14.
 * 我的卖家 已售订单
 */

class SellOrderCocalDetailActivity : BaseActivity(), View.OnClickListener {
    var phone = ""

    lateinit var mTimePicker: TimePickerView
    var sendTime = ""
    override fun initToolbar(): Boolean {
        mTitle.text = "订单详情"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, scrollView)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_sell_order_cocal_detail
    }

    override fun init() {
        super.init()
        initEvent()
        initData()
        initOptionPicker()
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        DataCtrlClass.cocalOrderDetail(mContext, intent.getStringExtra(Intent_Order_Detaile_Id), {
            if (it != null) {
                name.text = it.data?.name ?: ""
                coalVarietyName.text = "(" + it.data?.coalVarietyName + ")"
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
                price.text = String.format(mContext.getString(R.string.main_cocal_detail_unit_price), "￥" + it.data?.price)//单价
                count.text = String.format(mContext.getString(R.string.my_order_buy_num), it.data?.count)//购买数量
                orderId.text = String.format(mContext.getString(R.string.my_order_num), intent.getStringExtra(Intent_Order_Detaile_Id))//订单编号
                createDate.text = String.format(mContext.getString(R.string.my_order_create_time), it.data?.createDate)//创建时间
                payMoney.text = "合计: ￥" + it.data?.payMoney//合计
                paymentModeName.text = it.data?.paymentModeName //付款方式
                inspectonBody.text = it.data?.inspectonBody // 检验机构
                deliveryTime.text = it.data?.deliveryTime?.replace(",", "至") ?: "" //交货时间
                deliveryWayName.text = it.data?.deliveryWayName //交货方式
                provinceCity.text = it.data?.provinceCity //交货地点
                remark.text = it.data?.remark//备注

                if(!TextUtils.isEmpty(it.data?.mobile)&&!TextUtils.isEmpty(it.data?.consignee)&& !TextUtils.isEmpty(it.data?.address)){
                    rlConsignee.visibility = View.VISIBLE
                    consignee.text = String.format(mContext.getString(R.string.contact), it.data?.consignee)//联系人
                    mobile.text = "电话" + it.data?.mobile
                    phone = it.data?.mobile ?: ""
                    address.text = it.data?.address//地址

                    tv_right.visibility = View.VISIBLE
                } else {
                    rlConsignee.visibility = View.GONE
                    tv_right.visibility = View.GONE
                }
                if (tv_right.visibility == View.GONE && tv_left.visibility == View.GONE) {
                    llBtLay.visibility = View.GONE
                }
                orderId.text = String.format(mContext.getString(R.string.my_order_num), intent.getStringExtra(Intent_Order_Detaile_Id))//订单编号
            }
        })
    }

    companion object {
        var Intent_Order_Detaile_Id = "orderId"
        var Intent_Order_Detaile_Type = "type"
    }

    private fun initEvent() {
        if (intent.getIntExtra(Intent_Order_Detaile_Type, 1) == 1) {
            tv_left.visibility = View.VISIBLE
        } else {
            tv_left.visibility = View.GONE
        }
        tv_left.setOnClickListener(this)
        tv_right.setOnClickListener(this)
    }
    @SuppressLint("SimpleDateFormat")
    private fun initOptionPicker() {
        mTimePicker = TimePickerView(mContext, TimePickerView.Type.YEAR_MONTH_DAY)
        mTimePicker.setRange(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR) + 20)
        mTimePicker.setTime(Calendar.getInstance().time)
        mTimePicker.setTitle("发货时间")
        mTimePicker.setOnTimeSelectListener {
            if (it != null) {
                val format = SimpleDateFormat("yyyy-MM-dd")
                sendTime = format.format(it)
                DataCtrlClass.confirmPay(mContext,intent.getStringExtra(Intent_Order_Detaile_Id) , sendTime, {
                    if (it != null) {
                       finish()
                    }
                })
            }
        }
    }
    override fun onClick(p0: View) {
        when (p0) {
            tv_left -> {
                KeyboardUtils.hideSoftInput(this)
                mTimePicker.show()
            }
            tv_right -> {
                DialogUtils.Call(mContext as BaseActivity, phone)
            }

        }
    }
}