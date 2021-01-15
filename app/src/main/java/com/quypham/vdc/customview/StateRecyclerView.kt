package com.quypham.vdc.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.quypham.vdc.R
import com.quypham.vdc.databinding.RecyclerErrorViewBinding
import com.quypham.vdc.databinding.RecyclerLoadingViewBinding
import com.quypham.vdc.databinding.StateRecyclerViewBinding

class StateRecyclerView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    private val mBinding: StateRecyclerViewBinding = StateRecyclerViewBinding.inflate(
        LayoutInflater.from(context), this
    )

    private val mErrorViewBinding: RecyclerErrorViewBinding

    private val mLoadingViewBinding: RecyclerLoadingViewBinding

    val recyclerView: RecyclerView
        get() = mBinding.recyclerView

    var errorText: String = ""
        set(value) {
            field = value
            mErrorViewBinding.tvErrorMessage.text = value
        }

    @DrawableRes
    var errorIcon = 0
        set(value) {
            field = value
            mErrorViewBinding.ivError.setImageResource(value)
        }

    init {

        // inflate the layout
        mErrorViewBinding = mBinding.errorView
        mLoadingViewBinding = mBinding.loadingView

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StateRecyclerView,
            0,
            0
        ).apply {
            try {
                errorText = getString(R.styleable.StateRecyclerView_errorText) ?: context.getString(
                    R.string.general_error
                )
                errorIcon = getResourceId(
                    R.styleable.StateRecyclerView_errorIcon,
                    R.drawable.baseline_error_outline_white_48
                )
            } finally {
                recycle()
            }
        }
    }

    fun showRecyclerView() {
        mLoadingViewBinding.root.visibility = View.GONE
        mErrorViewBinding.root.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    fun showErrorView(errorMess: String) {
        errorText = errorMess
        mLoadingViewBinding.root.visibility = View.GONE
        recyclerView.visibility = View.GONE
        mErrorViewBinding.root.visibility = View.VISIBLE
    }

    fun showLoadingView() {
        mLoadingViewBinding.root.visibility = View.VISIBLE
        mErrorViewBinding.root.visibility = View.GONE
    }
}