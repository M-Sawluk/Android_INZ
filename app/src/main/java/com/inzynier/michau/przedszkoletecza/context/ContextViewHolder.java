package com.inzynier.michau.przedszkoletecza.context;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.inzynier.michau.przedszkoletecza.R;
import de.hdodenhof.circleimageview.CircleImageView;

class ContextViewHolder extends RecyclerView.ViewHolder {
    CircleImageView image;
    TextView name;

    ContextViewHolder(View itemView) {
        super(itemView);
        image =  itemView.findViewById(R.id.contex_img);
        name =  itemView.findViewById(R.id.context_title);
    }
}