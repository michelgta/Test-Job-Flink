package mx.com.testjobflink.utils.extensions

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import mx.com.testjobflink.R

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.observe(liveData: MutableLiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun Activity.startEnterTransitionAfterLoadingImage(imageAddress: String?, imageView: ImageView) {
    Glide.with(this)
        .load(imageAddress)
        .dontAnimate()
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }
        })
        .into(imageView)
}

fun Activity.loadImageUrl(image: ImageView, url: String){
    Glide.with(this)
        .load(url)
        .placeholder(ColorDrawable(Color.parseColor("#8FD8A0")))
        .into(image)
}

fun Fragment.showAlert(@StringRes message: Int, listener: () -> Unit): AlertDialog =
    AlertDialog.Builder(requireContext()).run {
        setTitle(getString(R.string.app_name))
        setMessage(message)

        setPositiveButton(android.R.string.ok) { dialog, _ ->
            listener()
            dialog.dismiss()
        }
        show()
    }

