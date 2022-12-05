package vttp_paf.day26.models;

import org.bson.Document;
import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class TVShow {
    
    private String name;
    private String summary;
    private String image;
    private double rating;

    public String getName() {       return name;       }
    public void setName(String name) {       this.name = name;       }

    public String getSummary() {       return summary;       }
    public void setSummary(String summary) {       this.summary = summary;       }

    public String getImage() {       return image;       }
    public void setImage(String image) {       this.image = image;       }

    public double getRating() {       return rating;       }
    public void setRating(double rating) {       this.rating = rating;       }

    public static TVShow create(Document doc) {

        final TVShow show = new TVShow();

        show.setName(doc.getString("name"));
        show.setSummary(doc.getString("summary"));

        Document d = doc.get("rating", Document.class);
        Object r = d.get("average");
		if (r instanceof Integer)
			show.setRating(((Integer)r).doubleValue());
		else
			show.setRating((Double)r);

        d = doc.get("image", Document.class);
        show.setImage(d.getString("original"));

        return show;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("name", name)
            .add("summary", defaultValue(summary, "no summary"))
            .add("image", defaultValue(image, "https://media.istockphoto.com/id/1149316411/vector/concept-404-error-page-flat-cartoon-style-vector-illustration.jpg?s=612x612&w=0&k=20&c=dLlOE7s6GuI4a5so_ipUFHeW9kaFWZVf-JTrFu5rAIk="))
            .add("rating", defaultValue(rating, 0d))
            .build();
    }

    private <T> T defaultValue(T value, T defValue) {
		if (null != value)
			return value;
		return  defValue;
	}
}
