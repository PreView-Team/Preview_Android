package preview.android.activity.util

import preview.android.model.Post

fun createBestPostList(): List<Post> {
    val list = arrayListOf<Post>()
    list.add(Post(title = "first", content = "content1", writer = "1", likes = 20))
    list.add(Post(title = "second", content = "content2", writer = "2", likes = 21))
    list.add(Post(title = "third", content = "content3", writer = "3", likes = 22))
    list.add(Post(title = "fourth", content = "content4", writer = "4", likes = 23))

    return list
}
