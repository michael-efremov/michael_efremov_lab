package ru.mike.lab.mvp.models

class PostsStack {

    private var mPosts: ArrayList<Post> = ArrayList()
    private var mCurrentPost = 0

    fun addPost(post: Post) {
        mPosts.add(post)
    }

    fun addPosts(posts: List<Post>) {
        mPosts.addAll(posts)
    }

    fun getPostPosition(post: Post): Int {
        return if (mPosts.isNotEmpty()) {
            mPosts.indexOf(post)
        } else {
            0
        }
    }

    fun getPost(position: Int): Post {
        return mPosts[position]
    }

    fun getCurrentPos(): Post {
        return mPosts[mCurrentPost]
    }

    fun checkPost(position: Int): Boolean {
        return mPosts.size > position
    }

    fun clear() {
        mPosts.clear()
    }

    fun isEmpty(): Boolean {
        return mPosts.isEmpty()
    }
}