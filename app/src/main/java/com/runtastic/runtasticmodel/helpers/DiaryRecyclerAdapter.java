package com.runtastic.runtasticmodel.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.realm.RealmController;

import java.util.ArrayList;

public class DiaryRecyclerAdapter extends RecyclerView.Adapter<DiaryRecyclerAdapter.DiaryViewHolder> {

    private static final String TAG = "DiaryRecyclerAdapter";

    private RealmController rControl;
    private Context mContext;

    ArrayList<String> testing = new ArrayList<>();

    public DiaryRecyclerAdapter(RealmController _rControl, Context _context){
        rControl = _rControl;
        mContext = _context;

        testing.add("1");
        testing.add("2");
        testing.add("3");
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.diary_recycler, viewGroup, false);
        DiaryViewHolder holder = new DiaryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder diaryViewHolder, int i) {
        //diaryViewHolder.text.setText(rControl.getDiary(rControl.getLoggedInUser()).get(i).getDetails());
        diaryViewHolder.text.setText(testing.get(i));
    }

    @Override
    public int getItemCount() {
        //return rControl.getDiary(rControl.getLoggedInUser()).size();
        return testing.size();
    }

    public class DiaryViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ConstraintLayout constraintLayout;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.diaryConstraint);
            text = itemView.findViewById(R.id.textView23);
        }
    }

}
