package com.prisyazhnuy.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.prisyazhnuy.newsapp.favourite.FavouriteListActivity;
import com.prisyazhnuy.newsapp.filter.FilterFragment;
import com.prisyazhnuy.newsapp.news_list.NewsListContract;
import com.prisyazhnuy.newsapp.news_list.NewsListFragment;
import com.prisyazhnuy.newsapp.sort.SortFragment;

public class MainActivity extends AppCompatActivity
        implements SortFragment.OnSortChangedListener,
        FilterFragment.OnFilterChangedListener {

    private static final int REQUEST_INVITE = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favourites: {
                startActivity(new Intent(this, FavouriteListActivity.class));
                break;
            }
            case R.id.invite: {
                inviteToApp();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void inviteToApp() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, getString(R.string.successful_invitation), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getString(R.string.failed_invitation), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onSortChanged() {
        reloadNews();
    }

    @Override
    public void onFilterChanged() {
        reloadNews();
    }

    private void reloadNews() {
        NewsListFragment newsListFrag = (NewsListFragment)
                getSupportFragmentManager().findFragmentById(R.id.newsListFragment);
        if (newsListFrag != null) {
            NewsListContract.NewsListPresenter presenter = newsListFrag.getPresenter();
            if (presenter != null) {
                presenter.loadBreakNews();
            }
        }
    }
}
