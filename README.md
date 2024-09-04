# Feature
* 探索页，可不登录查看
*  搜索页，可不登录查看，包含语言过滤和各种排序
*  我的页面，包含我的仓库，未登录情况下为登录界面
*  仓库列表，选择某项点击右上角的issues按钮，进入提交Issue界面
*  登录是跳转网页请求GitHub授权
*  设置页面，包含我的资料和收藏的仓库入口和切换语言按钮和登出按钮

# 使用到的技术点
* JetPack Compose
* MVI
* ViewModel, Lifecycle, Hilt
* Retrofit, OKHttp3
* DataStore
* UI test, compose test, hilt test

# Build and Run
* 已提供GitHubClientID和GitHubClientSecret，可直接编译运行
* app连接到GitHub，需要使用VPN
* 主要的测试用例在package com.example.githubdemo.MyComposeTest中
* 使用中模拟已登录状态，需要提供自己的github accessToken，赋值给ownValidAccessToken