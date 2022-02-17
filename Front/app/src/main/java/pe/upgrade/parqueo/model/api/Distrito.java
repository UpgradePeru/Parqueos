package pe.upgrade.parqueo.model.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Distrito {

    public static Distrito getDistrito(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");

        Distrito distrito = new Distrito(id, name, description);

        return distrito;
    }

    public static List<Distrito> getDistritos(JSONObject jsonObject) throws JSONException { //JSONArray
        List<Distrito> distritos = new ArrayList<>();

        //JSONObject jsonObj = jsonArray.getJSONObject(0);
        Boolean hasItems = jsonObject.getBoolean("hasItems");
        JSONArray jsonItems = jsonObject.getJSONArray("items");

        if (hasItems) {
            for (int i = 0; i != jsonItems.length(); i++) {
                JSONObject jsonObjItem = jsonItems.getJSONObject(i);
                Distrito distrito = Distrito.getDistrito(jsonObjItem);

                //Item item =Item.getItem(jsonObject.getJSONObject("item"));
                //distrito.setItem(item);

                distritos.add(distrito);
            }
        }
        return distritos;
    }

    private int id;
    private String name;
    private String description;

    public Distrito(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof Distrito) {
            Distrito that = (Distrito) obj;
            if (this.id == that.id) {
                result = true;
            }
        }
        return result;
    }

    public String toString() {
        return this.name;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("description", description);

        return jsonObject;
    }

    public int compareTo(Distrito that) {
        return this.name.compareTo(that.name);
    }

}
