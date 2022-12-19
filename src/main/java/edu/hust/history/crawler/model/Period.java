package edu.hust.history.crawler.model;

import edu.hust.history.crawler.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Period {
    public static int count = 0;
    private final int id;
    private String name;
    private String href;
    private final List<Person> people = new ArrayList<>();
    private final List<Place> places = new ArrayList<>();
    private final List<Event> events = new ArrayList<>();
    private final List<Festival> festivals = new ArrayList<>();

    public Period(String name, String html){
        this.id = count;
        count++;
        this.name = name;
        this.href = html;
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

    public List<Person> getPeople() {
        return people;
    }

    public void addPeople(List<Person> people) {
        this.people.addAll(people);
    }

    public void addPeople(Person person) {
        this.people.add(person);
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void addPlaces(List<Place> places) {
        this.places.addAll(places);
    }

    public void addPlaces(Place places) {
        this.places.add(places);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events.addAll(events);
    }

    public List<Festival> getFestivals() {
        return festivals;
    }

    public void setFestivals(List<Festival> festivals) {
        this.festivals.addAll(festivals);
    }

    /**
     * Tìm và lưu danh sách nhân vật lịch sử có liên quan đến thời kỳ lịch sử
     * @throws IOException
     */
    public void setInfo() throws IOException {
        Document document = Jsoup.connect(Crawler.URI + this.href).get();
        Elements periodElements = document.getElementsByAttributeValue("class", "readmore");
        if (!periodElements.isEmpty()) {
            for (Element periodE : periodElements) {
                Element subPeriod = periodE.getElementsByAttributeValue("class", "btn").get(0);
                Document subDoc = Jsoup.connect(Crawler.URI + subPeriod.attr("href")).get();
                // Lấy danh sách nhân vật liên quan đến thời kỳ lịch sử
                try {
                    Element listRefCharacters = subDoc.getElementById("list-ref-characters");
                    Elements characters = listRefCharacters.getElementsByTag("a");
                    for (Element character : characters) {
                        // Bỏ qua trường hợp là các triều đại
                        if (character.attr("href").contains("/nha-")) continue;
                        boolean isExisted = false;
                        // Tạo đối tượng nhân vật dựa trên tên và đường dẫn đến thông tin nhân vật
                        Person person = new Person(character.text(), character.attr("href"));
                        // Nếu nhân vật đã được tạo thì bỏ qua
                        for (Person p : this.getPeople()) {
                            if (p.getHref().equals(person.getHref())) {
                                isExisted = true;
                                break;
                            }
                        }
                        if (!isExisted) this.addPeople(person);
                    }
                } catch (Exception e) {
                    System.out.println("Không tìm thấy thông tin nhân vật nào. " + e);
//                    throw new RuntimeException(e);
                }

                // Lấy danh sách địa danh liên quan đến thời kỳ lịch sử
                try {
                    Element listRefPlaces = subDoc.getElementById("list-ref-places");
                    Elements places = listRefPlaces.getElementsByTag("a");
                    for (Element place : places) {
                        boolean isExisted = false;
                        // Tạo đối tượng địa danh dựa trên tên và đường dẫn đến thông tin địa danh
                        Place place1 = new Place(place.text(), place.attr("href"));
                        // Nếu địa danh đã được tạo thì bỏ qua
                        for (Place place2 : this.getPlaces()) {
                            if (place2.getHref().equals(place1.getHref())) {
                                isExisted = true;
                                break;
                            }
                        }
                        if (!isExisted) this.addPlaces(place1);
                    }
                } catch (Exception e) {
                    System.out.println("Không tìm thấy thông tin địa danh nào. " + e);
//                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String toJson() {

        return null;
    }
}
