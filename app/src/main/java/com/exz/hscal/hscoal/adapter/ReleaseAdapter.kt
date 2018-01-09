package com.exz.hscal.hscoal.adapter

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.RelativeLayout
import com.alibaba.fastjson.JSON
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

        Log.i("text",""+JSON.toJSON(item));
        when (item.lay) {//1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
            1 -> {
                v.tvK1.text = item.k
                v.edV.inputType = item.inputType
                v.edV.setText(item.v)
                if (v.edV.getTag() is TextWatcher) {
                    v.edV.removeTextChangedListener(v.edV.getTag() as TextWatcher);
                }
                val watcher = object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(p0: Editable) {
                        data.get(helper.adapterPosition).v = p0.toString().trim().replace("-","")
                        data.get(helper.adapterPosition).check = "3"
                    }
                }

                v.edV.addTextChangedListener(watcher)
                v.edV.tag = watcher

            }
            2 -> {
                v.tvk2.text = item.k
                v.tvV2.setText(item.v)
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
                if (v.edLeft.getTag() is TextWatcher) {
                    v.edLeft.removeTextChangedListener(v.edLeft.getTag() as TextWatcher);
                }
                val watcherLeft = object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(p0: Editable) {
                        data.get(helper.adapterPosition).left = p0.toString().trim().replace("-","")
                        if (!TextUtils.isEmpty(v.edLeft.text.toString().trim())) {
                            data.get(helper.adapterPosition).check = "3"
                        }
                    }
                }

                v.edLeft.addTextChangedListener(watcherLeft)
                v.edLeft.tag = watcherLeft

                v.edRight.setText(item.right)
                v.edRight.inputType = item.inputType

                if (v.edRight.getTag() is TextWatcher) {
                    v.edRight.removeTextChangedListener(v.edRight.getTag() as TextWatcher);
                }
                val watcherRight = object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(p0: Editable) {
                        data.get(helper.adapterPosition).right = p0.toString().trim().replace("-","")
                        if (!TextUtils.isEmpty(p0.toString().trim())) {
                            data.get(helper.adapterPosition).check = "3"
                        }
                    }
                }
                v.edRight.addTextChangedListener(watcherRight)
                v.edRight.tag = watcherRight

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
