package com.haruns.repository;

import com.haruns.entity.Comment;
import com.haruns.entity.User;
import com.haruns.utility.ConnectionProvider;
import com.haruns.utility.ConsoleTextUtils;
import com.haruns.utility.ICrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentRepository implements ICrud<Comment> {
	private final ConnectionProvider connectionProvider;
	private String sql;
	
	public CommentRepository() {
		this.connectionProvider=ConnectionProvider.getInstance();
	}
	
	@Override
	public Optional<Comment> save(Comment comment) {
		sql="INSERT INTO tblcomment (user_id,video_id,comment) VALUES (?,?,?)";
		try(PreparedStatement preparedStatement= connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, comment.getUser_id());
			preparedStatement.setLong(2, comment.getVideo_id());
			preparedStatement.setString(3, comment.getComment());
			
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Yorum kaydedilirken hata oluştu : "+e.getMessage());
		}
		
		return Optional.ofNullable(comment);
	}
	
	@Override
	public void delete(Long silinecekCommentId) {
		sql="DELETE FROM tblcomment WHERE id=?";
		try(PreparedStatement preparedStatement= connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1,silinecekCommentId);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Yorum silinirken hata oluştu. "+e.getMessage());
		}
	}
	
	@Override
	public Optional<Comment> update(Comment comment) {
		sql="UPDATE tblcomment SET comment=? WHERE id=?";
		try(PreparedStatement preparedStatement= connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1,comment.getComment());
			preparedStatement.setLong(2,comment.getId());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Yorum güncellenirken hata oluştu. "+e.getMessage());
		}
		return Optional.ofNullable(comment);
	}
	
	@Override
	public List<Comment> findAll() {
		sql="SELECT * FROM tblcomment";
		List<Comment> commentList=new ArrayList<>();
		try(PreparedStatement preparedStatement= connectionProvider.getPreparedStatement(sql)) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				long id = rs.getLong("id");
				long user_id = rs.getLong("user_id");
				long video_id = rs.getLong("video_id");
				String comment = rs.getString("comment");
				commentList.add(new Comment(id,user_id,video_id,comment));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Yorumlar listelenirken hata oluştu. "+e.getMessage());
		}
		return commentList;
	}
	
	@Override
	public Optional<Comment> findById(Long bulunacakCommentId) {
		sql="SELECT * FROM tblcomment WHERE id=?";
		try(PreparedStatement preparedStatement= connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1,bulunacakCommentId);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				long user_id = rs.getLong("user_id");
				long video_id = rs.getLong("video_id");
				String comment = rs.getString("comment");
				return Optional.of(new Comment(bulunacakCommentId,user_id,video_id,comment));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Yorum bulunamadı. "+e.getMessage());
		}
		return Optional.empty();
	}
	
	public List<Comment> findCommentOfVideo(Long video_id){
		sql="SELECT * FROM tblcomment WHERE video_id=?";
		List<Comment> commentList = new ArrayList<>();
		try(PreparedStatement preparedStatement= connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1,video_id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()){
				long id = rs.getLong("id");
				long user_id = rs.getLong("user_id");
				String comment = rs.getString("comment");
				commentList.add(new Comment(id,user_id,video_id,comment));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("Repository : Yorumlar listelenirken hata oluştu. "+e.getMessage());
		}
		return commentList;
	}
}