package com.example.githubdemo.ui.page

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.blankj.utilcode.util.ToastUtils
import com.example.githubdemo.R
import com.example.githubdemo.ui.theme.BgPage
import com.example.githubdemo.ui.widget.AppTopBarView
import com.example.githubdemo.ui.widget.ColumnPage
import com.example.githubdemo.ui.widget.InputArea
import com.example.githubdemo.ui.widget.LoadingProgress
import com.example.githubdemo.ui.widget.TextButton
import com.example.githubdemo.viewmodel.EditIssuesViewModel


/**
 * FIXME
 *
 * @author lincanye (silverever07@gmail.com)
 * @Datetime 2024-08-24 13:22
 */
@Composable
fun EditIssuesPage(viewModel: EditIssuesViewModel, navController: NavController,  owner: String, repo: String) {
  val inputTitle = rememberSaveable {
    mutableStateOf("")
  }
  val inputBody = rememberSaveable {
    mutableStateOf("")
  }
  val state by viewModel.state.collectAsStateWithLifecycle()
  ColumnPage(horizontalAlignment = Alignment.CenterHorizontally) {
    AppTopBarView(stringResource(id = R.string.edit_issues)) {
      navController.popBackStack()
    }

    InputArea(modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 10.dp)
      .height(60.dp), bgColor = BgPage, text = inputTitle, hintWord = "input title", onStopInput = {
        viewModel.checkCommitEnable(inputTitle.value, inputBody.value)
    })

    Spacer(modifier = Modifier.height(20.dp))

    InputArea(modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 10.dp)
      .height(400.dp), bgColor = BgPage, text = inputBody, hintWord = "input body", onStopInput = {
      viewModel.checkCommitEnable(inputTitle.value, inputBody.value)
    })
    Spacer(modifier = Modifier.height(20.dp))
    TextButton(content = stringResource(id = R.string.commit), modifier = Modifier.width(355.dp).height(45.dp), radius = 8.dp, enabled = state.commitEnable) {
      viewModel.createIssue(owner, repo, inputTitle.value, inputBody.value)
    }
  }

  if (state.loading) {
    LoadingProgress()
  }
}