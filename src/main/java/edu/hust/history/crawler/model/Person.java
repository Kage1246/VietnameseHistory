package edu.hust.history.crawler.model;

import edu.hust.history.crawler.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class Person {
    public static int count = 0;
    private final int id;
    private String name;
    private String href;
    private String birth;
    private String death;
    private String reignTime;
    private String predecessor;
    private String successor;
    private String aliases;
    private String realName;

    public Person(String name, String href){
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getReignTime() {
        return reignTime;
    }

    public void setReignTime(String reignTime) {
        this.reignTime = reignTime;
    }

    public String getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(String predecessor) {
        this.predecessor = predecessor;
    }

    public String getSuccessor() {
        return successor;
    }

    public void setSuccessor(String successor) {
        this.successor = successor;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setInfo() throws IOException {
        Document document = Jsoup.connect(Crawler.URI + this.getHref()).get();
        HashMap<String, String> infoKV = new HashMap<>();
        try {
            Element infoElement = document.getElementsByClass("infobox").get(1);
            Elements trElements = infoElement.getElementsByTag("tr");
            for (Element tr : trElements) {
                infoKV.put(tr.getElementsByTag("th").text().trim(),
                        tr.getElementsByTag("td").text().trim());
            }
        } catch (Exception e) {
            System.out.println("Không có thông tin nhân vật "+ this.getName() + ". " + e);
        }
        this.setBirth(infoKV.get("Sinh"));
        this.setDeath(infoKV.get("Mất"));
        this.setAliases(infoKV.get("Niên hiệu"));
        this.setPredecessor(infoKV.get("Tiền nhiệm"));
        this.setSuccessor(infoKV.get("Kế nhiệm"));
        this.setReignTime(infoKV.get("Trị vì"));
        this.setRealName(infoKV.get("Tên thật"));
    }
}
