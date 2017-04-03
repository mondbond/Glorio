package exp.glorio.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import exp.glorio.R;
import exp.glorio.common.BaseActivity;
import exp.glorio.common.BaseFragment;
import exp.glorio.di.DetailPublicComponent;
import exp.glorio.model.PublicStatistics;
import exp.glorio.presentation.DetailPublicPresenter;
import exp.glorio.util.VkUtil;
import exp.glorio.view.SexStatistic;
import exp.glorio.view.activity.DetailPublicActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPublicFragment extends BaseFragment implements DetailPublicView{

    @Inject
    DetailPublicPresenter presenter;

    private int publicId;

    private TextView detailPublicName;
    private TextView detailPublicDescription;
    private ImageView detailPublicImage;
    private TextView postResult;
    private TextView averageLikesResult;
    private TextView subPerLikesResult;
    private TextView averageAgeResult;
    private SexStatistic manPercent;
    private PublicStatistics publicStatistics;


    public DetailPublicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_detail_public, container, false);

        detailPublicName = (TextView) v.findViewById(R.id.detailPublicName);
        detailPublicDescription = (TextView) v.findViewById(R.id.detailPublicStatus);
        detailPublicImage = (ImageView) v.findViewById(R.id.detailPublicImage);
        postResult = (TextView) v.findViewById(R.id.postResult1);
        averageLikesResult = (TextView) v.findViewById(R.id.averageLikesResult);
        subPerLikesResult = (TextView) v.findViewById(R.id.subPerLikesResult);
        averageAgeResult = (TextView) v.findViewById(R.id.averageAgeResult);
        manPercent = (SexStatistic) v.findViewById(R.id.manPercent);

        Bundle arguments = getArguments();
        publicId = arguments.getInt(DetailPublicActivity.PUBLIC_NAME);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(DetailPublicComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);

//        getView().setVisibility(View.INVISIBLE);
//        showProgressAnimation(getView(), getContext());

        if(publicStatistics == null) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.showProgressAnimation();
            presenter.getAllPublicInfo(publicId);
        }else {
            setDetailPublicAnaliticResult(publicStatistics);
        }
    }

    @Override
    public void setDetailPublicAnaliticResult(PublicStatistics publicStatistics) {
        if (this.publicStatistics == null) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.hideProgressAnimation();
        }
        this.publicStatistics = publicStatistics;

//        getView().setVisibility(View.VISIBLE);
        detailPublicName.setText(publicStatistics.getPubName());
        detailPublicDescription.setText(publicStatistics.getPubDescription());
        Picasso.with(getActivity()).load(publicStatistics.getPubPhoto()).into(detailPublicImage);
        postResult.setText(String.valueOf(publicStatistics.getTotalPost()));
        averageAgeResult.setText(VkUtil.getFormatted(publicStatistics.getAverageAge()));
        averageLikesResult.setText(VkUtil.getFormatted(publicStatistics.getLikesPerPost()));
        subPerLikesResult.setText(VkUtil.getFormatted(publicStatistics.getSubPerAverageLikes()));
        manPercent.setManPart((int) publicStatistics.getManPersent());
    }
}
