package swt6.spring.worklog.dao.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoJdbc implements EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected static class EmployeeRowManager implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            var empl = new Employee();
            empl.setId(rs.getLong(1));
            empl.setFirstName(rs.getString(2));
            empl.setLastName(rs.getString(3));
            empl.setDateOfBirth(rs.getDate(4).toLocalDate());
            return empl;
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        final String sql = "SELECT ID,FIRSTNAME,LASTNAME,DATEOFBIRTH from Employee where ID = ?";
        try {
            var foundEmployees = jdbcTemplate.query(sql, new EmployeeRowManager(), id);
            if (foundEmployees.size() == 0) return null;
            return foundEmployees.stream().findFirst();
        } catch (Exception exception) {
            throw exception;
        }
    }

    @Override
    public List<Employee> findAll() {

        final String sql = "select ID,FIRSTNAME,LASTNAME,DATEOFBIRTH from Employee";
        return jdbcTemplate.query(sql ,new EmployeeRowManager());
    }

    @Override
    public Employee merge(Employee entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity);
        }
        return entity;
    }

    private void update(Employee empl) throws DataAccessException{
        final String sql = "update EMPLOYEE set FIRSTNAME=?, LASTNAME=?, DATEOFBIRTH=? where ID = ?";
        jdbcTemplate.update(sql, empl.getFirstName(), empl.getLastName(), Date.valueOf(empl.getDateOfBirth()), empl.getId());
    }

    @Override
    public void insert(Employee entity) {
        final String sql =
                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
                        + "values (?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            var stmt = con.prepareStatement(sql, new String[]{"ID"});
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setDate(3, Date.valueOf(entity.getDateOfBirth()));
            return stmt;
        });
        entity.setId(holder.getKey().longValue());
    }

    //    public void insertShort(final Employee e) throws DataAccessException {
//        final String sql =
//                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
//                        + "values (?, ?, ?)";
//        try {
//            jdbcTemplate.update(sql , e.getFirstName(),e.getLastName(),Date.valueOf(e.getDateOfBirth()));
//        } catch (Exception sqlException) {
//
//        }
//    }
//
//    @Override
//    public void insert(final Employee e) throws DataAccessException {
//        final String sql =
//                "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
//                        + "values (?, ?, ?)";
//        try {
//            jdbcTemplate.update(sql, ps -> {
//                ps.setString(1, e.getFirstName());
//                ps.setString(2, e.getLastName());
//                ps.setDate(3, Date.valueOf(e.getDateOfBirth()));
//            });
//        } catch (Exception sqlException) {
//
//        }
//    }
}
