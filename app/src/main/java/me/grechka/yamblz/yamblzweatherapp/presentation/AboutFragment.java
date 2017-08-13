package me.grechka.yamblz.yamblzweatherapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.grechka.yamblz.yamblzweatherapp.R;
import me.grechka.yamblz.yamblzweatherapp.presentation.main.MainActivity;

public class AboutFragment extends Fragment {

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) getActivity();

        mainActivity
                .getSupportActionBar()
                .setTitle(R.string.main_activity_navigation_about);

        mainActivity
                .selectBackButtonNavigation();
    }

    @OnClick(R.id.fragment_about_send_email)
    public void onSendEmail(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(getString(R.string.fragment_about_author_mail_type));
        intent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.fragment_about_author_mail_address));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.fragment_about_author_mail_theme));
        startActivity(Intent.createChooser(intent, getString(R.string.fragment_about_author_mail_provider_title)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
