package com.exz.hscal.hscoal.module.mine

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.ScreenUtils
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.DataCtrlClass.checkDriverIdentityData
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.CheckBusinessIdentityBean
import com.exz.hscal.hscoal.bean.CheckDriverIdentityBean
import com.exz.hscal.hscoal.pop.SchemePop
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.PreferencesService
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
    private var url = Urls.SubmitDriverIdentity
    private var userName = ""
    private var mobile = ""
    private var driverLicense = ""
    private var vehicleLicense = ""
    private lateinit var mPop: SchemePop
    private var driverAuthentication = ""
    private lateinit var mEntity: CheckDriverIdentityBean.CheckResultBean
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
        if (intent.hasExtra(Intent_State)) {
            driverAuthentication = intent.getStringExtra(Intent_State)
            mPop = SchemePop(this)
            if(driverAuthentication.equals("2")){

                checkData()
            }
        }
        bt_contact_name.setOnClickListener(this)
        bt_contact_phone.setOnClickListener(this)
        bt_driving_licence.setOnClickListener(this)
        bt_driving.setOnClickListener(this)
        bt_commit.setOnClickListener(this)
    }

    /*
      *
      * 供应商审核结果接口
      *
      */
    private fun checkData() {
        checkDriverIdentityData(mContext,  {
            if (it != null) {
                mEntity = it.data!!.checkResult
                if (!TextUtils.isEmpty(mEntity.reason)) {
                    mPop.data = mEntity.reason
                    mPop.showPopupWindow()
                }

                //联系人姓名
                tv_contact_name.text = mEntity.userName.value
                setCheckTextColor(tv_contact_name, mEntity.userName.check)

                //联系人手机号
                tv_contact_phone.text = mEntity.mobile.value
                setCheckTextColor(tv_contact_phone, mEntity.mobile.check)


                //驾驶证照片
                iv_driving_licence_img.setImageURI(mEntity.driverLicense.value)
                setCheckTextColor(tvDriverLicense, mEntity.driverLicense.check)

                //行驶证照片
                iv_driving_img.setImageURI(mEntity.vehicleLicense.value)
                setCheckTextColor(tvVehicleLicense, mEntity.vehicleLicense.check)

            }
        })
    }


    private fun setCheckTextColor(text: TextView, check: String) {
        if (check.equals("1")) {//审核通过
            text.setTextColor(ContextCompat.getColor(mContext, R.color.MaterialGrey600))
        } else {//审核被拒
            text.setTextColor(ContextCompat.getColor(mContext, R.color.Red))
        }
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
                if (driverAuthentication.equals("-1")||TextUtils.isEmpty(driverAuthentication)) {
                    userName = tv_contact_name.text.toString().trim()
                    if (TextUtils.isEmpty(userName)) {
                        type = 1
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人姓名").putExtra(UserInfoActivity.Intent_Text, userName), UserInfoActivity.RESULTCODE_TEXT)
                        return
                    }
                    mobile = tv_contact_phone.text.toString().trim()
                    if (TextUtils.isEmpty(mobile)) {
                        type = 2
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人手机").putExtra(UserInfoActivity.Intent_Text, mobile), UserInfoActivity.RESULTCODE_TEXT)
                        return
                    }
                    if (TextUtils.isEmpty(driverLicense)) {
                        type = 3
                        PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                        return
                    }
                    if (TextUtils.isEmpty(vehicleLicense)) {
                        type = 4
                        PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                        return
                    }

                } else {
                    url = Urls.EditDriverIdentity

                    if (mEntity.userName.check.equals("2")) {
                        type = 1
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人姓名").putExtra(UserInfoActivity.Intent_Text, mEntity.userName.value), UserInfoActivity.RESULTCODE_TEXT)
                        return
                    }
                    if (mEntity.mobile.check.equals("2")) {
                        type = 2
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人手机").putExtra(UserInfoActivity.Intent_Text, mEntity.mobile.value), UserInfoActivity.RESULTCODE_TEXT)
                        return
                    }

                    if (mEntity.driverLicense.check.equals("2")) {
                        type = 3
                        PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                        return
                    }
                    if (mEntity.vehicleLicense.check.equals("2")) {
                        type = 4
                        PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                        return
                    }

                }
                DataCtrlClass.applyForDriver(mContext, userName, mobile, driverLicense, vehicleLicense, url, {
                   if(it!=null) finish()
                })

            }


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) { //图片选择
            val images = data?.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>
            if (type == 3) { //驾驶证照片
                iv_driving_licence_img.setImageURI("file://" + (images.get(0) as ImageItem).path)
                driverLicense = (images.get(0) as ImageItem).path

                if (driverAuthentication.equals("2")) {
                    mEntity.driverLicense.check = "1"
                    setCheckTextColor(tvDriverLicense, mEntity.driverLicense.check)
                }
            } else if (type == 4) { //行驶证照片（数据流）
                iv_driving_img.setImageURI("file://" + (images.get(0) as ImageItem).path)
                vehicleLicense = (images.get(0) as ImageItem).path
                if (driverAuthentication.equals("2")) {
                    mEntity.vehicleLicense.check = "1"
                    setCheckTextColor(tvVehicleLicense, mEntity.vehicleLicense.check)
                }
            }
        } else
            when (type) {
                1 -> {//联系人姓名
                    if (data != null) {
                        tv_contact_name.text = data.getStringExtra(UserInfoActivity.Intent_Text)

                        tv_contact_name.text = data.getStringExtra(UserInfoActivity.Intent_Text)
                        if (driverAuthentication.equals("2")) {
                            userName = data.getStringExtra(UserInfoActivity.Intent_Text)
                            mEntity.userName.check = "1"
                            setCheckTextColor(tv_contact_name, mEntity.userName.check)
                        }
                    }
                }
                2 -> {//联系人手机
                    if (data != null) {
                        tv_contact_phone.text = data.getStringExtra(UserInfoActivity.Intent_Text)

                        if (driverAuthentication.equals("2")) {
                            mobile = data.getStringExtra(UserInfoActivity.Intent_Text)
                            mEntity.mobile.check = "1"
                            setCheckTextColor(tv_contact_phone, mEntity.mobile.check)
                        }
                    }
                }

            }
    }

    companion object {
        var Intent_State = "state"
    }
}