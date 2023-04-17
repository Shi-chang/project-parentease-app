package edu.northeastern.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.northeastern.myapplication.entity.User;
import edu.northeastern.myapplication.nanny.NannyshareMain;

public class BottomNavClickListener implements View.OnClickListener {
    private Context context;
    private User user;

    public BottomNavClickListener(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);

        switch (v.getId()) {
            case R.id.iv_home:
            case R.id.tv_home:
                intent = new Intent(context, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                break;

            case R.id.iv_nanny:
            case R.id.tv_nanny:
                intent = new Intent(context, NannyshareMain.class);
                break;

            case R.id.iv_tips:
            case R.id.tv_tips:
                intent = new Intent(context, PostActivity.class);
                break;

            case R.id.iv_account:
            case R.id.tv_account:
                intent = new Intent(context, MyInfoActivity.class);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }

        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}

