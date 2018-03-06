package com.prisyazhnuy.newsapp.filter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.prisyazhnuy.newsapp.data.NewsSourceModel;
import com.prisyazhnuy.newsapp.data.local.preferences.PreferencesSource;
import com.prisyazhnuy.newsapp.data.pojo.Source;
import com.prisyazhnuy.newsapp.data.pojo.SourcesResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * max.pr on 04.03.2018.
 */

public class FilterPresenterImpl extends MvpBasePresenter<FilterContract.FilterView>
        implements FilterContract.FilterPresenter {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private PreferencesSource mPreferencesSource;
    private NewsSourceModel mNewsSourceModel;

    public FilterPresenterImpl(PreferencesSource source, NewsSourceModel model) {
        this.mPreferencesSource = source;
        this.mNewsSourceModel = model;
    }

    @Override
    public void loadSources() {
        Disposable disposable = mNewsSourceModel.getSources(null, null, "us")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SourcesResponse>() {
                    @Override
                    public void accept(final SourcesResponse sourcesResponse) throws Exception {
                        ifViewAttached(new ViewAction<FilterContract.FilterView>() {
                            @Override
                            public void run(@NonNull FilterContract.FilterView view) {
                                List<Source> sources = sourcesResponse.getSources();
                                if (sources == null || sources.isEmpty()) {
                                    view.showNoSources();
                                } else {
                                    view.showSources(sources);
                                }
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(final Throwable throwable) throws Exception {
                        ifViewAttached(new ViewAction<FilterContract.FilterView>() {
                            @Override
                            public void run(@NonNull FilterContract.FilterView view) {
                                view.showError(throwable.getMessage());
                            }
                        });
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void setDateFrom(int year, int month, int day) {
        final String dateFrom = getDate(year, month, day);
        mPreferencesSource.setDateFrom(dateFrom);
        ifViewAttached(new ViewAction<FilterContract.FilterView>() {
            @Override
            public void run(@NonNull FilterContract.FilterView view) {
                view.setDateFrom(dateFrom);
            }
        });
    }

    @Override
    public void setDateTo(int year, int month, int day) {
        final String dateTo = getDate(year, month, day);
        mPreferencesSource.setDateTo(dateTo);
        ifViewAttached(new ViewAction<FilterContract.FilterView>() {
            @Override
            public void run(@NonNull FilterContract.FilterView view) {
                view.setDateTo(dateTo);
            }
        });
    }

    private String getDate(int year, int month, int day) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US); // Quoted "Z" to indicate UTC, no timezone offset
        dateFormat.setTimeZone(timeZone);
        Calendar calendar = new GregorianCalendar(year, month, day);
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public void loadCacheFilter() {
        String checkedSources = mPreferencesSource.getCheckedSources();
        final List<String> sources = parseCheckedSources(checkedSources);
        final String dateFrom = mPreferencesSource.getDateFrom();
        final String dateTo = mPreferencesSource.getDateTo();
        ifViewAttached(new ViewAction<FilterContract.FilterView>() {
            @Override
            public void run(@NonNull FilterContract.FilterView view) {
                view.setDateFrom(dateFrom);
                view.setDateTo(dateTo);
                view.setCheckedSources(sources);
            }
        });
    }

    @Override
    public void setSource(String id) {
        String checkedSources = mPreferencesSource.getCheckedSources();
        if (checkedSources != null) {
            List<String> sources = parseCheckedSources(checkedSources);
            boolean isContained = sources.remove(id);
            if (!isContained) {
                sources.add(id);
            }
            StringBuilder sourcesBuilder = new StringBuilder();
            for (String source : sources) {
                sourcesBuilder.append(source).append(",");
            }
            if (sourcesBuilder.length() > 0) {
                sourcesBuilder.deleteCharAt(sourcesBuilder.length() - 1);
            }
            mPreferencesSource.setCheckedSources(sourcesBuilder.toString());
        } else {
            mPreferencesSource.setCheckedSources(id);
        }
    }

    private List<String> parseCheckedSources(String checkedSources) {
        if (TextUtils.isEmpty(checkedSources)) {
            return new ArrayList<>();
        } else {
            String[] sources = checkedSources.split(",");
            List<String> result = new ArrayList<>(sources.length);
            result.addAll(Arrays.asList(sources));
            return result;
        }
    }

    @Override
    public void detachView() {
        mCompositeDisposable.clear();
        super.detachView();
    }
}
