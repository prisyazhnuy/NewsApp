package com.prisyazhnuy.newsapp.data.pojo;

import java.util.List;

/**
 * max.pr on 02.03.2018.
 */

public class NewsResponse extends BaseResponse {

    private int totalResults;
    private List<Article> articles;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
