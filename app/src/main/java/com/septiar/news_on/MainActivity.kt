package com.septiar.news_on

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvNews: RecyclerView
    private val list = ArrayList<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvNews = findViewById(R.id.rv_news)
        rvNews.setHasFixedSize(true)

        list.addAll(getListNews())
        showRecyclerList()
    }


    private fun getListNews(): ArrayList<News> {
        val dataNewsTitle = resources.getStringArray(R.array.news_title)
        val dataOverview = resources.getStringArray(R.array.news_overview)
        val dataDescription = resources.getStringArray(R.array.news_description)
        val dataDescription2 = resources.getStringArray(R.array.news_description_2)
        val dataImage = resources.getStringArray(R.array.news_image)
        val dataDate = resources.getStringArray(R.array.news_date)
        val dataLink = resources.getStringArray(R.array.news_link)
        val listNews = ArrayList<News>()
        for (i in dataNewsTitle.indices) {
            val news = News(
                dataNewsTitle[i],
                dataOverview[i],
                dataDescription[i],
                dataDescription2[i],
                dataImage[i],
                dataDate[i],
                dataLink[i],
            )
            listNews.add(news)
        }
        return listNews
    }

    private fun showRecyclerList() {
        rvNews.layoutManager = LinearLayoutManager(this)
        val listNewsAdapter = ListNewsAdapter(list)
        rvNews.adapter = listNewsAdapter

        listNewsAdapter.setOnItemClickCallback(object : ListNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: News) {
                showSelectedNews(data)
            }
        })
    }

    private fun showSelectedNews(news: News) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXTRA_NEWS", news)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_page) {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}