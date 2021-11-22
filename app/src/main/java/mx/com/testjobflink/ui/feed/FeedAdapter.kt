package mx.com.testjobflink.ui.feed

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import mx.com.testjobflink.ui.favorite.FavoriteFragment
import mx.com.testjobflink.ui.popular.PopularFragment
import mx.com.testjobflink.utils.Constants

class FeedTabsAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            Constants.TAB_1 -> PopularFragment()
            Constants.TAB_2 -> FavoriteFragment()
            else -> PopularFragment()
        }

    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


}