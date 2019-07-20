package presentation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mapapp.R;

import java.util.ArrayList;

public class AdminListAdapter extends BaseAdapter {

    private  static LayoutInflater inflater = null;
    String[][] data;

    public AdminListAdapter (Context context, String[][] data){

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.data = data;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.countrie_list_element, null);
        TextView country = view.findViewById(R.id.contryname);
        TextView season = view.findViewById(R.id.season);

        country.setText(data[position][0]);
        season.setText(data[position][1]);

        return view;
    }
}
