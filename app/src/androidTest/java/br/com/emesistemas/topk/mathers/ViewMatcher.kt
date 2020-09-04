package br.com.emesistemas.topk.mathers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import br.com.emesistemas.topk.R
import org.hamcrest.Description
import org.hamcrest.Matcher

object ViewMatcher {

    fun matchesInPosition(
        position: Int,
        verifyAuthorName: String,
        verifyRepoName: String,
        verifyForksCount: Int,
        verifyStarsCount: Int
    ): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            private val displayed = isDisplayed()

            override fun describeTo(description: Description?) {
                description!!.appendText("View com o nome de autor ")
                    .appendValue(verifyAuthorName)
                    .appendText(",repositório ")
                    .appendValue(verifyRepoName)
                    .appendText(", quantidade de estrelas ")
                    .appendValue(verifyStarsCount)
                    .appendText(", quantidade de forks ")
                    .appendValue(verifyForksCount)
                    .appendText(", na posição ")
                    .appendValue(position).appendText(" ")
                description.appendDescriptionOf(displayed)
            }

            override fun matchesSafely(item: RecyclerView?): Boolean {
                val viewHolder = item?.findViewHolderForAdapterPosition(position)

                if (viewHolder == null) {
                    IndexOutOfBoundsException("View do ViewHolder na posição $position não foi encontrada")
                }

                val itemView = viewHolder?.itemView

                itemView?.let {
                    return matchesAutorName(it)
                            && matchesRepoName(it)
                            && matchesTvStars(it)
                            && matchesTvForks(it)
                            && matchesIvAvatar(it)
                            && matchesIvStar(it)
                            && matchesIvFork(it)
                }
                return false
            }

            private fun matchesAutorName(itemView: View): Boolean {
                val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
                return tvAuthor.text.toString() == verifyAuthorName
                        && displayed.matches(tvAuthor)
            }

            private fun matchesRepoName(itemView: View): Boolean {
                val tvRepoName: TextView = itemView.findViewById(R.id.tvRepoName)
                return tvRepoName.text.toString() == verifyRepoName
                        && displayed.matches(tvRepoName)
            }

            private fun matchesTvStars(itemView: View): Boolean {
                val tvStarsCount: TextView = itemView.findViewById(R.id.tvStarsCount)
                return tvStarsCount.text.toString().toInt() == verifyStarsCount
                        && displayed.matches(tvStarsCount)
            }

            private fun matchesTvForks(itemView: View): Boolean {
                val tvForksCount: TextView = itemView.findViewById(R.id.tvForksCount)
                return tvForksCount.text.toString().toInt() == verifyForksCount
                        && displayed.matches(tvForksCount)
            }

            private fun matchesIvAvatar(itemView: View): Boolean {
                val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
                return displayed.matches(ivAvatar)
            }

            private fun matchesIvStar(itemView: View): Boolean {
                val ivStar: ImageView = itemView.findViewById(R.id.ivStar)
                return displayed.matches(ivStar)
            }

            private fun matchesIvFork(itemView: View): Boolean {
                val ivFork: ImageView = itemView.findViewById(R.id.ivFork)
                return displayed.matches(ivFork)
            }
        }
    }
}
