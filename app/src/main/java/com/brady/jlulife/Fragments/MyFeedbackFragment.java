package com.brady.jlulife.Fragments;

import android.os.Bundle;
import android.view.View;

import com.umeng.fb.fragment.FeedbackFragment;

/**
 * Created by brady on 15-12-2.
 */
public class MyFeedbackFragment extends FeedbackFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(com.brady.jlulife.R.id.umeng_fb_contact_title).setVisibility(View.GONE);
//        getResources().getString(R.string.umeng_fb_please_select_picture)
    }
    public static FeedbackFragment newInstance(String var0) {
        FeedbackFragment var1 = new MyFeedbackFragment();
        Bundle var2 = new Bundle();
        var2.putString("conversation_id", var0);
        var1.setArguments(var2);
        return var1;
    }
}
