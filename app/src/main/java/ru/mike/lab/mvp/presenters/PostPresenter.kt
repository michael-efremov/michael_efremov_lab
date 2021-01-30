package ru.mike.lab.mvp.presenters

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.mike.lab.mvp.models.PostsStack
import ru.mike.lab.R
import ru.mike.lab.mvp.models.ResponsePosts
import ru.mike.lab.mvp.views.PostView
import ru.mike.lab.network.ApiProvider
import ru.mike.lab.network.ApiResponse

sealed class State {
    object Load : State()
    object Empty : State()
    class Error(val error: String?) : State()
}

@InjectViewState
class PostPresenter(private val context: Context, private val apiProvider: ApiProvider) :
    MvpPresenter<PostView>() {

    private val mPostsStack = PostsStack()
    private var mSection: String = "latest"
    private var mPosition = 0
    private var mPage = 0

    fun getSection(section: String, page: Int = 0) {
        mSection = section

        viewState.setState(State.Load)
        CoroutineScope(Dispatchers.IO).launch {

            val response = apiProvider.getSectionPost(section, page)
            if (response is ApiResponse.Success) {
                val posts = getResponse<ResponsePosts>(response)

                posts?.posts?.let {
                    if (it.isEmpty()) {
                        withContext(Dispatchers.Main) {
                            viewState.setState(State.Empty)
                        }
                    } else {
                        mPostsStack.addPosts(it)
                        getPost()
                    }
                }

            } else {
                withContext(Dispatchers.Main) {
                    mPostsStack.clear()
                    val error = response as ApiResponse.Error
                    viewState.setState(State.Error(error.exception.message))
                }
            }

        }
    }

    fun getNextPost() {
        mPosition += 1
        getPost()

        viewState.onBackEnable(true)
    }

    fun getPreviousPost() {
        mPosition -= 1
        getPost()

        if (mPosition == 0) {
            viewState.onBackEnable(false)
        }

    }

    private fun getPost() {
        if (mPostsStack.checkPost(mPosition)) {
            val post = mPostsStack.getPost(mPosition)
            loadGif(post.gifURL)
        } else {
            mPage += 1
            getSection(mSection, mPage)
        }
    }

    private fun loadGif(url: String?) {
        Glide.with(context)
            .asGif()
            .skipMemoryCache(false)
            .load(url).into(MyTarget())
    }

    inner class MyTarget : CustomTarget<GifDrawable>() {

        override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            viewState.setState(State.Error(context.getString(R.string.error_load_image)))
        }

        override fun onLoadCleared(placeholder: Drawable?) {

        }

        override fun onResourceReady(
            resource: GifDrawable,
            transition: Transition<in GifDrawable>?
        ) {
            CoroutineScope(Dispatchers.Main).launch {
                viewState.onLoadGif(resource)
            }
        }

    }

    private inline fun <reified T> getResponse(response: ApiResponse): T? {
        return if (response is ApiResponse.Success && response.response is T) {
            response.response
        } else null
    }


}