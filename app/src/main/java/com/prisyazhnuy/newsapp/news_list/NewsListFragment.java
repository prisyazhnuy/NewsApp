package com.prisyazhnuy.newsapp.news_list;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.prisyazhnuy.newsapp.R;
import com.prisyazhnuy.newsapp.data.NewsRepository;
import com.prisyazhnuy.newsapp.data.pojo.Article;
import com.prisyazhnuy.newsapp.data.remote.RestClient;

import java.util.List;

public class NewsListFragment extends MvpFragment<NewsListContract.NewsListView, NewsListContract.NewsListPresenter>
        implements NewsListContract.NewsListView {

    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "NewsListFragment";
    private RecyclerView mRecyclerView;


    private String mParam1;

    private OnClickNewsItemListener mListener;

    public NewsListFragment() {
        // Required empty public constructor
    }

    @Override
    public NewsListContract.NewsListPresenter createPresenter() {
        return new NewsListPresenterImpl(NewsRepository.getInstance(RestClient.create()));
    }

    public static NewsListFragment newInstance(String param1) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
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
        getPresenter().onLoad();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClickNewsItemListener) {
            mListener = (OnClickNewsItemListener) context;
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
    public void showNews(List<Article> articles) {
        Context context = mRecyclerView.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new NewsRecycleViewAdapter(articles, mListener));
    }

    @Override
    public void showError() {
        Log.d(TAG, "Network error");
    }

    public interface OnClickNewsItemListener {
        void onItemClicked(Article item);
    }
}
