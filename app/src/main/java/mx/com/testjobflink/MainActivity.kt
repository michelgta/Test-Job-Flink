package mx.com.testjobflink

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.main_activity.*
import mx.com.testjobflink.data.models.Movie
import mx.com.testjobflink.ui.detail.DetailActivity
import mx.com.testjobflink.ui.feed.FeedTabsAdapter
import mx.com.testjobflink.ui.listener.FragmentCommunication
import mx.com.testjobflink.utils.Constants
import mx.com.testjobflink.utils.Constants.MOVIE
import mx.com.testjobflink.utils.Constants.RANKING
import mx.com.testjobflink.utils.Constants.TITLE

class MainActivity : AppCompatActivity(),FragmentCommunication {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setUpNavigation()

      viewPagerFeeds.adapter = FeedTabsAdapter(this)

        TabLayoutMediator(tabLayoutFeeds, viewPagerFeeds) { tab, position ->
            if (position == Constants.TAB_1)
                tab.text = getString(R.string.text_popular_movies)
            else
                tab.text = getString(R.string.text_favorites_movies)


        }.attach()

    }



    private fun setUpNavigation() {
        (supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment)?.navController?.navigate(R.id.favoriteFragment)
    }

    override fun selectMovie(poster: View, title: View, ranking: View, movie: Movie) {
       val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(MOVIE, movie)
        }
        val image = movie.posterPath
        val pairPoster : Pair<View, String> = Pair(poster, image)
        val pairTitle : Pair<View, String> = Pair(title, TITLE)
        val pairRanking : Pair<View, String> = Pair(ranking, RANKING)
        val options = ActivityOptions.makeSceneTransitionAnimation(this, pairPoster, pairTitle, pairRanking)

        startActivity(intent)

    }


}

