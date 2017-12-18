package com.exz.hscal.hscoal.module.main.steel

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.alibaba.fastjson.JSON
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.CargoListAdapter
import com.exz.hscal.hscoal.bean.AreaBean
import com.exz.hscal.hscoal.bean.CargoListBean
import com.exz.hscal.hscoal.bean.PopStairListBean
import com.exz.hscal.hscoal.module.main.SerachActivity
import com.exz.hscal.hscoal.pop.AreaPop
import com.exz.hscal.hscoal.pop.StairPop
import com.exz.hscal.hscoal.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import com.szw.framelibrary.view.DrawableCenterButton
import kotlinx.android.synthetic.main.action_bar_search.*
import kotlinx.android.synthetic.main.activity_search_coal.*
import org.jetbrains.anko.textColor
import razerdp.basepopup.BasePopupWindow
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by pc on 2017/12/5.
 * 找钢材
 */

class SeekSteelActivity : BaseActivity(), OnRefreshListener, View.OnClickListener {

    private lateinit var sortPop: StairPop
    private lateinit var coalPop: StairPop
    private lateinit var arePop: AreaPop
    private lateinit var mAdapter: CargoListAdapter
    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        SZWUtils.setPaddingSmart(mRecyclerView, 55f)
        SZWUtils.setMargin(header, 55f)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        edTitle.visibility = View.GONE
        tvTitle.visibility = View.VISIBLE

        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_search_coal
    }

    override fun init() {
        super.init()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//关闭软键盘
        initRecycler()
        initPop()
        refreshLayout.autoRefresh()
    }


    private var data = ArrayList<CargoListBean>()
    private fun initRecycler() {
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        data.add(CargoListBean())
        mAdapter = CargoListAdapter()
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        rb1.setOnClickListener(this)
        rb2.setOnClickListener(this)
        rb3.setOnClickListener(this)
        tvTitle.setOnClickListener(this)

        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(mContext, SeekSteelDetailActivity::class.java))

            }
        })
    }

    var sortData = ArrayList<PopStairListBean>()
    var coalData = ArrayList<PopStairListBean>()
    private fun initPop() {
        sortData.add(PopStairListBean("综合排序", true, "1"))
        sortData.add(PopStairListBean("价格由低到高", false, "1"))
        sortData.add(PopStairListBean("价格由高到低", false, "1"))
        sortData.add(PopStairListBean("最新上架", false, "1"))
        coalData.add(PopStairListBean("全部", true, "1"))
        coalData.add(PopStairListBean("热卷", false, "1"))
        coalData.add(PopStairListBean("建材", false, "1"))
        coalData.add(PopStairListBean("中厚板", false, "1"))
        coalData.add(PopStairListBean("冷轧涂膜", false, "1"))
        coalData.add(PopStairListBean("型管", false, "1"))
        sortPop = StairPop(mContext, {
            if (it != null) {
                if (it.name.equals("综合排序")) {
                    setGaryOrblue(rb1, false, "综合排序")
                } else {
                    setGaryOrblue(rb1, true, it.name)
                }

            }
        })
        sortPop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
            }
        }
        sortPop.data = sortData
        coalPop = StairPop(mContext, {
            if (it != null) {
                if (it.name.equals("全部")) {
                    setGaryOrblue(rb2, false, "全部")
                } else {
                    setGaryOrblue(rb2, true, it.name)
                }

            }
        })
        coalPop.data = coalData
        coalPop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
            }
        }
        arePop = AreaPop(mContext, { name, id, check ->

            setGaryOrblue(rb3, check, name)
        })
        arePop.data = (JSON.parseArray(getJson(), AreaBean::class.java) as ArrayList<AreaBean>)
        arePop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
            }
        }

    }

    private fun getJson(): String {

        val stringBuilder = StringBuilder()
        try {
            val assetManager = mContext.assets
            val bf = BufferedReader(InputStreamReader(
                    assetManager.open("area.json")))
            var b = true
            while (b) {
                val line = bf.readLine()
                if (line != null) {
                    stringBuilder.append(line)
                } else {
                    b = false
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }

    private fun setGaryOrblue(rb: DrawableCenterButton, check: Boolean, name: String) {
        if (!TextUtils.isEmpty(name)) rb.text = name
        if (check) {
            rb.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.blue_arrow), null)
            rb.textColor = ContextCompat.getColor(mContext, R.color.colorPrimary)

        } else {
            rb.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.gray_arrow), null)
            rb.textColor = ContextCompat.getColor(mContext, R.color.MaterialGrey600)
        }
    }


    override fun onClick(v: View) {
        when (v) {
            rb1 -> {
                if (!sortPop.isShowing) {
                    sortPop.showPopupWindow(radioGroup)
                } else {
                    radioGroup.clearCheck()
                }

            }
            rb2 -> {
                if (!coalPop.isShowing) {
                    coalPop.showPopupWindow(radioGroup)
                } else {
                    radioGroup.clearCheck()
                }
            }
            rb3 -> {
                if (!arePop.isShowing) {
                    arePop.showPopupWindow(radioGroup)
                } else {
                    radioGroup.clearCheck()
                }

            }
            tvTitle -> {
                startActivityForResult(Intent(mContext, SerachActivity::class.java), SerachActivity.RESULTCODE_SEARCH)
            }
        }
    }


    override fun onRefresh(refreshlayout: RefreshLayout?) {
        refreshlayout?.finishRefresh()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            SerachActivity.RESULTCODE_SEARCH -> {
                if (data != null) {
                    tvTitle.text = data.getStringExtra(SerachActivity.Intent_Search_Content)
                }
            }
            else -> {
            }
        }
    }
}