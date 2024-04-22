package com.example.newanimals.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.newanimals.R;
import com.example.newanimals.utils.SPHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class PersonFragment extends BaseFragment {
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.name_user) TextView name;
    @BindView(R.id.user_phone) TextView phone;
    @BindView(R.id.image) ImageView image;

    public static PersonFragment newInstance() {
        return new PersonFragment();
    }

    @Override
    protected int layoutId() {
        return R.layout.person_fragment_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        email.setText(SPHelper.getLogin());
        name.setText(SPHelper.getName() + " " + SPHelper.getSurname());
        phone.setText(SPHelper.getPhone());
        Picasso.get().load(SPHelper.getUrlPhotoUser()).into(image);
    }
}
