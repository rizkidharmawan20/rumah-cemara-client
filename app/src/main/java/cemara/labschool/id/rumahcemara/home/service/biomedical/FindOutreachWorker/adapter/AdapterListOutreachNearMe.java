package cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker.AppointmentFormOutreachActivity;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider.AppointmentFormActivity;
import cemara.labschool.id.rumahcemara.model.NearestOutreachModel;

public class AdapterListOutreachNearMe extends RecyclerView.Adapter<AdapterListOutreachNearMe.ViewHolder> {
    private List<NearestOutreachModel> articleModels;
    private Context context;

    public AdapterListOutreachNearMe(List<NearestOutreachModel> articleModels, Context context){
        this.articleModels = articleModels;
        this.context = context;
    }

    @Override
    public AdapterListOutreachNearMe.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_nearest_service, parent, false);

        return new AdapterListOutreachNearMe.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterListOutreachNearMe.ViewHolder holder, int position){
        final NearestOutreachModel articleModel = articleModels.get(position);
        final String id = articleModel.getId();
        final String userId = articleModel.getUser_id();
        final String srcImage = articleModel.getSrcImage();
        final String name = articleModel.getName();
        final String description = articleModel.getDescription();
        final String address = articleModel.getAddress();
        final String city = articleModel.getCity();
        final String phoneNumber = articleModel.getPhoneNumber();

        holder.textViewName.setText(name);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//        try {
//            Date date = sdf.parse(endDate);
//            String formated = new SimpleDateFormat("dd MMMM yyyy").format(date);
//            holder.textViewUpdatedAt.setText(formated);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.select_dp)).load(articleModel.getSrcImage()).into(holder.imageViewNearest);

        holder.linearLayout.setOnClickListener(view -> {
            View viewSheet = LayoutInflater.from(view.getContext()).inflate(R.layout.bottom_sheet_dialog_fragment, null);
            Log.d( "onClick: ",String.valueOf(viewSheet));
            final BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
            dialog.setContentView(viewSheet);
            dialog.show();
            ImageView close = dialog.findViewById(R.id.sheet_btn_close);
            ImageView imgProfile = dialog.findViewById(R.id.nearest_img);
            TextView tvname = dialog.findViewById(R.id.nearest_name);
            TextView tvaddress = dialog.findViewById(R.id.nearest_address);
            TextView tvcity = dialog.findViewById(R.id.nearest_city);
            TextView tvphone = dialog.findViewById(R.id.nearest_phone);

            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.select_dp)).load(articleModel.getSrcImage()).into(imgProfile);
            tvname.setText(name);
            tvaddress.setText(address);
            tvcity.setText(city);
            tvphone.setText(phoneNumber);

            if (close != null) {
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
            Button btnAppointment = dialog.findViewById(R.id.btn_appointment);
            if (btnAppointment != null) {
                btnAppointment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("user_id", userId);
                        bundle.putString("imgUrl", srcImage);
                        bundle.putString("fullname", name);
                        bundle.putString("address", address);
                        bundle.putString("phone", phoneNumber);
                        Intent intent = new Intent(view.getContext(), AppointmentFormOutreachActivity.class);
                        intent.putExtra("myData", bundle);
                        view.getContext().startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount(){ return articleModels.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageViewNearest;
        public FrameLayout linearLayout;

        public ViewHolder(View v){
            super(v);

            textViewName = v.findViewById(R.id.nearest_name);
            imageViewNearest = v.findViewById(R.id.nearest_img);
            linearLayout = v.findViewById(R.id.layout_article);
        }
    }

}