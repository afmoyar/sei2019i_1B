package dataAccess.SignalWrappers;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminUpdatePayload implements Serializable {

    public String id;
    @SerializedName("select")
    public ArrayList<String> countriesToSelect;
    @SerializedName("drop")
    public ArrayList<String> countriesToDrop;

    public AdminUpdatePayload(String id, ArrayList<String> countriesToSelect, ArrayList<String> countriesToDrop) {

        this.id = id;
        this.countriesToSelect = countriesToSelect;
        this.countriesToDrop = countriesToDrop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getCountriesToSelect() {
        return countriesToSelect;
    }

    public void setCountriesToSelect(ArrayList<String> countriesToSelect) {
        this.countriesToSelect = countriesToSelect;
    }

    public ArrayList<String> getCountriesToDrop() {
        return countriesToDrop;
    }

    public void setCountriesToDrop(ArrayList<String> countriesToDrop) {
        this.countriesToDrop = countriesToDrop;
    }
}
