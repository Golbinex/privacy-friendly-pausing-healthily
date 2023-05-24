package org.secuso.privacyfriendlypausinghealthily.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.secuso.privacyfriendlypausinghealthily.BuildConfig;
import org.secuso.privacyfriendlypausinghealthily.R;

/**
 * About Page :)
 *
 * @author Christopher Beckmann
 * @version 2.0
 */
public class AboutActivity extends AppCompatActivity {

    Handler mHandler;
    View mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mHandler = new Handler();

        mainContent = findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.setAlpha(0);
            mainContent.animate().alpha(1).setDuration(250);
        }

        overridePendingTransition(0, 0);
        setupActionBar();

        TextView t1 = (TextView) findViewById(R.id.githubURL);
        t1.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t2 = (TextView) findViewById(R.id.secusoWebsite);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView authors = (TextView) findViewById(R.id.textFieldAuthorNames);
        authors.setText(getString(R.string.about_author_contributors, getString(R.string.about_author_names)));

        ((TextView)findViewById(R.id.textFieldVersionName)).setText(BuildConfig.VERSION_NAME);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setTitle(R.string.about);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                exitActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exitActivity();
    }

    private void exitActivity() {
        if (mainContent != null) {
            mainContent.setAlpha(1);
            mainContent.animate().alpha(0).setDuration(125);
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        }, 70);
    }
}
