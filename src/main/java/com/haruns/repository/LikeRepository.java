package com.haruns.repository;

import com.haruns.entity.Like;
import com.haruns.utility.ConnectionProvider;
import com.haruns.utility.ConsoleTextUtils;
import com.haruns.utility.ICrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LikeRepository implements ICrud<Like> {
	private final ConnectionProvider connectionProvider;
	private String sql;
	
	public LikeRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	
	@Override
	public Optional<Like> save(Like like) {
		sql = "INSERT INTO tbllike(user_id, video_id) VALUES (?,?)";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1,like.getUser_id());
			preparedStatement.setLong(2,like.getVideo_id());
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository: Like kaydedilme sırasında hata oluştu: " + e.getMessage());
		}
		return Optional.empty();
	}
	
	@Override
	public void delete(Long id) {
		sql = "DELETE FROM tbllike WHERE id=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)){
			preparedStatement.setLong(1,id);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository: Like silme sırasında hata oluştu: " + e.getMessage());
		}
	}
	
	@Override
	public Optional<Like> update(Like like) {
		return Optional.empty();
	}
	
	@Override
	public List<Like> findAll() {
		sql = "SELECT * FROM tbllike";
		List<Like> likeList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)){
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				long user_id = rs.getLong("user_id");
				long video_id = rs.getLong("video_id");
				Like like = new Like(id,user_id,video_id);
				likeList.add(like);
			}
			
		} catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository: Like listelerken hata oluştu: " + e.getMessage());
		}
		return likeList;
	}
	
	@Override
	public Optional<Like> findById(Long bulunacakLikeId) {
		sql = "SELECT * FROM tbllike WHERE id=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)){
			preparedStatement.setLong(1,bulunacakLikeId);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				long user_id = rs.getLong("user_id");
				long video_id = rs.getLong("video_id");
				return Optional.of(new Like(bulunacakLikeId,user_id,video_id));
			}
			
		} catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository: Like bulunamadı... " + e.getMessage());
		}
		return Optional.empty();
	}
}