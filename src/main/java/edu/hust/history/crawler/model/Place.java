package edu.hust.history.crawler.model;

import edu.hust.history.crawler.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class Place {
    public static int count = 0;
    private final int id;
    private String name;
    private String href;

    public Place() {
        this.id = count;
        count++;
    }

    public Place(String name, String href){
        this.id = count;
        count++;
        this.name = name;
        this.href = href;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setInfo() throws IOException {
        Document document = Jsoup.connect(Crawler.URI + this.getHref()).timeout(0).get();
        HashMap<String, String> infoKV = new HashMap<>();
        try {
            Element infoElement = document.getElementsByClass("infobox").get(1);
            Elements trElements = infoElement.getElementsByTag("tr");
            for (Element tr : trElements) {
                infoKV.put(tr.getElementsByTag("th").text().trim(),
                        tr.getElementsByTag("td").text().trim());
            }
        } catch (Exception e) {
            System.out.println("Không có thông tin địa danh "+ this.getName() + ". " + e);
        }
    }
}