package com.haruns.repository;

import com.haruns.entity.Comment;
import com.haruns.entity.User;
import com.haruns.entity.Video;
import com.haruns.utility.ConnectionProvider;
import com.haruns.utility.ConsoleTextUtils;
import com.haruns.utility.ICrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements ICrud<User> {
	private final ConnectionProvider connectionProvider;
	private String sql;
	
	public UserRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	@Override
	public Optional<User> save(User user) {
		sql = "INSERT INTO tbluser (name,surname,email,username,password) VALUES (?,?,?,?,?)";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getSurname());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Kullanıcı kaydedilirken hata oluştu : " + e.getMessage());
		}
		
		return Optional.ofNullable(user);
	}
	
	@Override
	public void delete(Long silinecekUserId) {
		sql = "DELETE FROM tbluser WHERE id=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, silinecekUserId);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Kullanıcı silinirken hata oluştu. " + e.getMessage());
		}
	}
	
	
	@Override
	public Optional<User> update(User user) {
		sql = "UPDATE tbluser SET name=?,surname=?,email=?,username=?,password=? WHERE id=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getSurname());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.setLong(6, user.getId());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Kullanıcı güncellenirken hata oluştu. " + e.getMessage());
		}
		return Optional.ofNullable(user);
	}
	
	@Override
	public List<User> findAll() {
		sql = "SELECT * FROM tbluser";
		List<User> userList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				userList.add(new User(id, name, surname, email, username, password));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Kullanıcı listelenirken hata oluştu. " + e.getMessage());
		}
		return userList;
	}
	
	@Override
	public Optional<User> findById(Long bulunacakUserId) {
		sql = "SELECT * FROM tbluser WHERE id=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, bulunacakUserId);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String email = rs.getString("email");
				String username = rs.getString("username");
				String password = rs.getString("password");
				return Optional.of(new User(bulunacakUserId, name, surname, email, username, password));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Kullanıcı bulunamadı. " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public boolean isExistUsername(String username) {
		sql = "SELECT * FROM tbluser WHERE username=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Kullanıcı aranırken hata oluştu " + e.getMessage());
		}
		return false;
	}
	
	public boolean isExistEmail(String email) {
		sql = "SELECT * FROM tbluser WHERE email=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Kullanıcı aranırken hata oluştu " + e.getMessage());
		}
		return false;
	}
	
	public boolean isUsernameAndMailExist(String username, String email) {
		sql = "SELECT * FROM tbluser WHERE username=? AND email=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, email);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Optional<User> findByUsername(String username) {
		sql = "SELECT * FROM tbluser WHERE username=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String email = rs.getString("email");
				String password = rs.getString("password");
				return Optional.of(new User(id, name, surname, email, username, password));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Kullanıcı bulunamadı. " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public Optional<User> findByUsernameAndPassword(String username, String password) {
		sql = "SELECT * FROM tbluser WHERE username=? AND password=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String email = rs.getString("email");
				return Optional.of(new User(id, name, surname, email, username, password));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Kullanıcı bulunamadı. " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public List<Video> getVideosOfUser(User user) {
		sql = "SELECT * FROM tblvideo WHERE user_id=?";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, user.getId());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				long user_id = rs.getLong("user_id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				long views = rs.getLong("views");
				videoList.add(new Video(id, user_id, title, description, views));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Kullanıcı bulunamadı. " + e.getMessage());
		}
		return videoList;
	}
	
	public List<Video> getLikedVideosOfUser(User user) {
		sql =
				"SELECT * FROM tblvideo WHERE id IN (SELECT video_id FROM tbllike WHERE user_id=? AND (status=1 OR " +
						"status=3))";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, user.getId());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				long views = rs.getLong("views");
				videoList.add(new Video(id, user.getId(), title, description, views));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Kullanıcı bulunamadı. " + e.getMessage());
		}
		return videoList;
	}
	
	public List<Comment> getAllCommentsOfUser(User user) {
		sql = "SELECT * FROM tblcomment WHERE user_id=?";
		List<Comment> commentList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, user.getId());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				long video_id = rs.getLong("video_id");
				String comment = rs.getString("comment");
				commentList.add(new Comment(id, user.getId(),video_id, comment));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Kullanıcının yorumları bulunamadı. " + e.getMessage());
		}
		return commentList;
	}
	
}