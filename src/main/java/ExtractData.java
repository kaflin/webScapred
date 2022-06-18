import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


import java.net.URLEncoder;
import java.util.List;

public class ExtractData {

    public static void main(String[] args) {

        try {
            String searchQuery = "iphone 13";


            //Instantiate the client
            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);

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

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String jsonString = mapper.writeValueAsString(item1);
                        System.out.println(jsonString);
                    } catch (Exception e) {
                        System.out.println("Exception occurred:" + e.getMessage());
                    }
//                    System.out.println(String.format("Name:%s ,Url:%s ,Price:%s", itemName, itemUrl, itemPrice));
                }
            } else {
                System.out.println("No items found !");
            }

        } catch (Exception e) {
            System.out.println("Exception occurred:" + e.getMessage());
        }


    }
}
