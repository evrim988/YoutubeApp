package com.haruns.repository;

import com.haruns.dto.request.LikeRequestDTO;
import com.haruns.entity.Like;
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

public class LikeRepository implements ICrud<Like> {
	private final ConnectionProvider connectionProvider;
	private String sql;
	private static LikeRepository instance;
	
	private LikeRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}

	public static LikeRepository getInstance() {
		if (instance == null) {
			instance = new LikeRepository();
		}
		return instance;
	}
	
	
	@Override
	public Optional<Like> save(Like like) {
		sql = "INSERT INTO tbllike(user_id, video_id,status) VALUES (?,?,?)";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1,like.getUser_id());
			preparedStatement.setLong(2,like.getVideo_id());
			preparedStatement.setInt(3,like.getStatus());
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
		sql="UPDATE tbllike SET user_id=?, video_id=?,status=? WHERE id=?";
		try(PreparedStatement preparedStatement= connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1,like.getUser_id());
			preparedStatement.setLong(2,like.getVideo_id());
			preparedStatement.setInt(3,like.getStatus());
			preparedStatement.setLong(4,like.getId());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Like güncellenirken hata oluştu. "+e.getMessage());
		}
		return Optional.ofNullable(like);
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
				int status = rs.getInt("status");
				Like like = new Like(id,user_id,video_id,status);
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
				int status = rs.getInt("status");
				return Optional.of(new Like(bulunacakLikeId,user_id,video_id,status));
			}
			
		} catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository: Like bulunamadı... " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public boolean isLikeExist(Long user_id,Long video_id) {
		sql = "SELECT * FROM tbllike WHERE user_id=? AND video_id=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)){
			preparedStatement.setLong(1,user_id);
			preparedStatement.setLong(2,video_id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository: Like bulunamadı... " + e.getMessage());
		}
		return false;
	}
	
	public Like findByUserIdAndVideoId(Long user_id,Long video_id) {
		sql = "SELECT * FROM tbllike WHERE user_id=? AND video_id=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)){
			preparedStatement.setLong(1,user_id);
			preparedStatement.setLong(2,video_id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				long id = rs.getLong("id");
				int status = rs.getInt("status");
				return new Like(id,user_id,video_id,status);
			}
			
		} catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository: Like bulunamadı... " + e.getMessage());
		}
		return null;
	}

	public Long countLikes(Long videoId){
		sql = "SELECT COUNT(*) FROM tbllike WHERE video_id=? AND (status=1 OR status=3)";
		Long sayac = 0L;
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)){
			preparedStatement.setLong(1,videoId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				sayac += rs.getLong(1);
			}

		} catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository: Like bulunamadı... " + e.getMessage());
		}
		return sayac;
	}

	public Long countDislikes(Long videoId){
		sql = "SELECT COUNT(*) FROM tbllike WHERE video_id=? AND (status=-1 OR status=5)";
		Long sayac = 0L;
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)){
			preparedStatement.setLong(1,videoId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				sayac += rs.getLong(1);
			}

		} catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository: Like bulunamadı... " + e.getMessage());
		}
		return sayac;
	}


}