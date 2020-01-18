package com.example.urlsaver;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UrlEntryList extends ArrayAdapter<UrlEntry> {

    private Activity context;
    private List<UrlEntry> urlEntryList;

    public UrlEntryList(Activity context, List<UrlEntry> urlEntryList) {
        super(context, R.layout.list_layout, urlEntryList);
        this.context = context;
        this.urlEntryList = urlEntryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);
        TextView textViewlink = (TextView) listViewItem.findViewById(R.id.textViewLink);

        UrlEntry urlEntry = urlEntryList.get(position);

        textViewDescription.setText(urlEntry.getDescription());
        textViewlink.setText(urlEntry.getLink());

        return listViewItem;
    }
}
