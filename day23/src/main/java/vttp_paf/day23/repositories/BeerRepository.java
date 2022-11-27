package vttp_paf.day23.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_paf.day23.models.BeerStyle;

import static vttp_paf.day23.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class BeerRepository {
    
    @Autowired
    private JdbcTemplate template;

    public List<BeerStyle> getStyles() throws Exception {

        // Perform query
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_STYLES);
        List<BeerStyle> styles = new LinkedList<>();

        // Attempt to move cursor to next row
        while (rs.next()) {
            // have record -> add to list
            BeerStyle style = new BeerStyle();
            style.setStyle_id(Integer.parseInt(rs.getString(1)));
            style.setStyle_name(rs.getString(2));

            styles.add(style);
        }

        // System.out.println(">>>>>> Styles" + styles);
        return styles;
    }

    public List<String> getBreweries(int id) throws Exception {

        // Perform query
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_BREWERIES, id);
        final List<String> listOfBreweries = new LinkedList<>();

        // Attempt to move cursor to next row
        while (rs.next()) {
            // have record -> add to list
            listOfBreweries.add(rs.getString(1));
        }

        // System.out.println(">>>>>> List of breweries" + listOfBreweries);
        return listOfBreweries;
    }
}
