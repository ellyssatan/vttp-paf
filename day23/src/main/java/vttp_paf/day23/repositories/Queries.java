package vttp_paf.day23.repositories;

public class Queries {
    public static final String SQL_GET_STYLES = "select id, style_name from styles order by style_name asc";
    public static final String SQL_GET_BREWERIES = "select DISTINCT(brewery_name) from brewery_location where style_id = ? order by brewery_name";
}
