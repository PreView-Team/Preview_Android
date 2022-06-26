package preview.android.activity.util

import preview.android.model.Post

fun createBestPostList(): List<Post> {
    val list = arrayListOf<Post>()
    list.add(Post(title = "first", createDate = "2022.06.26", content = "content1\ncontent1", writer = "1", likes = 20))
    list.add(Post(title = "second", createDate = "2022.06.26", content = "content2\ncontent2", writer = "2", likes = 21))
    list.add(Post(title = "third", createDate = "2022.06.26", content = "content3\ncontent3", writer = "3", likes = 22))
    list.add(Post(title = "fourth", createDate = "2022.06.26", content = "content4\ncontnet4", writer = "4", likes = 23))

    return list
}
