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
    ArrayList<String> countries;

    public AdminListAdapter (Context context, ArrayList<String> lol){

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        countries = lol;

    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.countrie_list_element, null);
        TextView wasa = view.findViewById(R.id.textView15);

        wasa.setText(countries.get(position));

        return view;
    }
}
