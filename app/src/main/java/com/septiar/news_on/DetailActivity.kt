package com.septiar.news_on

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        val shareButton: Button = findViewById(R.id.action_share)
        shareButton.setOnClickListener {
            shareContent()
        }

        val imgDetail: ImageView = findViewById(R.id.img_detail_news)
        val tvDetailTitle: TextView = findViewById(R.id.tv_detail_title)
        val tvOverView: TextView = findViewById(R.id.tv_detail_overview)
        val tvDetailDate: TextView = findViewById(R.id.tv_detail_date)
        val tvDetailDescription: TextView = findViewById(R.id.tv_detail_description)
        val tvDetailDescription2: TextView = findViewById(R.id.tv_detail_description_2)
        val relatedNewsList: LinearLayout = findViewById(R.id.related_news_list)

        val news = intent.getParcelableExtra<News>("EXTRA_NEWS")
        if (news != null) {
            Glide.with(this)
                .load(news.image)
                .into(imgDetail)

            tvDetailTitle.text = news.title
            tvOverView.text = news.overview
            tvDetailDate.text = news.date
            tvDetailDescription.text = news.description
            tvDetailDescription2.text = news.description2
        }

        val relatedNews = getRelatedNews(tvDetailTitle.text, this)

        relatedNews.forEach { newsRelate ->
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_related_news, relatedNewsList, false)
            val imgRelatedNews: ImageView = itemView.findViewById(R.id.img_related_news)
            val tvRelatedTitle: TextView = itemView.findViewById(R.id.tv_related_title)
            val tvRelatedDate: TextView = itemView.findViewById(R.id.tv_related_date)

            Glide.with(this)
                .load(newsRelate.image)
                .into(imgRelatedNews)

            tvRelatedTitle.text = newsRelate.title
            tvRelatedDate.text = newsRelate.date

            itemView.setOnClickListener {
                openDetailActivity(newsRelate)
            }

            relatedNewsList.addView(itemView)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareContent() {
        val news = intent.getParcelableExtra<News>("EXTRA_NEWS")
        if (news != null) {
            val textToShare = "Check out this amazing article!"
            val linkToShare = news.link

            val shareText = "$textToShare $linkToShare"

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }

            startActivity(Intent.createChooser(sendIntent, null))
        }
    }

    private fun getRelatedNews(currentTitle: CharSequence, context: Context): List<News> {
        val titles = context.resources.getStringArray(R.array.news_title)
        val overview = context.resources.getStringArray(R.array.news_overview)
        val descriptions = context.resources.getStringArray(R.array.news_description)
        val descriptions2 = context.resources.getStringArray(R.array.news_description_2)
        val dates = context.resources.getStringArray(R.array.news_date)
        val links = context.resources.getStringArray(R.array.news_link)
        val images = context.resources.getStringArray(R.array.news_image)

        val newsList = mutableListOf<News>()
        if (titles.size == descriptions.size &&
            descriptions.size == dates.size &&
            dates.size == links.size &&
            links.size == images.size
        ) {
            for (i in titles.indices) {
                if (titles[i] != currentTitle) {
                    newsList.add(
                        News(
                            title = titles[i],
                            overview = overview[i],
                            description = descriptions[i],
                            description2 = descriptions2[i],
                            image = images[i],
                            date = dates[i],
                            link = links[i]
                        )
                    )
                }
            }
        }
        return newsList
    }

    private fun openDetailActivity(news: News) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("EXTRA_NEWS", news)
        }
        startActivity(intent)
    }
}
