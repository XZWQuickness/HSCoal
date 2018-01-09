package com.exz.hscal.hscoal.module.demand

import android.text.TextUtils
import android.view.View
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_offer.*

/**
 * Created by pc on 2017/12/12.
 * 报价
 */

class OfferActivity : BaseActivity(), View.OnClickListener {
  var type=""//类型：1煤炭 2有色金属
  var objectId=""//煤炭、有色金属询价id
  var price=""//价格
  var remark=""//备注说明


    override fun initToolbar(): Boolean {
        mTitle.text = "填写报价"
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
        return R.layout.activity_offer
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        type=intent.getStringExtra(Intent_type)
        objectId=intent.getStringExtra(Intent_objectId_Key)
        bt_submit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            bt_submit -> {
                price = ed_price.text.toString().trim()
                if (TextUtils.isEmpty(price)) {
                    ed_price.setShakeAnimation()
                    return
                }
                remark = ed_content.text.toString().trim()
                if (TextUtils.isEmpty(remark)) {
                    ed_content.setShakeAnimation()
                    return
                }
                DataCtrlClass.submitQuote(mContext,type,objectId,price,remark,{
                    if(it!=null){
                        finish()
                    }
                })
            }
        }
    }
    companion object {
       var  Intent_objectId_Key="objectId" //煤炭、有色金属询价id
       var  Intent_type="type" //类型：1煤炭 2有色金属
    }
}
