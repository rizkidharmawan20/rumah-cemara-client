package cemara.labschool.id.rumahcemara.home.service.asktheexpert;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.model.Topic;

public class AskTheExpertAdapter extends RecyclerView.Adapter<AskTheExpertAdapter.ViewHolder> {
    private List<Topic> topicModels;
    private Context context;

    public AskTheExpertAdapter(List<Topic> topics, Context context){
        this.topicModels = topics;
        this.context = context;
    }

    @Override
    public AskTheExpertAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_asktheexpert_topic, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AskTheExpertAdapter.ViewHolder holder, int position){
        final Topic topic = topicModels.get(position);
        final String topicId = topic.getId();
        final String question = topic.getQuestion();
        final String createAt = dateFormater(topic.getCreated_at(), "dd MMMM yyyy", "yyyy-MM-dd HH:mm:ss");
        final int status = topic.getStatus();

        holder.topicView.setText(question);
        holder.dateTopicView.setText(createAt);
        holder.topicList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatExpertActivity.class);
                intent.putExtra("topic_id", topicId);
                context.startActivity(intent);
            }
        });
    }

    public static String dateFormater(String dateFromJSON, String expectedFormat, String oldFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date date = null;
        String convertedDate = null;
        try {
            date = dateFormat.parse(dateFromJSON);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expectedFormat);
            convertedDate = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

    @Override
    public int getItemCount(){ return topicModels.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout topicList;
        public TextView topicView;
        public TextView dateTopicView;

        public ViewHolder(View v){
            super(v);

            topicView = (TextView) v.findViewById(R.id.topic);
            dateTopicView = (TextView) v.findViewById(R.id.date_topic);
            topicList = (LinearLayout) v.findViewById(R.id.topicList);
        }
    }
}
