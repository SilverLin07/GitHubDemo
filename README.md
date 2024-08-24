# Feature
* 探索页，可不登录查看
*  搜索页，可不登录查看，包含语言过滤和各种排序
*  我的页面，包含我的仓库，未登录情况下为登录界面
*  登录是跳转网页请求GitHub授权
*  设置页面，包含我的资料和收藏的仓库入口和切换语言按钮和登出按钮

# 使用到的技术点
* JetPack Compose
* MVI
* ViewModel, Lifecycle, Hilt
* Retrofit, OKHttp3
* DataStore

# Build and Run
* 已提供GitHubClientID和GitHubClientSecret，可直接编译运行

PS.
需求有部分不太明确，开发时最好在足够明确的情况下进行