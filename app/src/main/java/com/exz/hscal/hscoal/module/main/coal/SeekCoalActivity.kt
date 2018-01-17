package com.exz.hscal.hscoal.module.main.coal


import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.CargoListAdapter
import com.exz.hscal.hscoal.bean.AreaBean
import com.exz.hscal.hscoal.bean.CargoListBean
import com.exz.hscal.hscoal.bean.PopStairListBean
import com.exz.hscal.hscoal.module.main.SerachActivity
import com.exz.hscal.hscoal.module.main.SerachActivity.Companion.Intent_Search_Content
import com.exz.hscal.hscoal.module.main.SerachActivity.Companion.RESULTCODE_SEARCH
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
 */

class SeekCoalActivity : BaseActivity(), OnRefreshListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {


    private var refreshState = com.szw.framelibrary.config.Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1

    private lateinit var sortPop: StairPop
    private lateinit var coalPop: StairPop
    private lateinit var arePop: AreaPop
    private lateinit var mAdapter: CargoListAdapter

    private var keyword = ""//关键词
    private var coalVarietyId = ""//煤种id
    private var provinceId = ""//省份id
    private var cityId = ""//城市id
    private var sortType = "0"//排序方式（0综合排序 1价格递增 2价格递减 3最新上架）

    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        SZWUtils.setPaddingSmart(mRecyclerView, 65f)
        SZWUtils.setMargin(header, 65f)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        edTitle.visibility = View.GONE
        tvTitle.visibility = View.VISIBLE
        toolbar.inflateMenu(R.menu.menu_seek_cocal_detail_text)
        val actionView = toolbar.menu.getItem(0).actionView
        actionView.setPadding(10,10,20,10)
        (actionView as TextView).text = "清除"
        actionView.setOnClickListener {
            edTitle.setText("")
            tvTitle.text = ""
            keyword = edTitle.text.toString().trim { it <= ' ' }
            onRefresh(refreshLayout)
        }

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


    private fun initRecycler() {
        edTitle.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // do something
                keyword = edTitle.text.toString().trim { it <= ' ' }
               onRefresh(refreshLayout)
                return@OnEditorActionListener true
            }
            false
        })
        mAdapter = CargoListAdapter()
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        rb1.setOnClickListener(this)
        rb2.setOnClickListener(this)
        rb3.setOnClickListener(this)
        tvTitle.setOnClickListener(this)

        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var entity=mAdapter.data.get(position)
                startActivity(Intent(mContext, SeekCocalDetailActivity::class.java).putExtra(SeekCocalDetailActivity.Intent_Id,entity.id))

            }
        })
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
    }

    var sortData = ArrayList<PopStairListBean>()
    var coalData = ArrayList<PopStairListBean>()
    private fun initPop() {
        sortData.add(PopStairListBean("综合排序", true, "0"))
        sortData.add(PopStairListBean("价格由低到高", false, "1"))
        sortData.add(PopStairListBean("价格由高到低", false, "2"))
        sortData.add(PopStairListBean("最新上架", false, "3"))
        coalData.add(PopStairListBean("全部煤种", true, ""))
        coalData.add(PopStairListBean("焦炭/焦粉/焦粒", false, "1"))
        coalData.add(PopStairListBean("炼焦煤", false, "2"))
        coalData.add(PopStairListBean("动力煤", false, "3"))
        sortPop = StairPop(mContext, {
            if (it != null) {
                if (it.name.equals("综合排序")) {
                    setGaryOrblue(rb1, false, "综合排序")
                } else {
                    setGaryOrblue(rb1, true, it.name)
                }

                sortType = it.id
                onRefresh(refreshLayout)

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
                if (it.name.equals("全部煤种")) {
                    setGaryOrblue(rb2, false, "全部煤种")
                } else {
                    setGaryOrblue(rb2, true, it.name)
                }
                coalVarietyId = it.id
                onRefresh(refreshLayout)

            }
        })
        coalPop.data = coalData
        coalPop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                radioGroup.clearCheck()
            }
        }
        arePop = AreaPop(mContext, { name, provinceId, cityId, check ->
            setGaryOrblue(rb3, check, name)
            this.provinceId = provinceId
            this.cityId = cityId
            refreshLayout.autoRefresh()
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
                if (!coalPop.isShowing) {
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


    override fun onRefresh(refreshLayout: RefreshLayout?) {
        currentPage = 1
        refreshState = com.szw.framelibrary.config.Constants.RefreshState.STATE_REFRESH
        iniData()

    }

    override fun onLoadMoreRequested() {
        refreshState = com.szw.framelibrary.config.Constants.RefreshState.STATE_LOAD_MORE
        iniData()
    }

    private fun iniData() {
        DataCtrlClass.CoalListData(mContext, currentPage, keyword, coalVarietyId, provinceId, cityId, sortType) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == com.szw.framelibrary.config.Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it)
                } else {
                    mAdapter.addData(it)

                }
                if (it.isNotEmpty()) {
                    mAdapter.loadMoreComplete()
                    currentPage++
                } else {
                    mAdapter.loadMoreEnd()
                }
            } else {
                mAdapter.loadMoreFail()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULTCODE_SEARCH -> {
                if (data != null) {
                    tvTitle.text = data.getStringExtra(Intent_Search_Content)
                    keyword=data.getStringExtra(Intent_Search_Content)
                    onRefresh(refreshLayout)
                }
            }
            else -> {
            }
        }
    }
}
