package com.inzynier.michau.przedszkoletecza.childInfo.trusted.ppl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inzynier.michau.przedszkoletecza.R;
import com.inzynier.michau.przedszkoletecza.childInfo.remark.RemakrsDto;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;

import org.threeten.bp.LocalDate;

import java.util.List;

public class TrustedPersonAdapter extends ArrayAdapter<TrustedPersonModel> {
    private List<TrustedPersonModel> trusts;
    private Activity activity;
    private long positionToDelete;
    private String phone;

    public TrustedPersonAdapter(@NonNull Activity activity, List<TrustedPersonModel> trusts) {
        super(activity, R.layout.trusted_item, trusts);
        this.trusts = trusts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.trusted_item, null);
        TextView name = convertView.findViewById(R.id.ti_name);
        TextView civilId = convertView.findViewById(R.id.ti_civilId);
        TextView phone = convertView.findViewById(R.id.ti_phone);

        TrustedPersonModel trustedPersonModel = trusts.get(position);
        name.setText(name.getText()+ " " +trustedPersonModel.getName()+ " " + trustedPersonModel.getSurname());
        phone.setText(phone.getText() +" "+trustedPersonModel.getPhone());
        civilId.setText(civilId.getText() + " " +trustedPersonModel.getCivilId());

        convertView.setOnLongClickListener(v -> {
            View action_menu = activity.findViewById(R.id.t_action_menu);
            LinearLayout top_container = activity.findViewById(R.id.t_top_container);
            TransitionManager.beginDelayedTransition(top_container, new Slide(Gravity.BOTTOM));
            action_menu.setVisibility(View.VISIBLE);
            positionToDelete = trusts.get(position).getId();
            this.phone = trusts.get(position).getPhone();
            return true;
        });

        return convertView;
    }

    public long getPositionToDelete() {
        return positionToDelete;
    }

    public String getPhone() {
        return phone;
    }
}