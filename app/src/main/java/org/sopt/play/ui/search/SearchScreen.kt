package org.sopt.play.ui.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.play.ui.search.component.BottomSheet
import org.sopt.play.ui.search.component.FloatingButton
import org.sopt.play.ui.search.component.PostCard
import org.sopt.play.ui.search.component.PostModal
import org.sopt.play.ui.search.component.SearchTextField
import org.sopt.play.ui.search.model.Post

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.Factory,
    ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    SearchScreen(
        posts = state.posts,
        modifier = modifier,
        onCreatePost = viewModel::createPost,
        onDeletePost = viewModel::deletePost,
    )
}

@Composable
fun SearchScreen(
    posts: List<Post>,
    modifier: Modifier = Modifier,
    onCreatePost: (String, String) -> Unit = { _, _ -> },
    onDeletePost: (Long) -> Unit = {},
) {
    var value by remember { mutableStateOf("") }

    var showBottomSheet by remember { mutableStateOf(false) }

    var selectedPost by remember { mutableStateOf<Post?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            SearchTextField(
                value = value,
                onValueChange = { value = it },
                modifier = Modifier.padding(vertical = 12.dp),
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(posts) { post ->
                    PostCard(
                        author = post.author,
                        date = post.date,
                        title = post.title,
                        content = post.content,
                        modifier = Modifier.combinedClickable(
                            onClick = {},
                            onLongClick = {
                                selectedPost = post
                            },
                        ),
                    )
                }
            }
        }
        FloatingButton(
            onClick = {
                showBottomSheet = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        )
    }

    if (showBottomSheet) {
        BottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            onComplete = { title, content ->
                onCreatePost(title, content)
                showBottomSheet = false
            },
        )
    }

    val post = selectedPost

    if (post != null) {
        Dialog(
            onDismissRequest = {
                selectedPost = null
            },
        ) {
            PostModal(
                onCancel = {
                    selectedPost = null
                },
                onDelete = {
                    onDeletePost(post.id)
                    selectedPost = null
                },
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 640, showBackground = true,)
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        posts = listOf(
            Post(
                id = 1L,
                author = "민나",
                date = "2026.10.19",
                title = "웹토리들아",
                content = "세미나 열심히 듣고 있나요?",
            ),
            Post(
                id = 2L,
                author = "힘멜도연",
                date = "2026.10.06",
                title = "안드들아",
                content = "지각하면 가만안드",
            ),
        ),
    )
}