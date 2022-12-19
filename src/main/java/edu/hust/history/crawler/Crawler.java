package edu.hust.history.crawler;

import edu.hust.history.crawler.model.Period;
import edu.hust.history.crawler.model.Person;
import edu.hust.history.crawler.model.Place;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Crawler {
    public static final String URI = "https://nguoikesu.com";
    public static final String TIMELINE_HREF = "/dong-lich-su";

    public static void main(String[] args) throws IOException {
        // Tạo document từ url dòng lịch sử
        Document document = Jsoup.connect(URI + TIMELINE_HREF).get();
        Element mainContext = document.getElementById("jm-main");

        // Crawler các thời kỳ lịch sử
        Elements periodEs = mainContext.getElementsByAttributeValue("class", "level-0");
        for (Element periodE:periodEs) {
            String href = periodE.getElementsByTag("a").get(0).attr("href");
            String name = periodE.getElementsByTag("a").get(0).text();
            Period period = new Period(name,href);
            period.setInfo();

            // Print
            System.out.println(period.getName());
            for (Person person:period.getPeople()) {
                System.out.println("\t" + person.getHref());
                person.setInfo();
                System.out.println("\tSinh: " + person.getBirth() + "\n"
                        + "\tMất: " + person.getDeath() + "\n");
            }
            for (Place place:period.getPlaces()) {
                System.out.println("\t" + place.getHref());
                System.out.println("\t" + place.getName());
                place.setInfo();
            }
            System.out.println("");
        }
    }
}
