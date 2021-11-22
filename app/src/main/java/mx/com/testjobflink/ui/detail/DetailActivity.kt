package mx.com.testjobflink.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import kotlinx.android.synthetic.main.activity_detail.*
import mx.com.testjobflink.R
import mx.com.testjobflink.data.models.Movie
import mx.com.testjobflink.utils.Constants.MOVIE
import mx.com.testjobflink.utils.Constants.RANKING
import mx.com.testjobflink.utils.Constants.TITLE
import mx.com.testjobflink.utils.extensions.loadImageUrl
import mx.com.testjobflink.utils.extensions.posterFullUrl
import mx.com.testjobflink.utils.extensions.startEnterTransitionAfterLoadingImage

class DetailActivity : AppCompatActivity() {

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        intent.extras?.let {
            movie = it.getParcelable(MOVIE)
        }
        setUpMovieData()

    }

    private fun setUpMovieData() {
        movie?.let { item ->
            loadImageUrl(backgroundPoster, item.posterPath.posterFullUrl)

            ivMoviePoster.apply {
                transitionName = item.posterPath
                startEnterTransitionAfterLoadingImage(item.posterPath.posterFullUrl, this)
            }
            tvMovieTitle.apply {
                transitionName = TITLE
                text = item.title
            }

            tvTotalPointsItem.apply {
                transitionName = RANKING
                text = getString(
                    R.string.text_placeholder_votes,
                    item.voteAverage.toString(), item.voteCount.toString()
                )
            }

            tvMovieOverview.text = item.overview
        }
    }


}