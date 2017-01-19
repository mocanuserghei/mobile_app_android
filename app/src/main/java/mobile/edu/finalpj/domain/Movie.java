package mobile.edu.finalpj.domain;

/**
 * Created by ysyh on 23.11.2016.
 */
public class Movie {

    private String key;
    private String name;
    private String producer;
    private String description;

    public static int counter = 0;

    public Movie(String  key, String name, String producer, String description) {
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + "\n" +
                producer + "\n" +
                description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
