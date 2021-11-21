package mx.com.testjobflink

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.main_activity.*
import mx.com.testjobflink.data.models.Movie
import mx.com.testjobflink.ui.feed.FeedTabsAdapter
import mx.com.testjobflink.ui.listener.FragmentCommunication
import mx.com.testjobflink.utils.Constants

class MainActivity : AppCompatActivity(), FragmentCommunication {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        viewPagerFeeds.adapter = FeedTabsAdapter(this)

        TabLayoutMediator(tabLayoutFeeds, viewPagerFeeds) { tab, position ->
            if (position == Constants.TAB_1)
                tab.text = getString(R.string.text_popular_movies)
            else
                tab.text = getString(R.string.text_favorites_movies)
        }.attach()



    }

    override fun selectMovie(poster: View, title: View, ranking: View, movie: Movie) {
        TODO("Not yet implemented")
        
    }


}

