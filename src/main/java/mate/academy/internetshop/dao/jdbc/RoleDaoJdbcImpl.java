package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class RoleDaoJdbcImpl extends AbstractDao<Role> implements RoleDao {

    private static Logger logger = Logger.getLogger(RoleDaoJdbcImpl.class);

    public RoleDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public boolean attachRole(User user) {
        String role = user.getRoles().stream().findAny().get().toString();
        Long userId = user.getId();
        Long roleId = null;
        String getRoleIdQuery = "SELECT * FROM role WHERE role_name = ?;";
        try (PreparedStatement statement = connection.prepareStatement(getRoleIdQuery)) {
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                roleId = resultSet.getLong("role_id");
            }
        } catch (SQLException e) {
            logger.error("Can't get role_id", e);
            return false;
        }
        String attachRoleQuery = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(attachRoleQuery)) {
            statement.setLong(1, userId);
            statement.setLong(2, roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't attach role to user", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean attachRole(User user, Role role) {
        return false;
    }

    @Override
    public boolean detachRole(User user, Role role) {
        return false;
    }

    @Override
    public Set<Role> getRoles(User user) {
        Set<Role> roles = new HashSet<>();
        String query = "SELECT role.role_name "
                + "FROM user_role, role "
                + "WHERE user_role.role_id=role.role_id "
                + "AND user_role.user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String roleString = resultSet.getString("role_name");
                Role role = Role.of(roleString);
                roles.add(role);
            }
        } catch (SQLException e) {
            logger.error("Can't get roles", e);
            return null;
        }
        return roles;
    }
}
