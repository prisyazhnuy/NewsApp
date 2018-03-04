package com.prisyazhnuy.newsapp.sort;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.prisyazhnuy.newsapp.R;
import com.prisyazhnuy.newsapp.data.local.preferences.DataRepository;

public class SortFragment extends MvpFragment<SortContract.SortView, SortContract.SortPresenter>
        implements SortContract.SortView {

    private RadioGroup mGroupSort;
    private OnSortChangedListener mListener;

    public SortFragment() {
    }

    @NonNull
    @Override
    public SortContract.SortPresenter createPresenter() {
        return new SortPresenterImpl(DataRepository.getInstance(getContext()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sort, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGroupSort = view.findViewById(R.id.rgSort);
        mGroupSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.rBtnDate:
                        getPresenter().setPublishedSort();
                        break;
                    case R.id.rBtnPopularity:
                        getPresenter().setPopularitySort();
                        break;
                    default:
                        getPresenter().setPublishedSort();
                }
                if (mListener != null) {
                    mListener.onSortChanged();
                }
            }
        });
        getPresenter().onLoad();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSortChangedListener) {
            mListener = (OnSortChangedListener) context;
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
    public void setPublishedDateChecked() {
        mGroupSort.check(R.id.rBtnDate);
        if (mListener != null) {
            mListener.onSortChanged();
        }
    }

    @Override
    public void setPopularityChecked() {
        mGroupSort.check(R.id.rBtnPopularity);
        if (mListener != null) {
            mListener.onSortChanged();
        }
    }

    public interface OnSortChangedListener {
        void onSortChanged();
    }
}
