package edu.hust.history.crawler.model;

import edu.hust.history.crawler.Crawler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Person extends Model{
    private String period;
    private String birth;
    private String death;
    private String reignTime;
    private String predecessor;
    private String successor;
    private String aliases;
    private String realName;

    private List<Person> personList = new ArrayList<Person>();

    public static void getpersonList() throws IOException{
        String file = "src/main/resources/json/people.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(file)));
        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONArray("people"); // notice that `"posts": [...]`
        for (int i = 0; i < arr.length(); i++)
        {
            String href = arr.getJSONObject(i).getString("href");
            System.out.println(href);
        }
    }
    public Person() {
    }

    public Person(String name, String href, String period) {
        this.setPeriod(period);
        this.setName(name);
        this.setHref(href);
    }


    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    @Override
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
            System.out.println("Kh??ng c?? th??ng tin nh??n v???t "+ this.getName() + ". " + e);
        }
        this.setBirth(infoKV.get("Sinh"));
        this.setDeath(infoKV.get("M???t"));
        this.setAliases(infoKV.get("Ni??n hi???u"));
        this.setPredecessor(infoKV.get("Ti???n nhi???m"));
        this.setSuccessor(infoKV.get("K??? nhi???m"));
        this.setReignTime(infoKV.get("Tr??? v??"));
        this.setRealName(infoKV.get("T??n th???t"));
    }
}
