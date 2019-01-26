package cemara.labschool.id.rumahcemara.home.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.service.asktheexpert.AskTheExpertActivity;
import cemara.labschool.id.rumahcemara.home.service.behavioral.CounselingAppointmentActivity;
import cemara.labschool.id.rumahcemara.home.service.biomedical.BiomedicalAppointmentActivity;
import cemara.labschool.id.rumahcemara.home.service.structural.LegalCounselingAppointmentActivity;
import cemara.labschool.id.rumahcemara.home.service.structural.StructuralLegalAidActivity;
import cemara.labschool.id.rumahcemara.home.service.structural.StructuralViolationActivity;
import cemara.labschool.id.rumahcemara.util.news.adapter.NewsAdapter;
import cemara.labschool.id.rumahcemara.util.news.model.News;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.btn_highlight_menu)
    ImageView btnHighlight;
    @BindView(R.id.highlight_menu_item)
    LinearLayout highlightItem;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    List<News> newsList = new ArrayList<>();
    Dialog dialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, rootView);
        init();
        getListNews();
        return rootView;
    }

    @OnClick(R.id.btn_biomedical)
    public void dialogBiomedical() {
        showDialog(R.layout.dialog_biomedical);
        final CardView biomedical_appointment = dialog.findViewById(R.id.btn_biomedical_appointment);
        biomedical_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                toast();
                startActivity(new Intent(getContext(), BiomedicalAppointmentActivity.class));
            }
        });
        ImageView closeDialog = dialog.findViewById(R.id.btn_close);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    @OnClick(R.id.btn_behavioral)
    public void dialogBehavioral() {
        showDialog(R.layout.dialog_behavioral);
        CardView behavioral_appointment = dialog.findViewById(R.id.btn_behavioral_appointment);
        ImageView closeDialog = dialog.findViewById(R.id.btn_close);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        behavioral_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CounselingAppointmentActivity.class));
            }
        });
    }
    @OnClick(R.id.btn_structural)
    public void dialogStructural() {
        showDialog(R.layout.dialog_structural);
        CardView structural_appointment = dialog.findViewById(R.id.btn_structural_appointment);
        CardView legal_aid = dialog.findViewById(R.id.btn_legal_aid);
        CardView violation_report = dialog.findViewById(R.id.btn_violation_report);
        ImageView closeDialog = dialog.findViewById(R.id.btn_close);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        structural_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                toast();
                Intent intent = new Intent(getContext(), LegalCounselingAppointmentActivity.class);
                startActivity(intent);
            }
        });
        legal_aid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                toast();
                Intent intent = new Intent(getContext(), StructuralLegalAidActivity.class);
                startActivity(intent);
            }
        });
        violation_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                toast();
                Intent intent = new Intent(getContext(), StructuralViolationActivity.class);
                startActivity(intent);
            }
        });
    }
    @OnClick(R.id.btn_ask_the_expert)
    public void toAsktheExpert() {
        showDialog(R.layout.dialog_behavioral);
        CardView biomedical_appointment = dialog.findViewById(R.id.btn_behavioral_appointment);
        biomedical_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AskTheExpertActivity.class));
            }
        });
    }



    private void getListNews() {
        newsList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_news));
        newsList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_news));
        newsAdapter = new NewsAdapter(getActivity(), newsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }

    private void init() {

        btnHighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (highlightItem.getVisibility() == View.VISIBLE) {
                    highlightItem.setVisibility(View.GONE);
                    btnHighlight.setImageResource(R.drawable.list_menu_icon_unselected);
                } else {
                    highlightItem.setVisibility(View.VISIBLE);
                    btnHighlight.setImageResource(R.drawable.list_menu_icon_selected);
                }
            }
        });
    }

    private void toast() {
        Toast toast = Toast.makeText(getContext(), "On Progress", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showDialog(int layout) {
        dialog = new Dialog(Objects.requireNonNull(getContext()));
        //SET TITLE
        dialog.setTitle("Biomedical");

        //set content
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
