package hu.ait.aitforum.data

data class Post(
    var uid: String = "", // user id
    var author: String = "",
    var postDate: String = "",
    var postTitle: String = "",
    var postBody: String = "",
    var imgUrl: String = ""
)

// DTO Data Transfer Object
data class PostWithId(
    var postId: String = "",
    var post: Post
)