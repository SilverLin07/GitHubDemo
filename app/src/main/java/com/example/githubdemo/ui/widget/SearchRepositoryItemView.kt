package com.example.githubdemo.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.common.ext.noRippleClickable
import com.example.githubdemo.R
import com.example.githubdemo.data.bean.RepositoryItem
import com.example.githubdemo.ui.theme.FontHint
import com.example.githubdemo.ui.theme.Purple40


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-23 19:08
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchRepositoryItemView(item: RepositoryItem, onClick: () -> Unit = {}, showIssues: Boolean = false, onClickIssues: () -> Unit = {}) {
  Row(modifier = Modifier
    .padding(10.dp)
    .fillMaxWidth()
    .noRippleClickable {
      onClick()
    }) {
    AsyncImage(model = item.owner.avatarUrl, modifier = Modifier
      .clip(CircleShape)
      .size(60.dp), contentDescription = null)
    Spacer(modifier = Modifier.width(20.dp))
    Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = item.fullName, fontSize = 14.sp, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
        if (showIssues) {
          Spacer(modifier = Modifier.width(10.dp))
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.noRippleClickable {
              onClickIssues()
            }) {
            Image(
              painter = painterResource(id = R.drawable.ic_issues),
              contentDescription = null,
              modifier = Modifier.size(20.dp)
            )
            Text(text = "issues", fontSize = 12.sp, color = FontHint)
          }
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
      FlowRow(verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.ic_star_small), modifier = Modifier.size(16.dp), contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = item.stargazersCount.toString(), color = FontHint, fontSize = 12.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Image(painter = painterResource(id = R.drawable.ic_fork_small), modifier = Modifier.size(16.dp), contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = item.forks.toString(), color = FontHint, fontSize = 12.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Image(painter = painterResource(id = R.drawable.ic_time_small), modifier = Modifier.size(16.dp), contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = item.updatedAtTimeFormat, color = FontHint, fontSize = 12.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Image(painter = painterResource(id = R.drawable.ic_storage_small),modifier = Modifier.size(16.dp), contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "${item.size}KB", color = FontHint, fontSize = 12.sp)
        if (!item.language.isNullOrBlank()) {
          Spacer(modifier = Modifier.width(10.dp))
          Text(text = item.language, color = Purple40, fontSize = 14.sp)
        }
      }
    }
  }
}