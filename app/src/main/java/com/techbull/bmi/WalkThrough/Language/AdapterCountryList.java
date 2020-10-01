package com.techbull.bmi.WalkThrough.Language;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;
import com.techbull.bmi.R;

import java.util.List;

public class AdapterCountryList extends RecyclerView.Adapter<AdapterCountryList.ViewHolder> {
    private Context context;
    private List<CountryList> mCountryList;
    private int selectedItemPosition;

    public AdapterCountryList(Context context, List<CountryList> mCountryList) {
        this.context = context;
        this.mCountryList = mCountryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(mCountryList.get(position).getName());

        holder.layoutHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemPosition = position;
                notifyDataSetChanged();
            }
        });
        if (mCountryList.get(position).getName().equals(mCountryList.get(selectedItemPosition).getName())) {
            Prefs.putString("language", mCountryList.get(position).getShortName());
            holder.img.setVisibility(View.VISIBLE);
            holder.layoutHolder.setBackgroundColor(context.getResources().getColor(R.color.cardSelectedColor));
        } else {
            holder.img.setVisibility(View.INVISIBLE);
            holder.layoutHolder.setBackgroundColor(context.getResources().getColor(R.color.walk_through_background));
        }

    }

    @Override
    public int getItemCount() {
        return mCountryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;
        LinearLayout layoutHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.img);
            layoutHolder = itemView.findViewById(R.id.layoutHolder);

        }
    }
}
