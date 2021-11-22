package mx.com.testjobflink.ui.detail.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_detail_movie.*
import mx.com.testjobflink.R
import mx.com.testjobflink.data.models.Movie
import mx.com.testjobflink.utils.Constants
import mx.com.testjobflink.utils.extensions.loadImageUrl
import mx.com.testjobflink.utils.extensions.posterFullUrl
import mx.com.testjobflink.utils.extensions.startEnterTransitionAfterLoadingImage

class DetailMovieDialog(activity: Activity, movie: Movie) : Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
    var mMovie: Movie = movie
    var mActivity:Activity = activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_detail_movie)
        setUpMovieData()
        ivCloseFullScreen.setOnClickListener { dismiss() }
    }

    private fun setUpMovieData() {
        mMovie.let { item ->
            mActivity.loadImageUrl(backgroundPoster, item.posterPath.posterFullUrl)
            ivMoviePoster.apply {
                transitionName = item.posterPath
                mActivity.startEnterTransitionAfterLoadingImage(
                    item.posterPath.posterFullUrl,
                    this
                )
            }
            tvMovieTitle.apply {
                transitionName = Constants.TITLE
                text = item.title
            }
            tvTotalPointsItem.apply {
                transitionName = Constants.RANKING
                text = mActivity.getString(
                    R.string.text_placeholder_votes,
                    item.voteAverage.toString(), item.voteCount.toString()
                )
            }
            tvMovieOverview.text = item.overview
        }
    }

}