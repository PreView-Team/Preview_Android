package preview.android.activity.util

import preview.android.model.Post

fun createBestPostList(): List<Post> {
    val list = arrayListOf<Post>()
    list.add(Post(title = "first", writer = "1", like = 20))
    list.add(Post(title = "second", writer = "2", like = 21))
    list.add(Post(title = "third", writer = "3", like = 22))
    list.add(Post(title = "fourth", writer = "4", like = 23))

    return list
}
