package com.cotton;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ShareActivity extends Activity {

    LinearLayout instagramLayout;
    LinearLayout facebookLayout;
    Button closeButton;
    Uri contentUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_main);
        contentUri = Uri.parse(getIntent().getStringExtra("Uri"));
        instagramLayout = (LinearLayout)findViewById(R.id.instagram_layout);
        instagramLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOnInstagram();
            }
        });
        facebookLayout = (LinearLayout)findViewById(R.id.facebook_layout);
        facebookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOnFacebook();
            }
        });
        closeButton = (Button)findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void shareOnInstagram(){
        String type = "image/*";

        Intent share = new Intent(Intent.ACTION_SEND);
        // Set the MIME type
        share.setType(type);
        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, contentUri);
        share.setPackage("com.instagram.android");
        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }

    public void shareOnFacebook(){
        String type = "image/*";
        Intent share = new Intent(Intent.ACTION_SEND);
        // Set the MIME type
        share.setType(type);
        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, contentUri);
        share.setPackage("com.facebook.katana");
        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }
}
