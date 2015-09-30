package com.elegion.githubclient.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.elegion.githubclient.R;
import com.elegion.githubclient.adapter.RepositoriesAdapter;
import com.elegion.githubclient.adapter.decoration.RepositoryDecoration;
import com.elegion.githubclient.api.ApiClient;
import com.elegion.githubclient.model.Repository;
import com.elegion.githubclient.utils.LoginAlertDialog;
import com.elegion.githubclient.utils.OnClickDialogListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Mochalov.
 */
public class MyRepositoriesActivity extends BaseActivity implements OnClickDialogListener{

    private RecyclerView mRepositoryList;
    private static final String TAG = MyRepositoriesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_repositories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRepositoryList = (RecyclerView) findViewById(R.id.repositories_list);
        mRepositoryList.setLayoutManager(new LinearLayoutManager(this));
        mRepositoryList.setAdapter(new RepositoriesAdapter());
        mRepositoryList.addItemDecoration(new RepositoryDecoration());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        new GetRepositoriesTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnDialogClick(int i) {
        logout();
    }

    private class GetRepositoriesTask extends AsyncTask<Void, Void, List<Repository>> {

        @Override
        protected List<Repository> doInBackground(Void... params) {
            List<Repository> repositories = new ArrayList<>();
            try {
                JSONObject responseObject = new ApiClient()
                        .addAuthHeader()
                        .setUrl(ApiClient.GET_CURRENT_USER_REPOS_URL)
                        .asArray()
                        .executeGet();

                if (responseObject.optInt(ApiClient.STATUS_CODE) != ApiClient.STATUS_CODE_OK) {
                    DialogFragment dialog = new LoginAlertDialog(MyRepositoriesActivity.this);
                    dialog.show(getSupportFragmentManager(), "LoginAlertFragment");
                    return repositories;
                }

                JSONArray array = (JSONArray)responseObject.get(ApiClient.LIST_DATA_KEY);

                for (int i = 0; i < array.length(); i++) {
                    repositories.add(
                            new Repository(((JSONObject) array.get(i)).getString("name"))
                    );
                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return repositories;
        }

        @Override
        protected void onPostExecute(List<Repository> repositories) {
            ((RepositoriesAdapter)mRepositoryList.getAdapter()).addAll(repositories);
        }
    }
}
