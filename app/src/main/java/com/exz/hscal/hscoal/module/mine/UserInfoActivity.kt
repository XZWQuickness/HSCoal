package com.exz.hscal.hscoal.module.mine

import android.content.Intent
import android.util.TypedValue
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.imageloder.GlideImageLoader
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_userinfo.*
import java.util.*

/**
 * Created by pc on 2017/12/5.
 * 我的资料
 */

class UserInfoActivity : BaseActivity(), View.OnClickListener {
    var type = 0
    override fun initToolbar(): Boolean {
        mTitle.text = "我的资料"
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
        return R.layout.activity_userinfo
    }

    override fun init() {
        initCamera()
        initEvent()
    }


    private fun initCamera() {
        val w = ScreenUtils.getScreenWidth() * 0.2
        val layoutParams = iv_header.layoutParams
        layoutParams.width = w.toInt()
        layoutParams.height = w.toInt()
        iv_header.layoutParams = layoutParams
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = GlideImageLoader()
        //显示相机
        imagePicker.isShowCamera = true
        //是否裁剪
        imagePicker.isCrop = true
        //是否按矩形区域保存裁剪图片
        imagePicker.isSaveRectangle = true
        //圖片緩存
        imagePicker.imageLoader = GlideImageLoader()
        imagePicker.isMultiMode = false//单选
        //矩形尺寸
        imagePicker.style = CropImageView.Style.RECTANGLE
        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, resources.displayMetrics).toInt()
        imagePicker.focusWidth = width
        imagePicker.focusHeight = width
        //圖片輸出尺寸
        imagePicker.outPutX = width
        imagePicker.outPutY = width
    }

    private fun initEvent() {
        bt_header.setOnClickListener(this)
        bt_nicekname.setOnClickListener(this)
        bt_company_name.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            bt_header -> {
                PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
            }
            bt_nicekname -> {
                type = 1
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "修改昵称"),RESULTCODE_TEXT)
            }
            bt_company_name -> {
                type = 2
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "修改公司名称"),RESULTCODE_TEXT)
            }
            bt_telephone -> {
                type = 3
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "修改固定电话"),RESULTCODE_TEXT)
            }
            bt_telephone -> {
                type = 4
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "修改QQ号"),RESULTCODE_TEXT)
            }
        }
    }


    private fun editInfo(key: String, value: String) {
        DataCtrlClass.editPersonInfo(this, key, value) {
            if (it != null) {
                if (key == "header") {
                    iv_header.setImageURI(it)
                } else if (key == "nickname") {
                    tv_nicekname.text = value
                }
            }
        }
    }

    companion object {
        var Intent_Text = "text"
        var RESULTCODE_TEXT = 100
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) { //图片选择
                val images = data?.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>
                editInfo("header", (images[0] as ImageItem).path)
            } else if (resultCode == RESULTCODE_TEXT && data != null) {
                when (type) {
                    1 -> {
                        editInfo("nickname", data.getStringExtra(Intent_Text))
                    }
                    2 -> {
                        editInfo("nickname", data.getStringExtra(Intent_Text))
                    }
                    3 -> {
                        editInfo("nickname", data.getStringExtra(Intent_Text))
                    }
                    4 -> {
                        editInfo("nickname", data.getStringExtra(Intent_Text))
                    }
                }
            }
        } catch (e: Exception) {
        }
    }

}
