package BTCTwilioSMS.BTC;

import org.joda.time.LocalDateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;


public class BitCoin implements Runnable{

    private Double value;
    private LocalDateTime timeFetched;
    private boolean notify;

    public BitCoin() {
        this.notify = false;
        getPrice();
    }

    public void run(){
        getPrice();
    }

    private void getPrice(){
        try {
            String url = "https://www.google.com/search?q=BTC+price&oq=BTC+price&aqs=chrome..69i57j0l6j69i60.955j0j4&sourceid=chrome&ie=UTF-8";
            Document doc = Jsoup.connect(url).header("Content-Type", "text/*").get();

            String fetched_price = doc.getElementsByClass("DFlfde SwHCTb").text().replace(",", ".").replace(" ", "");
            Double price = Double.valueOf(fetched_price);
            LocalDateTime localDateTime = LocalDateTime.now();

            setValue(price);
            setTimeFetched(localDateTime);
            notify = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getTimeFetched() {
        return timeFetched;
    }

    public void setTimeFetched(LocalDateTime timeFetched) {
        this.timeFetched = timeFetched;
    }
}
