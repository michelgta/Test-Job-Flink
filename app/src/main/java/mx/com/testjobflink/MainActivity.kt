package mx.com.testjobflink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.main_activity.*
import mx.com.testjobflink.R
import mx.com.testjobflink.ui.feed.FeedTabsAdapter
import mx.com.testjobflink.ui.main.MainFragment
import mx.com.testjobflink.utils.Constants

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }


        viewPagerFeeds.adapter = FeedTabsAdapter(this)

        TabLayoutMediator(tabLayoutFeeds, viewPagerFeeds) { tab, position ->
            if(position==Constants.TAB_1)
                tab.text = getString(R.string.text_popular_movies)
             else
                tab.text = getString(R.string.text_favorites_movies)


        }.attach()


    }
}