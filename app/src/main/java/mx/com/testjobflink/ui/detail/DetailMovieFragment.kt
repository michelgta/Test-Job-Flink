package mx.com.testjobflink.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.com.testjobflink.R
import mx.com.testjobflink.data.models.Movie

private const val POSTER_VIEW = "posterView"
private const val TITLE = "tittle"
private const val RANKING = "ranking"
private const val MOVIE = "movie"


class DetailMovieFragment : Fragment() {

    private var posterView: String? = null
    private var tittle: String? = null
    private var ranking: String? = null
    private var movie: Movie? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            posterView = it.getString(POSTER_VIEW)
            tittle = it.getString(TITLE)
            ranking = it.getString(RANKING)
            movie = it.getParcelable(MOVIE)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_movie, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(posterView: String, tittle: String, ranking:String, movie: Movie) =
            DetailMovieFragment().apply {
                arguments = Bundle().apply {
                    putString(POSTER_VIEW, posterView)
                    putString(TITLE, tittle)
                    putString(RANKING, ranking)
                    putParcelable(MOVIE,movie)
                }
            }
    }
}