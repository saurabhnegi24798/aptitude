package com.example.aptitude;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private int sets=0;
    String category;

    public GridAdapter(int sets,String category){
        this.sets=sets;
        this.category=category;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view;
        if(convertView==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item,parent,false);
        }
        else {
            view = convertView;
        }
        ((TextView)view.findViewById(R.id.text)).setText(String.valueOf(position + 1));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),QuestionsActivity.class);
                intent.putExtra("Category",category);
                intent.putExtra("Set","SET"+String.valueOf(position+1));
                view.getContext().startActivity(intent);
            }
        });
        return  view;
    }
}
