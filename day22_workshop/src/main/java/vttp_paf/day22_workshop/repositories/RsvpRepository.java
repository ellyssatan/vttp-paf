package vttp_paf.day22_workshop.repositories;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_paf.day22_workshop.models.Rsvp;

import static vttp_paf.day22_workshop.repositories.Queries.*;

@Repository
public class RsvpRepository {

    @Autowired
    private JdbcTemplate template;

    public List<Rsvp> getAllRsvps() {
        // prevent inheritance
       final List<Rsvp> rsvpList = new LinkedList<>();
       
       final SqlRowSet rs = template.queryForRowSet(SQL_GET_ALL_RSVP);

       while (rs.next()) {
            rsvpList.add(Rsvp.create(rs));
       }
       
       return rsvpList;
    }

    public List<Rsvp> getRsvpByName(String name) {

        final List<Rsvp> rsvpList = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_RSVP_BY_NAME, name);

        while (rs.next()) {
            rsvpList.add(Rsvp.create(rs));
        }

        return rsvpList;
    }

    public Rsvp insertRsvp(Rsvp rsvp) {

        KeyHolder keyholder = new GeneratedKeyHolder();
        try {
            template.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RSVP,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, rsvp.getName());
                ps.setString(2, rsvp.getEmail());
                ps.setInt(3, rsvp.getPhone());
                System.out.println("Confirmation date > " + rsvp.getConfirmation_date());
                ps.setTimestamp(4, new Timestamp(rsvp.getConfirmation_date().toDateTime().getMillis()));
                ps.setString(5, rsvp.getComments());
                return ps;
            }, keyholder);
            BigInteger primaryKeyVal = (BigInteger) keyholder.getKey();
            rsvp.setId(primaryKeyVal.intValue());

        } catch (DataIntegrityViolationException e) {
            Rsvp existingRSVP = findRsvpByEmail(rsvp.getEmail());
            existingRSVP.setComments(rsvp.getComments());
            existingRSVP.setName(rsvp.getName());
            existingRSVP.setPhone(rsvp.getPhone());
            existingRSVP.setConfirmation_date(rsvp.getConfirmation_date());
            if (updateRsvp(existingRSVP, rsvp.getEmail()))
                rsvp.setId(existingRSVP.getId());
        }

        return rsvp;
    }

    // find old rsvp record
    public Rsvp findRsvpByEmail(String email) {

        Rsvp r = new Rsvp();
        final SqlRowSet rs = template.queryForRowSet(SQL_FIND_RSVP_BY_EMAIL, email);

        while (rs.next()) {
            r = Rsvp.create(rs);
        }

        return r;
    }

    public boolean updateRsvp(Rsvp p, String email) {
        return template.update(SQL_UPDATE_RSVP,
                        p.getName(),
                        p.getEmail(),
                        p.getPhone(),
                        new Timestamp(p.getConfirmation_date().toDateTime().getMillis()),
                        p.getComments(),
                        email) > 0;
    }

    public int countRsvp() {
        System.out.println(">>>>>>>>> COUNT IS: " + template.queryForRowSet(SQL_COUNT_RSVP).getInt(1));
        return template.queryForRowSet(SQL_COUNT_RSVP).getInt("total");
    }

}
