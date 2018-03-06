package com.prisyazhnuy.newsapp.news_list;

import com.prisyazhnuy.newsapp.data.pojo.Article;

/**
 * max.pr on 05.03.2018.
 */

public interface NewsInteractionListener {
    void onItemClicked(Article item);

    void onItemChecked(Article item, boolean isChecked);

    void onShareClicked(Article item);
}
