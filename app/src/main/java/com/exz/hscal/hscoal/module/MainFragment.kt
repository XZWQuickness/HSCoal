package com.exz.hscal.hscoal.module

import android.content.Intent
import android.os.Bundle
import android.os.Looper.getMainLooper
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.CargoListAdapter
import com.exz.hscal.hscoal.bean.BannersBean
import com.exz.hscal.hscoal.bean.CargoListBean
import com.exz.hscal.hscoal.bean.NewsBean
import com.exz.hscal.hscoal.bean.TabEntity
import com.exz.hscal.hscoal.imageloader.BannerImageLoader
import com.exz.hscal.hscoal.module.login.LoginActivity
import com.exz.hscal.hscoal.module.main.coal.SeekCoalActivity
import com.exz.hscal.hscoal.module.main.coal.SeekCocalDetailActivity
import com.exz.hscal.hscoal.module.main.goods.SeekGoodsActivity
import com.exz.hscal.hscoal.module.main.release.ReleaseGoodsActivity
import com.exz.hscal.hscoal.module.main.steel.SeekSteelActivity
import com.exz.hscal.hscoal.module.main.steel.SeekSteelDetailActivity
import com.exz.hscal.hscoal.module.mine.ApplyForDevelopersActivity
import com.exz.hscal.hscoal.utils.SZWUtils
import com.exz.hscal.hscoal.widget.MyWebActivity
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import com.tencent.map.geolocation.TencentLocation
import com.tencent.map.geolocation.TencentLocationListener
import com.tencent.map.geolocation.TencentLocationManager
import com.tencent.map.geolocation.TencentLocationRequest
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.header_main.view.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by pc on 2017/12/4.
 */

class MainFragment : MyBaseFragment(), OnRefreshListener, OnBannerListener, View.OnClickListener, TencentLocationListener {


    private lateinit var mLocationManager: TencentLocationManager

    private lateinit var mAdapter: CargoListAdapter
    private lateinit var headerView: View
    private var mScrollY = 0

    private var url = Urls.HotCoal
    private var requestCheck = "HotCoal"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_main, container, false)
        return rootView
    }

    override fun initView() {
        initBar()
        initLocation()
        initRecycler()
        initHeader()
        initBanner()
        initNews()
        refreshLayout.autoRefresh()
    }

    private fun initBanner() {
        DataCtrlClass.bannerData(context) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                banners = it
                //设置图片集合
                headerView.banner.setImages(it)
                //banner设置方法全部调用完毕时最后调用
                headerView.banner.start()
            }
        }
    }

    private fun initNews() {
        DataCtrlClass.TopNewsData(context) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                news = it
                for (bean in news) {
                    startWithList.add(bean.title)
                }
                headerView.marqueeView.startWithList(startWithList)
            }
        }
    }

    private fun initLocation() {

        mLocationManager = TencentLocationManager.getInstance(context)
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02)
        // 创建定位请求
        val request = TencentLocationRequest.create()
                .setInterval((5 * 1000).toLong()) // 设置定位周期
                .setAllowGPS(true)  //当为false时，设置不启动GPS。默认启动
                .setQQ("10001")
                .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME) // 设置定位level

        // 开始定位
        mLocationManager.requestLocationUpdates(request, this, getMainLooper())

    }

    override fun onStatusUpdate(p0: String?, p1: Int, p2: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(location: TencentLocation, error: Int, reason: String) {
        var msg: String? = null
        if (error == TencentLocation.ERROR_OK) {
            // 定位成功
            val sb = StringBuilder().apply {
                append("latitude=").append(location.getLatitude()).append(",")
                append("longitude=").append(location.getLongitude()).append(",")
                toast(append("address=").append(location.getAddress()).append(","))
            }
        } else {
            // 定位失败
            msg = "定位失败: " + reason
        }
    }

    private fun initBar() {
        toolbar.navigationIcon = null
        buttonBarLayout.alpha = 0f
        blurView.alpha = 0f
        mTitle.text = "首页"
        mTitle.setTextColor(ContextCompat.getColor(context, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
        StatusBarUtil.setPaddingSmart(activity, blurView)
    }

    private fun initRecycler() {
        mAdapter = CargoListAdapter()
        headerView = View.inflate(context, R.layout.header_main, null)
        mAdapter.addHeaderView(headerView)
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(context, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val h = DensityUtil.dp2px(170f)
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mScrollY += dy
                if (mScrollY < h) {
                    buttonBarLayout.alpha = 1f * mScrollY / h
                    blurView.alpha = 1f * mScrollY / h
                }

            }
        })



        headerView.bt_tab_1.setOnClickListener(this)
        headerView.bt_tab_2.setOnClickListener(this)
        headerView.bt_tab_3.setOnClickListener(this)
        headerView.bt_tab_4.setOnClickListener(this)
        headerView.bt_tab_5.setOnClickListener(this)

    }

    private val mTitles = arrayOf("煤炭货源", "钢材货源")
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()
    private var banners = ArrayList<BannersBean>()
    private var news = ArrayList<NewsBean>()
    private var startWithList = ArrayList<String>()
    private fun initHeader() {
        headerView.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        //设置图片加载器
        headerView.banner.setImageLoader(BannerImageLoader())
        //设置自动轮播，默认为true
        headerView.banner.isAutoPlay(true)
        //设置轮播时间
        headerView.banner.setDelayTime(3000)
        //设置指示器位置（当banner模式中有指示器时）
        headerView.banner.setIndicatorGravity(BannerConfig.CENTER)
        headerView.banner.setOnBannerListener(this)
        mTitles.indices.mapTo(mTabEntities) { TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        headerView.mainTabBar.setTabData(mTabEntities)

        headerView.marqueeView.setOnItemClickListener { position, textView ->


                startActivity(Intent(context, MyWebActivity::class.java).putExtra(MyWebActivity.Intent_Title, startWithList.get(position)).putExtra(MyWebActivity.Intent_Url, news.get(position).url))
        }

        headerView.mainTabBar.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                if (position == 0) {
                    url = Urls.HotCoal//煤炭
                    requestCheck = "HotCoal"
                    refreshLayout.autoRefresh()
                } else {
                    url = Urls.HotSteel//金属
                    requestCheck = "HotSteel"
                    refreshLayout.autoRefresh()
                }
            }

            override fun onTabReselect(position: Int) {
            }
        })

        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var entity=mAdapter.data.get(position)
                if(headerView.mainTabBar.currentTab==0) {
                    startActivity(Intent(context, SeekCocalDetailActivity::class.java).putExtra(SeekCocalDetailActivity.Intent_Id, entity.id))
                }else{
                    startActivity(Intent(context, SeekSteelDetailActivity::class.java).putExtra(SeekSteelDetailActivity.Intent_Id, entity.id))
                }
            }
        })

    }

    override fun OnBannerClick(position: Int) {
        startActivity(Intent(context, MyWebActivity::class.java).putExtra(MyWebActivity.Intent_Title, "Banner详情").putExtra(MyWebActivity.Intent_Url, banners.get(position).url))
    }

    override fun onClick(view: View) {
        when (view) {
            headerView.bt_tab_1 -> {//找煤炭
                startActivity(Intent(context, SeekCoalActivity::class.java))
            }
            headerView.bt_tab_2 -> {//找钢材
                startActivity(Intent(context, SeekSteelActivity::class.java))
            }
            headerView.bt_tab_3 -> {//找货源

                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), LoginActivity.RESULT_LOGIN_CANCELED)
                    return
                }
                //"供应商认证：-1未申请 0待审核，1已认证 2未通过",
                when (MineFragment.driverAuthentication) {
                    "-1" -> {
                        startActivity(Intent(context, ApplyForDevelopersActivity::class.java))
                    }
                    "0" -> {
                        toast("司机证审核中")
                    }
                    "1" -> {

                        startActivity(Intent(context, SeekGoodsActivity::class.java))
                    }
                    "2" -> {
                        startActivity(Intent(context, ApplyForDevelopersActivity::class.java).putExtra(ApplyForDevelopersActivity.Intent_State, MineFragment.driverAuthentication))
                    }
                }



            }
            headerView.bt_tab_4 -> {//发布询价
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), LoginActivity.RESULT_LOGIN_CANCELED)
                    return
                }
                //"供应商认证：-1未申请 0待审核，1已认证 2未通过",
                when (MineFragment.businessAuthentication) {
                    "-1" -> {
                        startActivity(Intent(context, ApplyForDevelopersActivity::class.java))
                    }
                    "0" -> {
                        toast("供应商认证审核中")
                    }
                    "1" -> {
                        startActivity(Intent(context, ReleaseGoodsActivity::class.java).putExtra(ReleaseGoodsActivity.Intent_Class_Name, "发布询价"))
                    }
                    "2" -> {
                        startActivity(Intent(context, ApplyForDevelopersActivity::class.java).putExtra(ApplyForDevelopersActivity.Intent_State, MineFragment.businessAuthentication))
                    }
                }


            }
            headerView.bt_tab_5 -> {//发布卖品
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), LoginActivity.RESULT_LOGIN_CANCELED)
                    return
                }
                //"供应商认证：-1未申请 0待审核，1已认证 2未通过",
                when (MineFragment.businessAuthentication) {
                    "-1" -> {
                        startActivity(Intent(context, ApplyForDevelopersActivity::class.java))
                    }
                    "0" -> {
                        toast("供应商认证审核中")
                    }
                    "1" -> {
                        startActivity(Intent(context, ReleaseGoodsActivity::class.java).putExtra(ReleaseGoodsActivity.Intent_Class_Name, "发布卖品"))
                    }
                    "2" -> {
                        startActivity(Intent(context, ApplyForDevelopersActivity::class.java).putExtra(ApplyForDevelopersActivity.Intent_State, MineFragment.businessAuthentication))
                    }
                }

            }
        }
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {

//        首页_成交量/成交金额
        DataCtrlClass.TurnoverData(context) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                headerView.coal_count.text = String.format(context.getString(R.string.main_coal), it.coalCount) + "吨"
                headerView.coal_price.text = it.coalPrice + "元"
                headerView.steel_count.text = String.format(context.getString(R.string.main_steel), it.steelCount) + "吨"
                headerView.steel_price.text = it.steelPrice + "元"

//                SZWUtils.matcherSearchTitle(  headerView.coal_count ,headerView.coal_count.text.toString().trim(),0,context.getString(R.string.main_coal).length,ContextCompat.getColor(context,R.color.MaterialGrey600))
//                SZWUtils.matcherSearchTitle( headerView.coal_price,headerView.coal_price.text.toString().trim(),0,context.getString(R.string.main_coal).length,ContextCompat.getColor(context,R.color.MaterialGrey600))
//                SZWUtils.matcherSearchTitle(headerView.steel_count,headerView.steel_count.text.toString().trim(),0,context.getString(R.string.main_steel).length,ContextCompat.getColor(context,R.color.MaterialGrey600))
//                SZWUtils.matcherSearchTitle(headerView.steel_price,headerView.steel_price.text.toString().trim(),0,context.getString(R.string.main_steel).length,ContextCompat.getColor(context,R.color.MaterialGrey600))
            }
        }
        DataCtrlClass.HomeHotData(context, url, requestCheck) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                mAdapter.setNewData(it)
                mAdapter.loadMoreEnd()
            }
        }
    }


    companion object {
        fun newInstance(): MainFragment {
            val bundle = Bundle()
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
