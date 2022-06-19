import org.json.JSONException;
import org.json.JSONObject;

public class OnlineData {
    private String title;
    private String url;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Title", title);
            obj.put("URl", url);
        } catch (JSONException e) {
            System.out.println(("DefaultListItem.toString JSONException: " + e.getMessage()));
        }
        return obj;
    }
}
