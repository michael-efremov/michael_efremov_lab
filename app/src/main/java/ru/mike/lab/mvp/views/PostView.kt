package ru.mike.lab.mvp.views

import com.bumptech.glide.load.resource.gif.GifDrawable
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.mike.lab.mvp.presenters.State

@StateStrategyType(OneExecutionStateStrategy::class)
interface PostView : MvpView {

    fun onMessage(message: String)
    fun onLoadGif(gif: GifDrawable)
    fun onBackEnable(isEnable: Boolean)
    fun setState(state: State)

}