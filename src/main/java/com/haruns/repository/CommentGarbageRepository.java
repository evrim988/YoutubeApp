package com.haruns.repository;

import com.haruns.entity.CommentGarbage;
import com.haruns.utility.ConnectionProvider;
import com.haruns.utility.ConsoleTextUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentGarbageRepository {
	private final ConnectionProvider connectionProvider;
	private String sql = "";
	private static CommentGarbageRepository instance;

	public static CommentGarbageRepository getInstance(){
		if(instance == null){
			instance = new CommentGarbageRepository();
		}
		return instance;
	}


	private CommentGarbageRepository() {
		connectionProvider = ConnectionProvider.getInstance();
	}



	
	public Optional<CommentGarbage> save(CommentGarbage commentGarbage) {
		sql = "INSERT INTO tblcommentgarbage (comment_id,comment) VALUES (?,?)";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, commentGarbage.getComment_id());
			preparedStatement.setString(2, commentGarbage.getComment());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Eski Yorum kaydedilirken hata oluştu : " + e.getMessage());
		}
		return Optional.ofNullable(commentGarbage);
	}
	
	public List<CommentGarbage> findAllOldComments(Long comment_id) {
		sql = "SELECT * FROM tblcommentgarbage WHERE comment_id=?";
		List<CommentGarbage> commentGarbageList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, comment_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String comment = resultSet.getString("comment");
				
				CommentGarbage commentGarbage = new CommentGarbage(id, comment_id, comment);
				commentGarbageList.add(commentGarbage);
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Eski Yorumlar listelenirken hata oluştu : " + e.getMessage());
		}
		return commentGarbageList;
	}
}