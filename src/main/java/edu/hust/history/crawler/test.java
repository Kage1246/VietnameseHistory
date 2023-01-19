package edu.hust.history.crawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hust.history.crawler.model.Period;
import edu.hust.history.crawler.model.Person;
import edu.hust.history.crawler.model.Place;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class test {
    public static final ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {
//        Period period = new Period("Nhà Lý", "/dong-lich-su/nha-hau-le");
//        period.setInfo();
//        for (Person person:period.getPeople()) {
//            System.out.println(person.getName());
//            person.setInfo();
//            System.out.println("\tSinh: " + person.getBirth() + "\n"
//                            + "\tMất: " + person.getDeath() + "\n"
//                            + "\tNiên hiệu: " + person.getAliases() + "\n"
//                            + "\tTiền nhiệm: " + person.getPredecessor() + "\n"
//                            + "\tKế nhiệm: " + person.getSuccessor() + "\n"
//                            + "\tTrị vì: " + person.getReignTime() + "\n"
//                            + "\tTên thật: " + person.getRealName() + "\n");
//        }
//        for (Place place:period.getPlaces()) {
//            System.out.println("Place: " + place.getName() + "\n");
//            place.setInfo();
//            mapper.writeValue(new File("src/main/resources/json/test.json"), place);
//        }

        Person.getpersonList();
    }
}
