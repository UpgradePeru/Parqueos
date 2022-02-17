package pe.upgrade.parqueo.model.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cancha {

    public static Cancha getCancha(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");

        double price = jsonObject.getDouble("price");
        String contact = jsonObject.getString("contact");
        String address = jsonObject.getString("address");
        double latitude = jsonObject.getDouble("latitude");
        double longitude = jsonObject.getDouble("longitude");
        String urlImg = jsonObject.getString("urlImg");
        int distritoId = jsonObject.getInt("distritoId");
        boolean enable = jsonObject.getBoolean("enable");

        Cancha cancha = new Cancha(id, name, description, price, contact, address, latitude, longitude, urlImg, distritoId, enable);

        return cancha;
    }

    public static List<Cancha> getCanchas(JSONObject jsonObject) throws JSONException { //JSONArray
        List<Cancha> canchas = new ArrayList<>();

        //JSONObject jsonObj = jsonArray.getJSONObject(0);
        Boolean hasItems = jsonObject.getBoolean("hasItems");
        JSONArray jsonItems = jsonObject.getJSONArray("items");

        if (hasItems) {
            for (int i = 0; i != jsonItems.length(); i++) {
                JSONObject jsonObjItem = jsonItems.getJSONObject(i);
                Cancha cancha = Cancha.getCancha(jsonObjItem);

                //Item item =Item.getItem(jsonObject.getJSONObject("item"));
                //distrito.setItem(item);

                canchas.add(cancha);
            }
        }
        return canchas;
    }

    private int id;
    private String name;
    private String description;
    private double price;
    private String contact;
    private String address;
    private double latitude;
    private double longitude;
    private String urlImg;
    private int distritoId;
    private boolean enable;

    public Cancha(int id, String name, String description, double price, String contact, String address, double latitude, double longitude, String urlImg, int distritoId, boolean enable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.contact = contact;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.urlImg = urlImg;
        this.distritoId = distritoId;
        this.enable = enable;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public int getDistritoId() {
        return distritoId;
    }

    public void setDistritoId(int distritoId) {
        this.distritoId = distritoId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof Cancha) {
            Cancha that = (Cancha) obj;
            if (this.id == that.id) {
                result = true;
            }
        }
        return result;
    }

    public String toString() {
        return this.name;
    }

    public JSONObject toJSON22() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("description", description);

        return jsonObject;
    }

    public int compareTo(Cancha that) {
        return this.name.compareTo(that.name);
    }

}
