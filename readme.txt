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

    3 自动轮播广告AndroidImageSlider
      https://github.com/daimajia/AndroidImageSlider
      (1)基本使用
      (2)事件监听
      (3)架构分析

    4 RecyclerView
    (1) RecyclerView是什么？
    RecyclerView是一种新的视图组，目标是为任何基于适配器的视图提供相似的渲染方式。它被作为ListView和GridView控件的升级版，在最新的support-V7版本中提供支持。
    整体上看RecyclerView架构，提供了一种更精美的体验，高度的适应力，异常的灵活，通过设置它提供的不同LayoutManager，ItemDecoration , ItemAnimator实现很多炫酷的效果。
    (2)事件监听
    自己定义
    (3)数据增删
    (4)动画

    5 首页商品分类
    (1) CardView
    CardView适用于实现卡片式布局效果的重要控件，由appcompat-v7库提供，实际上CardView也是一个FrameLayout，只是额外提供了圆角和阴影效果，看上去有立体的感觉。一般CardView都用在ListView的item布局中。










