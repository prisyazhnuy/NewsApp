package com.prisyazhnuy.newsapp.favourite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.prisyazhnuy.newsapp.R;
import com.prisyazhnuy.newsapp.data.local.db.NewsDAORealm;
import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.news_list.NewsInteractionListener;
import com.prisyazhnuy.newsapp.news_list.NewsListContract;
import com.prisyazhnuy.newsapp.news_list.NewsRecycleViewAdapter;

import java.util.List;

public class FavouriteListFragment extends MvpFragment<NewsListContract.NewsListView,
        NewsListContract.NewsListPresenter> implements NewsListContract.NewsListView, NewsInteractionListener {

    private NewsInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private NewsRecycleViewAdapter mNewsAdapter;

    public FavouriteListFragment() {
    }

    @NonNull
    @Override
    public NewsListContract.NewsListPresenter createPresenter() {
        return new FavouriteListPresenterImpl(NewsDAORealm.getInstance(getContext().getCacheDir().getPath()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;
        }
        Context context = mRecyclerView.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mNewsAdapter = new NewsRecycleViewAdapter(null, this);
        mRecyclerView.setAdapter(mNewsAdapter);
        getPresenter().loadBreakNews();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewsInteractionListener) {
            mListener = (NewsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void delete(long id) {
        mNewsAdapter.removeItem(id);
    }

    @Override
    public void showNews(List<Article> articles) {
        mNewsAdapter.addAll(articles);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyList() {
        Toast.makeText(getContext(), getString(R.string.no_news), Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearList() {
        mNewsAdapter.clear();
    }

    @Override
    public void onItemClicked(Article item) {
        mListener.onItemClicked(item);
    }

    @Override
    public void onItemChecked(Article item) {
        getPresenter().delete(item.getId());
    }

    @Override
    public void onShareClicked(Article item) {

    }
}
