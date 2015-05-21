package com.gdg.studyjam.smartchef;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anu on 4/21/2015.
 */
public class MyAdapter extends ArrayAdapter {

    public MyAdapter(Context context, ArrayList<Course> arrayList) {
        super(context,0, arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course courses=(Course)getItem(position);
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_in_list_view,parent,false);//listitem.xml name

        }
//this is not activity class so cannot wirte findviewById directly

        TextView title= (TextView)convertView.findViewById(R.id.nameTxt);
        TextView desc= (TextView) convertView.findViewById(R.id.cousinesTxt);
       ImageView image2= (ImageView) convertView.findViewById(R.id.imageView);

        title.setText(courses.name);
        desc.setText(courses.recipe_count);
        if(courses.img==null){

        }else {
            new DownloadImageTask((ImageView) convertView.findViewById(R.id.imageView))
                    .execute(courses.img);
        }
        return convertView;
    }
}

