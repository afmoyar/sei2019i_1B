package presentation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mapapp.R;

public class SeasonCountriesAdapter extends BaseAdapter{

    private  static LayoutInflater inflater = null;

    Context context;
    String[][] data;

    public SeasonCountriesAdapter(Context context, String[][] data) {
        this.context = context;
        this.data = data;

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_season_countries, null);
        TextView title = view.findViewById(R.id.title);
        title.setText(data[position][0]);
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
