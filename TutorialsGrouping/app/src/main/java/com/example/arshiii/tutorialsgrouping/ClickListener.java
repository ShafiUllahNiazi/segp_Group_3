package com.example.arshiii.tutorialsgrouping;

import android.view.View;

/**
 * Created by Arshiii on 10/30/2017.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
