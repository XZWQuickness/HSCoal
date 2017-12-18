package com.exz.hscal.hscoal.module.mine

import android.content.Intent
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.FileIOUtils
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
import kotlinx.android.synthetic.main.activity_applyfor_driver.*
import java.util.*

/**
 * Created by pc on 2017/12/15.
 * 申请司机
 */

class ApplyForDriverActivity : BaseActivity(), View.OnClickListener {
    private var type = 0
    private var drivingLicence = ""
    private var driving = ""
    override fun initToolbar(): Boolean {
        mTitle.text = "申请司机"
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
        return R.layout.activity_applyfor_driver
    }

    override fun init() {
        super.init()
        initView()
        initCamera()
    }

    private fun initCamera() {
        val w = ScreenUtils.getScreenWidth() * 0.2
        val layoutParams = iv_driving_licence_img.layoutParams
        layoutParams.width = w.toInt()
        layoutParams.height = w.toInt()
        iv_driving_licence_img.layoutParams = layoutParams
        iv_driving_img.layoutParams = layoutParams
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

    private fun initView() {
        bt_contact_name.setOnClickListener(this)
        bt_contact_phone.setOnClickListener(this)
        bt_driving_licence.setOnClickListener(this)
        bt_driving.setOnClickListener(this)
        bt_commit.setOnClickListener(this)
    }


    override fun onClick(p0: View) {
        when (p0) {
            bt_contact_name -> {//联系人姓名
                type = 1
                var contactName = tv_contact_name.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人姓名").putExtra(UserInfoActivity.Intent_Text, contactName), UserInfoActivity.RESULTCODE_TEXT)
            }
            bt_contact_phone -> {//联系人手机
                type = 2
                var contactPhone = tv_contact_phone.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人手机").putExtra(UserInfoActivity.Intent_Text, contactPhone), UserInfoActivity.RESULTCODE_TEXT)
            }
            bt_driving_licence -> {//驾驶证照片
                type = 3
                PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
            }
            bt_driving -> { //行驶证照片
                type = 4
                PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
            }
            bt_commit -> {
                var contactName = tv_contact_name.text.toString().trim()
                if (TextUtils.isEmpty(contactName)) {
                    type = 1
                    startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人姓名").putExtra(UserInfoActivity.Intent_Text, contactName), UserInfoActivity.RESULTCODE_TEXT)
                    return
                }
                var contactPhone = tv_contact_phone.text.toString().trim()
                if (TextUtils.isEmpty(contactPhone)) {
                    type = 2
                    startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人手机").putExtra(UserInfoActivity.Intent_Text, contactPhone), UserInfoActivity.RESULTCODE_TEXT)
                    return
                }
                if (TextUtils.isEmpty(drivingLicence)) {
                    type = 3
                    PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                    return
                }
                if (TextUtils.isEmpty(driving)) {
                    type = 4
                    PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                    return
                }
                DataCtrlClass.applyForDriver(mContext, contactName, contactPhone, drivingLicence, driving, {
                    finish()
                })

            }


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) { //图片选择
            val images = data?.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>
            if (type == 3) {
                iv_driving_licence_img.setImageURI("file://" + (images.get(0) as ImageItem).path)
                drivingLicence = EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream((images[0] as ImageItem).path))
            } else if (type == 4) {
                iv_driving_img.setImageURI("file://" + (images.get(0) as ImageItem).path)
                driving = EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream((images[0] as ImageItem).path))
            }
        } else
            when (type) {
                1 -> {//联系人姓名
                    if (data != null) {
                        tv_contact_name.text = data.getStringExtra(UserInfoActivity.Intent_Text)
                    }
                }
                2 -> {//联系人手机
                    if (data != null) {
                        tv_contact_phone.text = data.getStringExtra(UserInfoActivity.Intent_Text)
                    }
                }

            }
    }
}