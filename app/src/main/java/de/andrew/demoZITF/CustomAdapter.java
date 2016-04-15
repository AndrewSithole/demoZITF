package de.andrew.demoZITF;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Andrew on 4/12/16.
 */
public class CustomAdapter extends BaseAdapter {
    String [] result;
    Context context;
    Double [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context mContext, String[] prgmNameList, Double[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mContext;
        imageId=prgmImages;
        inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv,tv1;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item_collapsable, null);
        holder.tv=(TextView) rowView.findViewById(R.id.text1);
        holder.tv1=(TextView) rowView.findViewById(R.id.text2);
        holder.tv.setText(result[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}