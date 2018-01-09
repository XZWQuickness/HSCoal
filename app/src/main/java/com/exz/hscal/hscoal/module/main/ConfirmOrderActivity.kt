package com.exz.hscal.hscoal.module.main

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.mine.address.AddressChooseActivity
import com.exz.hscal.hscoal.module.mine.myorder.MyOrderActivity
import com.exz.hscal.hscoal.utils.DialogUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_confirm_order.*
import org.jetbrains.anko.toast

/**
 * Created by pc on 2017/12/12.
 * 确认订单
 */

class ConfirmOrderActivity : BaseActivity(), View.OnClickListener {

    private var type = ""//类型：1煤炭 2有色金属
    private var objectId = ""//煤炭、有色金属货源id
    private var deliveryWayId = ""//交货方式id
    private var shippingAddressId = ""//配送地址id
    private var count = ""//购买数量
    private var remark = ""//备注说明

    private var residualCount = 0f

    private var allPrice=0f

    override fun initToolbar(): Boolean {
        mTitle.text = "确认订单"
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
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return R.layout.activity_confirm_order
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        add.setOnClickListener(this)
        tvCount.setOnClickListener(this)
        minus.setOnClickListener(this)
        tv_name.setOnClickListener(this)
        llAddress.setOnClickListener(this)
        bt_submit.setOnClickListener(this)
        type = intent.getStringExtra(Intent_Type)
        objectId = intent.getStringExtra(Intent_Id)
        deliveryWayId = intent.getStringExtra(Intent_Type_Address)
        if (intent.getStringExtra(Intent_Type_Address).equals("1")) {//物流配送
            llAddress.visibility = View.VISIBLE

            initAddress()

        } else {//到场自提
            llAddress.visibility = View.GONE
        }
        getConfirmOrder()

        tvCount.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!TextUtils.isEmpty(p0.toString().trim())){
                    tvTotal.text="￥"+(p0.toString().trim().toFloat()*allPrice).toString()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun getConfirmOrder() {
        //获取确认订单信息
        DataCtrlClass.ConfirmOrderData(mContext, type, objectId, {
            if (it != null) {
                img.setImageURI(it.data?.image)
                tvTitle.text = it.data?.name
                price.text = it.data?.price
                allPrice= it.data?.price!!.toFloat()
                residualCount = it.data?.residualCount?.toFloat()!!

              tvTotal.text="￥"+allPrice.toString()

            }
        })
    }

    private fun initAddress() {
        DataCtrlClass.confirmOrderShippingAddress(mContext, shippingAddressId, {
            if (it != null) {
                tv_address.visibility = View.VISIBLE
                tv_name.text = it.data?.userName + it.data?.mobile
                tv_address.text = it.data?.address ?: ""
                shippingAddressId=it.data?.id ?:""
            } else {
                tv_address.visibility = View.GONE
            }
        })
    }

    override fun onClick(p0: View) {
        var mCount = tvCount.text.toString().trim().toFloat()
        when (p0) {
            llAddress -> {
                startActivityForResult(Intent(mContext, AddressChooseActivity::class.java),100)
            }
            add -> {
                if (residualCount > mCount) {
                    mCount++
                } else {
                    mCount = residualCount
                }
                tvCount.text = mCount.toString()

            }
            tvCount -> {
                DialogUtils.changeNum(mContext, mCount, residualCount,type, {
                    if (it != null) {
                        tvCount.text = it.toString()
                    }
                })
            }
            minus -> {
                if (mCount > 1) {
                    mCount--
                    tvCount.text = mCount.toString()
                }
            }
            bt_submit -> { //生成订单
                count = tvCount.text.toString().trim()
                remark = ed_remark.text.toString().trim()
                if (deliveryWayId.equals("1")) {//交货方式 1 物流配送
                    if (TextUtils.isEmpty(shippingAddressId)) {
                        startActivityForResult(Intent(mContext, AddressChooseActivity::class.java),100)
                        return
                    }
                }

                DataCtrlClass.GenerateOrder(mContext,type,objectId,deliveryWayId,shippingAddressId,count,remark,{
                    if(it!=null){
                        finish()
                        startActivity(Intent(mContext,MyOrderActivity::class.java))
                    }
                })

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.hasExtra(Intent_Address_Id)) {
            shippingAddressId = data.getStringExtra(Intent_Address_Id)
            llAddress.visibility = View.VISIBLE
            initAddress()
        }

    }

    companion object {
        var Intent_Type_Address = "deliveryWayId" //交货方式 1 物流配送 2 到场自提
        var Intent_Address_Id = "shippingAddressId" // 地址id
        var Intent_Type = "type" //类型：1煤炭 2有色金属
        var Intent_Id = "objectId" // 煤炭、有色金属货源id
    }
}
