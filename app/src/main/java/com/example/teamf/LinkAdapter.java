package com.example.teamf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teamf.ui.DonateFrag;

import java.util.List;

public class LinkAdapter extends ArrayAdapter<DonateLink> {
    int resource;

    public LinkAdapter(MainActivity ctx, int res, List<DonateLink> items)
    {
        super(ctx, res, items);
        resource = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        DonateLink link = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView setOrgName = (TextView) itemView.findViewById(R.id.link_detail);

        setOrgName.setText(link.getOrgName());

        return itemView;
    }

}
