package com.exz.hscal.hscoal.adapter

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.ReleaseBean
import kotlinx.android.synthetic.main.item_release_cocal1.view.*
import kotlinx.android.synthetic.main.item_release_cocal2.view.*
import kotlinx.android.synthetic.main.item_release_cocal3.view.*
import kotlinx.android.synthetic.main.item_release_cocal4.view.*
import kotlinx.android.synthetic.main.item_release_cocal5.view.*
import org.w3c.dom.Text

/**
 * Created by pc on 2017/12/7.
 */

class ReleaseAdapter<T : ReleaseBean> : BaseMultiItemQuickAdapter<T, BaseViewHolder>(ArrayList<T>()) {

    init {
        addItemType(1, R.layout.item_release_cocal1)
        addItemType(2, R.layout.item_release_cocal2)
        addItemType(3, R.layout.item_release_cocal3)
        addItemType(4, R.layout.item_release_cocal4)
        addItemType(5, R.layout.item_release_cocal5)
    }

    override fun convert(helper: BaseViewHolder, item: T) {
        var v = helper.itemView
        when (item.lay) {//1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
            1 -> {
                v.tvK1.text = item.k
                v.edV.inputType = item.inputType
                when (item.check) {
                    "0" -> {
                        v.edV.hint = item.v
                    }
                    "3" -> {
                        v.edV.setText(item.v)
                    }
                }
                v.edV.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        item.v = p0.toString().trim()
                        item.check = "3"
                    }

                })

            }
            2 -> {
                v.tvk2.text = item.k

                when (item.check) {
                    "0" -> {
                        v.tvV2.hint = item.v
                    }
                    "3" -> {
                        v.tvV2.setText(item.v)
                    }
                }
            }
            3 -> {
                v.tvK3.text = item.k
                v.tvLeft.setText(item.left)
                v.tvRight.setText(item.right)
            }
            4 -> {
                v.tvK4.text = item.k
                v.edLeft.setText(item.left)
                v.edLeft.inputType = item.inputType
                v.edRight.setText(item.right)
                v.edRight.inputType = item.inputType

                v.edLeft.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        if (!TextUtils.isEmpty(v.edRight.text.toString().trim())) {
                            item.check = "3"
                        }
                    }

                })
                v.edRight.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        if (!TextUtils.isEmpty(v.edLeft.text.toString().trim())) {
                            item.check = "3"
                        }
                    }

                })
            }
            5 -> {
                v.tvK5.text = item.k
                if (item.v.contains("http")) {
                    v.iv_img.setImageURI(item.v)
                } else {
                    v.iv_img.setImageURI("file://" + item.v)
                }
            }
        }

        val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(0, 0, 0, item.line)
        v.layoutParams = lp
        helper.addOnClickListener(R.id.tvLeft)
        helper.addOnClickListener(R.id.tvRight)
    }
}
