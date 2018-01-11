package com.exz.hscal.hscoal.module.mine.sellorder
import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.bigkoo.pickerview.TimePickerView
import com.blankj.utilcode.util.KeyboardUtils
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.release.ReleaseSteelActivity
import com.exz.hscal.hscoal.module.mine.myorder.OrderCocalDetailActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.DialogUtils
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_sell_order_steel_detail.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pc on 2017/12/14.
 * 我的卖家 商品管理 金属详情
 */

class SellOrderSteelDetailActivity : BaseActivity(), View.OnClickListener {
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

        toolbar.inflateMenu(R.menu.menu_seek_cocal_detail_text)
        if (intent.getStringExtra(Intent_State).equals("3")) {
            var actionView = toolbar.menu.getItem(0).actionView
            (actionView as TextView).text = "修改"
            actionView.setOnClickListener {

                startActivity(Intent(mContext, ReleaseSteelActivity::class.java))
            }
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_sell_order_steel_detail
    }

    override fun init() {
        super.init()
        initEvent()
        initData()
        initOptionPicker()
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

                price.text= String.format(mContext.getString(R.string.main_cocal_detail_unit_price),"￥"+it.data?.price )//单价
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

                if(!it.data?.mobile.equals("")&&!it.data?.consignee.equals("")&&!it.data?.address.equals("")){
                    rlConsignee.visibility= View.VISIBLE
                    consignee.text=String.format(mContext.getString(R.string.contact),it.data?.consignee)//联系人
                    mobile.text="电话"+it.data?.mobile
                    address.text=it.data?.address//地址
                    phone = it.data?.mobile ?: ""
                    tv_right.visibility = View.VISIBLE
                }else{
                    rlConsignee.visibility= View.GONE
                    tv_right.visibility= View.GONE
                }
                if (tv_right.visibility == View.GONE && tv_left.visibility == View.GONE) {
                    llBtLay.visibility = View.GONE
                }
                orderId.text= String.format(mContext.getString(R.string.my_order_num),intent.getStringExtra(OrderCocalDetailActivity.Intent_Order_detaile_Id))//订单编号
            }
        })
    }


    private fun initEvent() {
        if (intent.getIntExtra(Intent_State, 1) == 1) {
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
                DataCtrlClass.confirmPay(mContext,intent.getStringExtra(SellOrderCocalDetailActivity.Intent_Order_Detaile_Id) , sendTime, {
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

    companion object {
        var Intent_State="intent_state"
        var Intent_Id = "orderId"
    }
}