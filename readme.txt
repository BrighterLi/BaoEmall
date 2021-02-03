1、 整体架构
用FragmentTabHost实现底部菜单
ToolBar的基本使用
自定义ToolBar

2、 首页
1 用FragmentTabHost实现底部菜单
  (1) FragmentTabHost的用法
    1) Activity 要继承FragmentActivity
    2) 调用setup()方法
    3) 添加TabSpec
  (2) Selector背景选择器
      图标按下和未按下的显示效果不一样
  (3) 底部菜单的几种实现方式
      TabHost + Activity(比较老)
      RationButton + Fragment(代码量较大)
      FragmentTabHost + Fragment
      LinearLayout + TextView
      LinearLayout+TextView+红点(RelativeLayout) (建议使用这种方式)
  2 ToolBar的使用
       https://www.jianshu.com/p/05ef48b777cc
       ActionBar的升级版，比较灵活，不像ActionBar那么固定。
       Toolbar是一个ViewGroup，用来做App的标题栏，主要有5部分，导航Button、logo、正副标题、自定义View以及action menu。
    通过xml文件属性可以设置导航Button、logo和正副标题，通过代码设置action menu，利用自定义View可以解决标题不居中的问题。
       这里使用Toolbar有什么用，我自己写一个RelativeLayout或者其他什么布局都能实现，为啥非要用Toolbar呢？这里说一下，使用Toolbar比起传统的自定义布局的好处。
    第一、不需要考虑标题栏和系统状态栏匹配的问题，你自己写还得匹配系统状态栏；第二、就是Toolbar可以和其他的MD设计风格的空间连用，做出比较炫的效果，
    比如Toolbar+NestScrollView,Toolbar+DrawerLayout + NavigationView等等；第三、谷歌推荐的控件。
    (1)ToolBar的基本使用
    (2)ToolBar自定义

    3 主页Tab—自动轮播广告AndroidImageSlider
      https://github.com/daimajia/AndroidImageSlider
      (1)基本使用
      (2)事件监听
      (3)架构分析

    4 主页Tab—RecyclerView
    (1) RecyclerView是什么？
    RecyclerView是一种新的视图组，目标是为任何基于适配器的视图提供相似的渲染方式。它被作为ListView和GridView控件的升级版，在最新的support-V7版本中提供支持。
    整体上看RecyclerView架构，提供了一种更精美的体验，高度的适应力，异常的灵活，通过设置它提供的不同LayoutManager，ItemDecoration , ItemAnimator实现很多炫酷的效果。
    (2)事件监听
    自己定义
    (3)数据增删
    (4)动画

    5 主页Tab—首页商品分类
    (1) CardView
    CardView适用于实现卡片式布局效果的重要控件，由appcompat-v7库提供，实际上CardView也是一个FrameLayout，只是额外提供了圆角和阴影效果，看上去有立体的感觉。一般CardView都用在ListView的item布局中。

    6 OkHttp
    (1) Android中网络请求的进化
    HttpURLConnection —> Http Client(Apache ) —> Volley —> OkHttp
    (2)OkHttp
    Http Get
    OkHttpClient:客户端对象
    Request：OkHttp中的访问的请求
    Builder:辅助类
    Response:OkHttp中的响应
    Http Post(JSON)
    MediaType:数据类型
    RequestBody:请求数据

    7 OkHttp的简单封装
    get,post
    request
    callback

   8 主页Tab—主页商品分类重构

   9 Fresco(FaceBook)
   (1) Fresco介绍
   图片加载组件，图片下载和内存管理，图片显示
   (2)Fresco使用
   基本使用；渐进式加载图片；多图加载；事件监听

   10 下拉刷新SwipeRefreshLayout控件
    可以用于RecyclerView,ListView,GridView
    不支持上拉加载

   11热卖Tab—热门商品列表实现(下拉刷新，下拉加载)
   下拉刷新/下拉加载控件:MaterialRefreshLayout

   12 Adapter封装
   (1)假如有很多个RecyclerView，没必要每次把所有的方法写一遍，可以进行Adapter的封装。
   (2)封装方法
   1)数据使用泛型
   2)数据绑定通过实现抽象方法来实现
   3)ViewHolder中的View成员变量转而通过view数组来实现
   4)基类里面提供常用的方法

   13 商品分类Tab— 一级商品分类实现
   RecyclerView

   14 商品分类Tab— 二级商品列表实现
    RecyclerView

  15 购物车-自定义控件
   自定义数字加减控件:
   1)输入框只能是数字，且不能通过键盘输入
   2)通过加减按钮操作数字
   3)监听加减按钮
   4)数字有最小值和最大值区别
   5)自定义属性

  16 购物车-数据
  (1)购物车数据存储器
  1)数据存储在本地(用什么方式存储？为什么要存储)
  2)提供put、update、delete、getAll方法
  3)put数据时如购物车中存在相同的产品数量加1
  (2)SharedPreference
  要存储在本地，具体的数据可以转成json存在SharedPreference；不用本地数据库，是因为数据量不大

  17 购物车Tab—商品显示
   RecyclerView

  18 购物车Tab—商品选择
  RecyclerView—Adapter—data

  19 购物车Tab—购物车编辑实现

  20 分页工具类封装
  (1) 封装原则
  1)公共的东西抽出来
  2)变化的东西有调入者传入
  3)提供简洁的API

  21 商品列表
  点击首页商品进入商品列表页

  22商品列表—列表网格切换

  23 native与h5交互
  (1)Android与H5交互两种情况
  1)Android调用h5
  2)h5调用Android
  (2)步骤
  1)设置允许执行Js脚本
  webSettings.setJavaScriptEnabled(true);
  2)添加通信接口
  webView.addjavascriptInterface(Interface, "InterfaceName");
  3)JS调用Android
  InterfaceName.MethodName
  4)Android调用JS
  webView.loadUrl("javascript:functionName()");
  (3)loadUrl
  1)本包内部asset目录
  webView.loadUrl("file:///android_asset/index.html");
  2)本地sd卡内
  webView.loadUrl("content://com.android.htmlfileprovider/sdcard/index.html");
  3)指定URL的html文件
  webView.loadUrl("http://www.baidu.com");

  24 使用h5实现商品图文详情页面
  Android与h5交互

  25 商品分享
  分享功能
  mob.com

  26 用户登录
   密码加密 des
   服务器返回token+用户信息
  27 api权限
  token保护api,token验证成功后才会返回api数据
  401：token丢失；402：token错误；403：token过期
  28 APP登录拦截
    跳转过程中判断是否登录

  29 短信sdk集成
  用户注册
  30 获取短信验证
  短信验证码sdk文档
  请求，回调

  31 支付sdk
  支付宝，微信支付，百度钱包(公司才可以申请)
  第三方聚合：ping++(公司才可以申请);Bmob(个人可申请，不专业)
  服务端集成
  ping++集成：https://github.com/PingPlusPlus/pingpp-android/tree/master
  https://www.pingxx.com/docs/client/android.html
  32 提交订单
  33 支付

  34 添加收货地址
  地址：三级联动
  35 收件地址

  36我的

  37我的订单












