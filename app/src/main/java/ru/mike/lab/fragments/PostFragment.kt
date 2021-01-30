package ru.mike.lab.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import com.bumptech.glide.load.resource.gif.GifDrawable
import kotlinx.android.synthetic.main.include_placeholders_text.*
import kotlinx.android.synthetic.main.post_fragment_layout.*
import kotlinx.android.synthetic.main.post_fragment_layout.placeholder_layout
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.mike.lab.R
import ru.mike.lab.mvp.presenters.PostPresenter
import ru.mike.lab.mvp.presenters.State
import ru.mike.lab.mvp.views.PostView
import ru.mike.lab.network.Api
import ru.mike.lab.network.ApiProviderImpl


class PostFragment : MvpAppCompatFragment(), PostView {

    companion object {
        const val ARGUMENTS_TYPE = "TYPE"
    }

    @InjectPresenter
    lateinit var mPresenter: PostPresenter

    @ProvidePresenter
    fun providePresenter(): PostPresenter {
        return PostPresenter(requireActivity().applicationContext, ApiProviderImpl(Api.getApi()))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton.setOnClickListener {
            mPresenter.getNextPost()
        }

        backButton.setOnClickListener {
            mPresenter.getPreviousPost()
        }

        retryButton.setOnClickListener {
            load()
        }

        load()
    }

    override fun onBackEnable(isEnable: Boolean) {
        backButton.isEnabled = isEnable
    }

    override fun onLoadGif(gif: GifDrawable) {
        progressBar.isVisible = false

        val fadeOut = AlphaAnimation(1f, 0f).apply {
            interpolator = DecelerateInterpolator()
            duration = 300
        }
        postImage.animation = fadeOut
        postImage.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.emty_drawable
            )
        )

        val fadeIn: Animation = AlphaAnimation(0f, 1f).apply {
            interpolator = DecelerateInterpolator()
            duration = 300

        }
        postImage.animation = fadeIn

        postImage.setImageDrawable(gif)
        gif.start()

    }

    override fun onMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun setState(state: State) {
        when (state) {
            is State.Load -> {
                placeholder_layout.isVisible = false
                nextButton.isEnabled = true
                progressBar.isVisible = true
                postImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.emty_drawable
                    )
                )
            }
            is State.Error -> {
                placeholder_layout.isVisible = true
                placeholderText.text = state.error
                nextButton.isEnabled = false
                backButton.isEnabled = false
                progressBar.isVisible = false
                postImage.setImageDrawable(null)
            }
            is State.Empty -> {
                placeholder_layout.isVisible = true
                placeholderText.text = getString(R.string.gif_empty)
                nextButton.isEnabled = false
                backButton.isEnabled = false
                progressBar.isVisible = false
                postImage.setImageDrawable(null)
            }
        }
    }

    private fun load() {
        arguments?.getString(ARGUMENTS_TYPE, "")?.let {
            mPresenter.getSection(it)
        } ?: setState(State.Empty)
    }

}