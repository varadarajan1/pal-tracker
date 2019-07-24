package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private JdbcTemplate template;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder holder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = (connection) -> {
            PreparedStatement statement = connection.prepareStatement("INSERT into time_entries (project_Id,user_Id,date,hours) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());
            return statement;
        };
        this.template.update(creator, holder);
        return this.find(holder.getKey().longValue());
    }

    @Override
    public TimeEntry find(Long id) {
        return this.template.query("Select * from time_entries where id=?", new Object[]{id}, extractor);
    }

    @Override
    public List<TimeEntry> list() {
        return template.query("SELECT id, project_id, user_id, date, hours FROM time_entries", mapper);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        this.template.update("Update time_entries " +
                "set project_id=?, user_Id=?,date=?,hours=? where id=?",
                timeEntry.getProjectId(),
                timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours(),id);
        return this.find(id);
    }
    @Override
    public void delete(Long id) {
          this.template.update("Delete from time_entries where id=?",id);
    }

    private RowMapper<TimeEntry> mapper = (resultSet, rowNum) -> new TimeEntry(resultSet.getLong("id"),
            resultSet.getLong("project_Id"),
            resultSet.getLong("user_id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getInt("hours")
    );

    private ResultSetExtractor<TimeEntry> extractor = (resultSet) -> resultSet.next() ? mapper.mapRow(resultSet, 1) : null;
}
