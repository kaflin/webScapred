import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.JSONArray;

import java.io.FileWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ExtractData {

    public static void main(String[] args) {

        try {
            String searchQuery = "iphone 13";


            //Instantiate the client
            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            List<Item> list = new ArrayList<Item>();
            JSONArray jsonArray = new JSONArray();

            //Set up the URL with search term and send request
            String searchUrl = "https://newyork.craigslist.org/search/sss?query=" + URLEncoder.encode(searchQuery, "UTF-8");
            HtmlPage page = client.getPage(searchUrl);

            List<HtmlElement> items = page.getByXPath("//li[@class='result-row']");
            if (!items.isEmpty()) {

                for (HtmlElement item : items) {


                    //Get the details from <h3 class="result-heading"><a href=""></a></h3?
                    HtmlAnchor itemAnchor = (item.getFirstByXPath(".//h3[@class='result-heading']/a"));

                    //Get the price from <a><span class="result-price"></span></a>
                    HtmlElement spanprice = (item.getFirstByXPath(".//a/span[@class='result-price']"));

                    //It is possible that an item doesn't have any price
                    String itemPrice = spanprice == null ? "0.0" : spanprice.asNormalizedText();

                    Item item1 = new Item();

                    item1.setTitle(itemAnchor.asNormalizedText());
                    item1.setUrl(itemAnchor.getHrefAttribute());
                    item1.setPrice(itemPrice);
                    if (item1 != null) {
                        list.add(item1);
                    }
                }

                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(list.get(i).getJSONObject());
                }
                System.out.println(jsonArray);
                try (FileWriter file = new FileWriter("data.json")) {
                    file.write(jsonArray.toString());
                    System.out.println("Successfully Copied JSON Object to File...");
                    System.out.println("\nJSON Object: " + jsonArray);
                } catch (Exception e) {
                    System.out.println(e);

                }

            } else {
                System.out.println("No items found !");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred:" + e.getMessage());
        }


    }
}
