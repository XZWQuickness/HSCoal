package com.exz.hscal.hscoal.utils;

import android.app.Activity
import android.content.Context
import android.text.*
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.common.controls.dialog.CommonDialogFactory
import com.common.controls.dialog.DialogUtil
import com.common.controls.dialog.ICommonDialog
import com.exz.hscal.hscoal.R
import kotlinx.android.synthetic.main.dialog_address.view.*
import kotlinx.android.synthetic.main.dialog_change_num.view.*


/**
 * Created by 史忠文
 * on 2017/10/24.
 */
object DialogUtils {
    lateinit var dialog: ICommonDialog
    /**
     * 清除提醒
     */
    fun delete(context: Context, listener: () -> Unit) {
        dialog = CommonDialogFactory.createDialogByType(context, DialogUtil.DIALOG_TYPE_103)
        dialog.setTitleText("删除")
        dialog.setContentText("确定删除？")
        dialog.setCancelBtn("取消") { dialog.dismiss() }
        dialog.setOkBtn("确定") {
            dialog.dismiss()
            listener.invoke()
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    /**
     * 清除缓存
     */
    fun clearCache(context: Context, listener: () -> Unit) {
        dialog = CommonDialogFactory.createDialogByType(context, DialogUtil.DIALOG_TYPE_103)
        dialog.setTitleText("删除")
        dialog.setContentText("确定缓存？")
        dialog.setCancelBtn("取消") { dialog.dismiss() }
        dialog.setOkBtn("确定") {
            dialog.dismiss()
            listener.invoke()
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    /**
     * 清除搜索记录
     */
    fun deleteSearch(context: Context, listener: View.OnClickListener) {
        dialog = CommonDialogFactory.createDialogByType(context, DialogUtil.DIALOG_TYPE_103)
        dialog.setTitleText("删除")
        dialog.setContentText("确定清除记录？")
        dialog.setCancelBtn("取消") { dialog.dismiss() }
        dialog.setOkBtn("确定") { v ->
            dialog.dismiss()
            listener.onClick(v)
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    /**
     * 支付返回
     */
    fun payBack(context: Activity) {
        dialog = CommonDialogFactory.createDialogByType(context, DialogUtil.DIALOG_TYPE_103)
        dialog.setTitleText("返回")
        dialog.setContentText("您确定放弃支付?")
        dialog.setCancelBtn("支付", { dialog.dismiss() })
        dialog.setOkBtn("确定", {
            dialog.dismiss()
            context.finish()
        })
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    /**
     * 余额支付 没支付密码
     */
    fun payNoPwd(context: Activity, listener: (v: View) -> Unit) {
        dialog = CommonDialogFactory.createDialogByType(context, DialogUtil.DIALOG_TYPE_103)
        dialog.setTitleText("啊哦")
        dialog.setContentText("未设置支付密码！")
        dialog.setCancelBtn("取消") { dialog.dismiss() }
        dialog.setOkBtn("去设置") { v ->
            dialog.dismiss()
            listener.invoke(v)
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    /**
     *
     */
    fun reason(context: Context, content: String) {
        dialog = CommonDialogFactory.createDialogByType(context, DialogUtil.DIALOG_TYPE_103)
        dialog.setTitleText("啊哦")
        dialog.setContentText("拒绝原因")
        dialog.setOkBtn("确定") { v ->
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }
    /**
     *
     */
    fun hint(context: Context, title: String,listener: () -> Unit) {
        dialog = CommonDialogFactory.createDialogByType(context, DialogUtil.DIALOG_TYPE_103)
        dialog.setTitleText("啊哦")
        dialog.setContentText(title)
        dialog.setOkBtn("确定") { v ->
            dialog.dismiss()
            listener.invoke()
        }
        dialog.setCancelBtn("取消",{v ->
            dialog.dismiss()
        })
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }
    /**
     *
     */
    fun updateApk(context: Context, title: String,isMust:String,listener: () -> Unit) {
        if(isMust == "0"){//可以忽略升级
            dialog = CommonDialogFactory.createDialogByType(context, DialogUtil.DIALOG_TYPE_103)
            dialog.setTitleText("发现新版本")
            dialog.setCancelBtn("取消",{v ->
                dialog.dismiss()
            })

        }else{ //必须升级
            dialog = CommonDialogFactory.createDialogByType(context, 4)
            dialog.setTitleText("发现新版本\n\n"+title)
            dialog.setCancelable(false)
        }

        dialog.setContentText(title)
        dialog.setOkBtn("更新") { v ->
            dialog.dismiss()
            listener.invoke()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    /**
     *
     */
    fun address(context: Context, content: String,listener: (c:String) -> Unit) {
        dialog = CommonDialogFactory.createDialogByType(context, DialogUtil.DIALOG_TYPE_103)
        val  view=View.inflate(context,R.layout.dialog_address,null)
        dialog.setContentView(view)
        view.ed_content.setText(content)
        dialog.setTitleText("详细地址")
        dialog.setOkBtn("确定") { v ->
            var conten=view.ed_content.text.toString().trim()
            if(!TextUtils.isEmpty(conten)){
                dialog.dismiss()
                listener.invoke(conten)
            }

        }
        dialog.setCancelBtn("取消",{v ->
            dialog.dismiss()
        })
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }




    /**
     * 数量更改弹窗
     */
    fun changeNum(context: Context, count: Float, max: Float, type: String, listener: (num: Float) -> Unit) {
        dialog = DialogType104(context)
        val view = View.inflate(context, R.layout.dialog_change_num, null)
        ViewHolder(view)

        view.count.setSelection(view.count.text.length)

        if (type.equals("1")) {
            view.count.inputType=InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
            view.count.setText(String.format("%s", count))
        } else {
            view.count.inputType=InputType.TYPE_CLASS_NUMBER
            if(type.equals("2")){
                view.count.setText(String.format("%s", count).replace(".0",""))
            }
        }
        countIndex = view.count.text.toString().toFloat()
        view.count.setSelection(view.count.text.toString().trim().length)
        view.count.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!TextUtils.isEmpty(p0.toString().trim()) && p0.toString().trim().toFloat() > max) {
                    view.count.setText(max.toString())
                }

            }
        })
        this.maxCount = max
        dialog.setTitleText("修改数量")
        dialog.setContentView(view)
        dialog.setOkBtn("确定") {
            val trim = view.count.text.toString().trim()
            if (!TextUtils.isEmpty(trim)) {
                listener.invoke(trim.toFloat())
            }else{
                listener.invoke(1f)
            }
            dialog.dismiss()
            countIndex = 1f
        }
        dialog.setCancelBtn("取消") {
            dialog.dismiss()
            countIndex = 1f
        }
        dialog.setOnShowListener { KeyboardUtils.toggleSoftInput() }
        (dialog as DialogType104).setOnBeforeDismiss {
            KeyboardUtils.hideSoftInput(view.count)
            true
        }
        dialog.show()
    }

    private var countIndex = 1.toFloat()
    private var maxCount = 0f

    internal class ViewHolder(private var view: View) : View.OnClickListener {
        init {
            view.count.setSelection(view.count.text.length)
            view.add.setOnClickListener(this)
            view.minus.setOnClickListener(this)
        }

        override fun onClick(p0: View) {
            countIndex = view.count.text.toString().toFloat()
            when (p0.id) {
                R.id.minus -> countIndex = if (countIndex <= 1) 1f else --countIndex
                R.id.add -> {
                    if (maxCount > countIndex) countIndex += 1 else maxCount
                }
            }

            view.count.setText(String.format("%s", countIndex).replace(".0",""))
            view.count.setSelection(view.count.text.length)
        }

    }
}