package com.prisyazhnuy.newsapp.filter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.prisyazhnuy.newsapp.R;
import com.prisyazhnuy.newsapp.data.NewsSourceRepository;
import com.prisyazhnuy.newsapp.data.local.preferences.DataRepository;
import com.prisyazhnuy.newsapp.data.pojo.Source;
import com.prisyazhnuy.newsapp.data.remote.RestClient;

import java.util.Calendar;
import java.util.List;

public class FilterFragment extends MvpFragment<FilterContract.FilterView, FilterContract.FilterPresenter>
        implements FilterContract.FilterView {

    private ListView mLvSources;
    private TextView mTvDateFrom;
    private TextView mTvDateTo;

    private OnFilterChangedListener mListener;
    private List<String> mCheckedSources;

    public FilterFragment() {
    }

    @NonNull
    @Override
    public FilterContract.FilterPresenter createPresenter() {
        return new FilterPresenterImpl(DataRepository.getInstance(getContext()),
                NewsSourceRepository.getInstance(RestClient.create()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLvSources = view.findViewById(R.id.lvSources);
        mTvDateFrom = view.findViewById(R.id.tvDateFrom);
        mTvDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1;
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        getPresenter().setDateFrom(year, month, day);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        mTvDateTo = view.findViewById(R.id.tvDateTo);
        mTvDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1;
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        getPresenter().setDateTo(year, month, day);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        getPresenter().loadCacheFilter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFilterChangedListener) {
            mListener = (OnFilterChangedListener) context;
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
    public void showSources(List<Source> sources) {
        ArrayAdapter<Source> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_multiple_choice, sources);
        mLvSources.setAdapter(adapter);
        mLvSources.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Source source = (Source) adapterView.getItemAtPosition(i);
                Toast.makeText(getContext(), source.getId() + " " + source.getName(), Toast.LENGTH_SHORT).show();
                getPresenter().setSource(source.getId());
                if (mListener != null) {
                    mListener.onFilterChanged();
                }
            }
        });
        if (mCheckedSources != null && !mCheckedSources.isEmpty()) {
            for (int i = 0; i < sources.size(); i++) {
                if (mCheckedSources.contains(sources.get(i).getId())) {
                    mLvSources.setItemChecked(i, true);
                }
            }
        }
        if (mListener != null) {
            mListener.onFilterChanged();
        }
    }

    @Override
    public void setDateFrom(String from) {
        mTvDateFrom.setText(from);
        if (mListener != null) {
            mListener.onFilterChanged();
        }
    }

    @Override
    public void setDateTo(String to) {
        mTvDateTo.setText(to);
        if (mListener != null) {
            mListener.onFilterChanged();
        }
    }

    @Override
    public void showNoSources() {
        Toast.makeText(getContext(), getString(R.string.no_sources), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setCheckedSources(List<String> sources) {
        mCheckedSources = sources;
        getPresenter().loadSources();
    }

    public interface OnFilterChangedListener {
        void onFilterChanged();
    }
}
