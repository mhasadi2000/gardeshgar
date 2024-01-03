package tn.yassin.discovery
import tn.yassin.discovery.Models.PostsAdmin

class PostData {

    companion object {
        var MyPostsModels: ArrayList<PostsAdmin> = arrayListOf(
            PostsAdmin("1","آزادی","azadi injast","azadi.jpg","desert","2","میدان آزادی"),
            PostsAdmin("2","eram","azadi injast","eram.jpg","desert","3"),
            PostsAdmin("3","golestan","azadi injast","golestan.jpg","desert","4")
        )

        var MyC1PostsModels: ArrayList<PostsAdmin> = arrayListOf(
            PostsAdmin("1","آزادی","azadi injast","azadi.jpg","desert","2"),
            PostsAdmin("2","eram","azadi injast","eram.jpg","desert","3"),
            PostsAdmin("3","golestan","azadi injast","golestan.jpg","desert","4")
        )

        var MyC2PostsModels: ArrayList<PostsAdmin> = arrayListOf(
            PostsAdmin("1","آزادی","azadi injast","azadi.jpg","desert","2"),
            PostsAdmin("2","eram","azadi injast","eram.jpg","desert","3"),
            PostsAdmin("3","golestan","azadi injast","golestan.jpg","desert","4")
        )

        var MyC3PostsModels: ArrayList<PostsAdmin> = arrayListOf(
            PostsAdmin("1","آزادی","azadi injast","azadi.jpg","desert","2"),
            PostsAdmin("2","eram","azadi injast","eram.jpg","desert","3"),
            PostsAdmin("3","golestan","azadi injast","golestan.jpg","desert","4")
        )

        fun fetchPostsModels(): ArrayList<PostsAdmin> {
            return MyPostsModels
        }

        fun fetchC1PostsModels(): ArrayList<PostsAdmin> {
            return MyC1PostsModels
        }

        fun fetchC2PostsModels(): ArrayList<PostsAdmin> {
            return MyC2PostsModels
        }

        fun fetchC3PostsModels(): ArrayList<PostsAdmin> {
            return MyC3PostsModels
        }
    }
}

