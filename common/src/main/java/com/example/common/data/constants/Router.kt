package com.example.common.data.constants

object Router {
  const val SPLASH_PAGE = "SPLASH_PAGE"
  const val EXPLORE_PAGE = "EXPLORE_PAGE"
  const val SEARCH_PAGE = "SEARCH_PAGE"
  const val MINE_PAGE = "MINE_PAGE"

  const val WEB_BASE_PAGE = "common/WebPage"
  const val WEB_PAGE = "common/WebPage/{url}"

  fun logOut() {
//    AppSessionUtil.onLogOut()
//    // 退出IM的登录
//    val intent = Intent(ContextUtils.getContext(), Class.forName("com.toto.gi.MainActivity"))
//    intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TOP
//    val activity = ContextUtils.getActivity()
//    ContextUtils.setActivity(null)
//    activity?.startActivity(intent)
  }
}