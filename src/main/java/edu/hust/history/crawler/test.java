package edu.hust.history.crawler;

import edu.hust.history.crawler.model.Period;
import edu.hust.history.crawler.model.Person;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        Period period = new Period("abc", "/dong-lich-su/nha-ly");
        period.setInfo();
        for (Person person:period.getPeople()) {
            System.out.println(person.getName());
            person.setInfo();
            System.out.println("\tSinh: " + person.getBirth() + "\n"
                            + "\tMất: " + person.getDeath() + "\n"
                            + "\tNiên hiệu: " + person.getAliases() + "\n"
                            + "\tTiền nhiệm: " + person.getPredecessor() + "\n"
                            + "\tKế nhiệm: " + person.getSuccessor() + "\n"
                            + "\tTrị vì: " + person.getReignTime() + "\n"
                            + "\tTên thật: " + person.getRealName() + "\n");
        }
//        Person person = new Person("abc", "/nhan-vat/ly-thai-to");
//        person.setInfo();
    }
}
