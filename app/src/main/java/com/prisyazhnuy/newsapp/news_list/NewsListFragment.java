package com.prisyazhnuy.newsapp.news_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.prisyazhnuy.newsapp.R;
import com.prisyazhnuy.newsapp.data.NewsRepository;
import com.prisyazhnuy.newsapp.data.local.preferences.DataRepository;
import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.data.remote.RestClient;

import java.util.List;

public class NewsListFragment extends MvpFragment<NewsListContract.NewsListView, NewsListContract.NewsListPresenter<NewsListContract.NewsListView>>
        implements NewsListContract.NewsListView {

    private static final String TAG = "NewsListFragment";
    private RecyclerView mRecyclerView;
    private NewsRecycleViewAdapter mNewsAdapter;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
//    private boolean loading;


    private OnClickNewsItemListener mListener;

    public NewsListFragment() {
    }

    @NonNull
    @Override
    public NewsListContract.NewsListPresenter createPresenter() {
        return new NewsListPresenterImpl(NewsRepository.getInstance(RestClient.create()),
                DataRepository.getInstance(getContext()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        if (view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = mRecyclerView.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = recyclerView.getLayoutManager().getItemCount();
                lastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // End has been reached
                    // Do something
//                    if (onLoadMoreListener != null) {
//                        onLoadMoreListener.onLoadMore();
//                    }
                    getPresenter().loadNextNews();
//                    loading = true;
                }
            }
        });
        mNewsAdapter = new NewsRecycleViewAdapter(null, mListener);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClickNewsItemListener) {
            mListener = (OnClickNewsItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSortChangedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showNews(List<Article> articles) {
        mNewsAdapter.addAll(articles);
//        loading = false;
    }

    @Override
    public void showError(String message) {
        Log.d(TAG, "Network error");
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

    public interface OnClickNewsItemListener {
        void onItemClicked(Article item);
    }
}
