package com.exz.hscal.hscoal.module.mine.address

import android.app.Activity
import android.graphics.PixelFormat
import android.net.Uri
import android.text.TextUtils
import android.view.View
import com.exz.hscal.hscoal.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import android.view.WindowManager
import com.blankj.utilcode.util.KeyboardUtils
import com.common.controls.dialog.ICommonDialog
import com.exz.hscal.hscoal.utils.DialogType104
import kotlinx.android.synthetic.main.activity_map_webview.*
import kotlinx.android.synthetic.main.dialog_address.view.*
import java.net.URLDecoder


/**
 * Created by pc on 2018/1/12.
 */

class MapWebViewActivity : BaseActivity() {
    var dialog: ICommonDialog?=null
    lateinit var  viewDialog:View
    var latitude=""
    var longitude=""
    override fun initToolbar(): Boolean {
        mTitle.text = "设置详细地址"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        //频为了避免闪屏和透明问题，Activity在onCreate时需要设置:
        window.setFormat(PixelFormat.TRANSLUCENT)
        //输入法遮挡问题
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return R.layout.activity_map_webview
    }

    override fun init() {
        super.init()
        iniDaialog()
        initView()
    }

    private fun iniDaialog() {
        viewDialog = View.inflate(mContext, R.layout.dialog_address, null)
        dialog = DialogType104(mContext)
        (dialog as DialogType104).setContentView(viewDialog)
        (dialog as DialogType104).setTitleText("详细地址")
        (dialog as DialogType104).setOkBtn("确定") { v ->
            var conten = viewDialog.ed_content.text.toString().trim()
            if (!TextUtils.isEmpty(conten)) {
                (dialog as DialogType104).dismiss()
                val intent = intent
                intent.putExtra("content", conten)
                intent.putExtra("longitude", longitude)
                intent.putExtra("latitude", latitude)
                setResult(Activity.RESULT_OK, intent)
                finish()

            }


        }
        (dialog as DialogType104).setCancelBtn("取消", { v ->
            (dialog as DialogType104).dismiss()
        })
        (dialog as DialogType104).setCanceledOnTouchOutside(false)

        (dialog as DialogType104).setOnShowListener { KeyboardUtils.toggleSoftInput() }
        (dialog as DialogType104).setOnBeforeDismiss {
            KeyboardUtils.hideSoftInput(viewDialog.ed_content)
            true
        }
    }

    private fun initView() {


        var webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        mWebView.loadUrl("https://3gimg.qq.com/lightmap/components/locationPicker2/index.html?search=1&type=0&backurl=http%3A%2F%2F3gimg.qq.com%2Flightmap%2Fcomponents%2FlocationPicker2%2Fback.html&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&referer=myapp")



        mWebView.setWebViewClient(object : com.tencent.smtt.sdk.WebViewClient() {

            override fun shouldOverrideUrlLoading(v: com.tencent.smtt.sdk.WebView, url: String): Boolean {

                if (!url.contains("http://3gimg.qq.com/lightmap/components/locationPicker2/back.html")) {
                    v.loadUrl(url);
                } else if (url.contains("http://3gimg.qq.com/lightmap/components/locationPicker2/back.html?name=")) {
                    val decode = URLDecoder.decode(url, "UTF-8")
                    val uri = Uri.parse(decode)

                    val latng = uri.getQueryParameter("latng")//纬度在前，经度在后，以逗号分隔
                    val split = latng.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    latitude = split[0]
                    longitude = split[1]
                    viewDialog.ed_content.setText(uri.getQueryParameter("name"))
                    viewDialog.ed_content.setSelection( viewDialog.ed_content.text.toString().trim().length)
                    if(dialog!=null&&!dialog!!.isShow){
                        dialog!!.show()
                    }


                }
                return true
            }

        })



    }   }
