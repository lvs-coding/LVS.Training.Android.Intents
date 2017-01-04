package com.example.android.intents;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        share();

    }

    private void showMap() {
        //Although the Android platform guarantees that certain intents will resolve to one of the
        //built-in apps (such as the Phone, Email, or Calendar app), you should always include a
        //verification step before invoking an intent.
        //
        //You should perform this check when your activity first starts in case you need to disable
        //the feature that uses the intent before the user attempts to use it. If you know of a
        //specific app that can handle the intent, you can also provide a link for the user to
        //download the app (see how to link to your product on Google Play).
        //
        //To verify there is an activity available that can respond to the intent,
        // call queryIntentActivities() to get a list of activities capable of handling your Intent.
        // If the returned List is not empty, you can safely use the intent.

        // Build the intent
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

        // Verify it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(mapIntent);
        }
    }

    //Notice that when you start an activity by passing your Intent to startActivity() and there is
    // more than one app that responds to the intent, the user can select which app to use by
    // default (by selecting a checkbox at the bottom of the dialog; see figure 1). This is nice
    // when performing an action for which the user generally wants to use the same app every time,
    // such as when opening a web page (users likely use just one web browser) or taking a photo
    // (users likely prefer one camera).
    //
    //However, if the action to be performed could be handled by multiple apps and the user might
    // prefer a different app each time—such as a "share" action, for which users might have several
    // apps through which they might share an item—you should explicitly show a chooser dialog as
    // shown in figure 2. The chooser dialog forces the user to select which app to use for the
    // action every time (the user cannot select a default app for the action). To show the chooser,
    // create an Intent using createChooser() and pass it to startActivity()
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        // Always use string resources for UI text.
        // This says something like "Share this photo with"
        String title = "Share with";
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(intent, title);


        // Verify the intent will resolve to at least one activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
       }
    }
}
