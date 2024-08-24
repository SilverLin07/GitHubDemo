package com.example.githubdemo.data.bean


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-24 13:38
 */
data class IssueResponse(
  val id: Long,
  val title: String,
  val body: String,
  val state: String,
  val url: String
)