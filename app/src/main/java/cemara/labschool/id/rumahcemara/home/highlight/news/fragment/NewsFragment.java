package cemara.labschool.id.rumahcemara.home.highlight.news.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.Constants;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.NewsHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.MarkNewsClickListener;
import cemara.labschool.id.rumahcemara.util.NewsClickListener;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import cemara.labschool.id.rumahcemara.util.news.adapter.NewsAdapter;
import cemara.labschool.id.rumahcemara.util.news.model.News;
import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    List<News> newsList = new ArrayList<>();
    String id;
    private Context context;
    //carousel
    @BindView(R.id.carousel)
    CarouselView customCarouselView;
    String[] sampleNetworkImageURLs = {
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image1&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image4&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image5&txt=350%C3%97150&w=350&h=150"
    };
    String[] sampleTitles = {"Artikel 1", "Artikel 2", "Artikel 3"};

    List<cemara.labschool.id.rumahcemara.model.News> newsPager=new ArrayList<>();

    View rootView;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.news_campaign_fragment, container, false);
        ButterKnife.bind(this, rootView);
        context = getActivity();
        try {
            assert getArguments() != null;
            id = getArguments().getString("id", "");
        }catch (Exception ignored){}
        getListNews(id);
        return rootView;
    }

    private void getListNews(String id) {
//        Loading.show(getContext());
        NewsHelper.getNewsCampaign(id, new RestCallback<ApiResponse<List<cemara.labschool.id.rumahcemara.model.News>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<cemara.labschool.id.rumahcemara.model.News>> body) {
                Loading.hide(getContext());
                if (body != null && body.isStatus()) {
                    newsList.clear();
                    List<cemara.labschool.id.rumahcemara.model.News> newsLists=body.getData();
                    Log.d("aa","sss");

                    // Insert Pager
                    int maxPager=newsLists.size()> Constants.MAX_NEWS_PAGER?Constants.MAX_NEWS_PAGER: newsLists.size();
                    for(int i=0;i<maxPager;i++){
                        newsPager.add(newsLists.get(0));//Always get position 0 , because always delete item already get below
                        newsLists.remove(0);
                    }
                    for(int i=0;i<newsLists.size();i++){
                        newsList.add(new News(newsLists.get(i).getId(), newsLists.get(i).getTitle(), newsLists.get(i).getUserCreator().getProfile().getFullname(), DateHelper.dateFormat(DateHelper.stringToDate(newsLists.get(i).getCreatedAt())), newsLists.get(i).getBanner()));
                    }
                    //newsList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_news));

                    newsAdapter = new NewsAdapter(getContext(), newsList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    initSlider(rootView);


                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(getContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Toast.makeText(getContext(),"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                Loading.hide(getContext());
            }

            @Override
            public void onCanceled() {
                Loading.hide(getContext());
            }
        });

    }

    private void initSlider(View v) {
        //customCarouselView.setPageCount(sampleTitles.length);
        customCarouselView.setViewListener(viewListener);
        customCarouselView.setPageCount(newsPager.size());
        customCarouselView.setSlideInterval(4000);
    }


    // To set images
    ImageListener imageListener = (position, imageView) -> Glide.with(getContext())
            .load(sampleNetworkImageURLs[position])
            .into(imageView);

    // To set custom views
    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.carousel_custom_news, null);
            TextView tvLabelCarousel = customView.findViewById(R.id.tv_label_carousel);
            ImageView ivCarousel = customView.findViewById(R.id.iv_carousel);
            ImageView ivShare = customView.findViewById(R.id.iv_carousel_share);
            ImageView ivMark = customView.findViewById(R.id.iv_carousel_mark);

            Glide.with(getContext())
                    .load(newsPager.get(position).getBanner())
                    .into(ivCarousel);
            tvLabelCarousel.setText(newsPager.get(position).getTitle());
            ivShare.setOnClickListener(v -> {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, newsPager.get(position).getTitle()+
                        "\n"+ newsPager.get(position).getBanner());
                startActivity(Intent.createChooser(sharingIntent, "Share using:"));
            });
           // ivMark.setOnClickListener(markOnClickListener);
            ivMark.setOnClickListener(new MarkNewsClickListener(context, newsPager.get(position)));

            ivCarousel.setOnClickListener(new NewsClickListener(newsPager.get(position)));
            return customView;
        }
    };
}
