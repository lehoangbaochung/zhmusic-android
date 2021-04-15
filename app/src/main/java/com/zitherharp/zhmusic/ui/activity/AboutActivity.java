package com.zitherharp.zhmusic.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zitherharp.zhmusic.R;

public class AboutActivity extends AppCompatActivity {
    TextView tvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String aboutApp = String.format("%s\n%s\n%s", "Online Music Player App", "Version: v1.0", "Date: 2021-04");
        String aboutAuthors = String.format("%s\n%s", "Authors: LHBC & NTN", "Address: Ha Noi");
        String aboutContact = String.format("%s\n%s\n%s\n%s", "Contact us:",
                "Email: contact.lhbc@gmail.com", "Facebook: Nam Met", "Phone: 0586243970");

        tvAbout = findViewById(R.id.about_app);
        tvAbout.setText(aboutApp + "\n\n" + aboutAuthors + "\n\n" + aboutContact);
    }
}