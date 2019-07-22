package presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mapapp.R;

public class ListViewAdapter extends BaseAdapter {

    private  static LayoutInflater inflater = null;

    Context context;
    String[][] data;

    public ListViewAdapter(Context context, String[][] data) {
        this.context = context;
        this.data = data;

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = inflater.inflate(R.layout.list_element, null);
       TextView title = view.findViewById(R.id.title);
       TextView description = view.findViewById(R.id.description);
       TextView rate_number = view.findViewById(R.id.rate_number);

       RatingBar ratingBar = view.findViewById(R.id.ratingBar);

       title.setText(data[position][0]);
       description.setText(data[position][1]);
       float rating = (Integer.valueOf(data[position][2]))/2;
       rate_number.setText(Float.toString(rating));

       ratingBar.setProgress(Integer.valueOf(data[position][2]));

        return view;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {

        return data[position][0];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
