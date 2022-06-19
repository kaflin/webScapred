import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.JSONArray;

import java.io.FileWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onlinekhabar {
    public static void main(String[] args) {

        try {
            String searchQuery = "Modi";
            ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2));


            //Instantiate the client
            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            List<OnlineData> list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray();

            //Set up the URL with search term and send request
            for (Integer num : numbers) {
                String searchUrl = "https://english.onlinekhabar.com/page/" + num + "?s=" + URLEncoder.encode(searchQuery, "UTF-8");
                HtmlPage page = client.getPage(searchUrl);


                List<HtmlElement> items = page.getByXPath("//div[@class='ok-news-post ltr-post']");
                if (!items.isEmpty()) {

                    for (HtmlElement item : items) {
                        HtmlElement itemHeading = (item.getFirstByXPath(".//div[@class='ok-post-contents']/h2"));
                        HtmlAnchor itemAnchor = (item.getFirstByXPath("//div[@class='ok-post-contents']/h2/a"));
                        OnlineData onlineData = new OnlineData();
                        onlineData.setTitle(itemHeading.asNormalizedText());
                        onlineData.setUrl(itemAnchor.getHrefAttribute());
                        if (onlineData != null) {
                            list.add(onlineData);
                        }
                    }

                    for (int i = 0; i < list.size(); i++) {
                        jsonArray.put(list.get(i).getJSONObject());
                    }
                    try (FileWriter file = new FileWriter("onlineKhabar.json")) {
                        file.write(jsonArray.toString());
                        System.out.println("Successfully Copied JSON Object to File...");
                        System.out.println("\nJSON Object: " + jsonArray);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("No items found !");
                }
            }
        }catch (Exception e) {
            System.out.println("Exception occurred:" + e.getMessage());
        }

    }
}
