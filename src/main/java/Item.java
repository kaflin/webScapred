import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class Item {
    private String title;
    private String price;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Title", title);
            obj.put("Price", price);
            obj.put("URl", url);
        } catch (JSONException e) {
            System.out.println(("DefaultListItem.toString JSONException: "+e.getMessage()));
        }
        return obj;
    }
}
