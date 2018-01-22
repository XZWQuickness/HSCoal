package com.exz.hscal.hscoal.pop

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.exz.hscal.hscoal.R
import kotlinx.android.synthetic.main.pop_select_goods_type.view.*
import razerdp.basepopup.BasePopupWindow

/**
 * Created by pc on 2017/12/6.
 */

class SelectGoodsTypePop(context: Activity, listener: (type: String) -> Unit) : BasePopupWindow(context) {
    private lateinit var inflate: View


    init {

        popupWindow.isClippingEnabled = false
        inflate.tv_submit.setOnClickListener {
            var type = ""
            if (inflate.radioGroup.checkedRadioButtonId == inflate.radioGroup.getChildAt(0).id) {
                type = "1"
            } else {
                type = "2"
            }
            listener.invoke(type)
            dismiss()

        }
    }


    override fun getClickToDismissView(): View = popupWindowView

    override fun onCreatePopupView(): View? {
        inflate = View.inflate(context, R.layout.pop_select_goods_type, null)
        inflate.rb1.isChecked = true
        return inflate
    }

    override fun initAnimaView(): View = findViewById(R.id.ll_Animation)

    override fun initShowAnimation(): Animation {
        val shakeAnimate = AnimationUtils.loadAnimation(context, R.anim.select_goods_type_start)
        return shakeAnimate
    }

//    override fun initExitAnimation(): Animation {
//        val shakeAnimate = AnimationUtils.loadAnimation(context, R.anim.select_goods_type_exit)
//        return shakeAnimate
//    }


}