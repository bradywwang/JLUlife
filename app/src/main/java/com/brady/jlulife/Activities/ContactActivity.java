package com.brady.jlulife.Activities;

import com.brady.jlulife.Fragments.MyFeedbackFragment;
import com.brady.jlulife.R;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;

/**
 * Created by brady on 15-12-2.
 */
public class ContactActivity extends BaseActivity {
    @Override
    public void initFragment() {
//        String conversation_id = getIntent().getStringExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID);
        String id = new FeedbackAgent(this).getDefaultConversation().getId();
        FeedbackFragment mFeedbackFragment = MyFeedbackFragment.newInstance(id);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container,mFeedbackFragment)
                .commit();
/*        FeedbackAgent agent = new FeedbackAgent(this);
        agent.startFeedbackActivity();*/
    }
}
