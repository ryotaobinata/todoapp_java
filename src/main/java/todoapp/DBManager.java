package todoapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBManager {
	private static Connection conn=null;
	private String url="jbdc:h2:~/todoapp";
	private String user;
	private String password;
	
	public DBManager () {
		
	}
	
	//タスクの追加
	public void insert(Task task) {
		try {
			Class.forName("org.h2.Driver");
			conn=DriverManager.getConnection(url);
			
			//タスクの追加
			String sql="INSERT INTO tasks (title,due_date,description,is_done) VALUES(?,?,?,?)";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1, task.getTitle());
			//LocalDateをjava.sql.Dateに変換(Nullチェック)
			stmt.setDate(2, task.getDeadLine()!=null ?java.sql.Date.valueOf(task.getDeadLine()):null );
			stmt.setString(3, task.getInfo());
			stmt.setBoolean(4, task.isCompleted());
			
			int r=stmt.executeUpdate();
			
			if (r!=0) {
				
			}else {
				System.out.println("追加に失敗");
			}
			
			stmt.close();
			
		}catch (ClassNotFoundException e) {
			throw new IllegalStateException("ドライバのロードに失敗しました");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//終了処理
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	//タスクの更新
	public void update(Task task) {
		try {
			Class.forName("org.h2.Driver");
			conn=DriverManager.getConnection(url);
			
			//タスクの更新
			String sql="UPDATE tasks SET title=?,due_date=?,description=?,is_done=? WHERE id=?";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1, task.getTitle());
			//LocalDateをjava.sql.Dateに変換(Nullチェック)
			stmt.setDate(2, task.getDeadLine()!=null ?java.sql.Date.valueOf(task.getDeadLine()):null );
			stmt.setString(3, task.getInfo());
			stmt.setBoolean(4, task.isCompleted());
			stmt.setInt(5, task.getId());
			
			int r=stmt.executeUpdate();
			
			if (r!=0) {
				
			}else {
				System.out.println("更新に失敗");
			}
			
			stmt.close();
			
		}catch (ClassNotFoundException e) {
			throw new IllegalStateException("ドライバのロードに失敗しました");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//終了処理
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	//タスクの削除
	public void delete(List<Task>tasklist) {
		try {
			Class.forName("org.h2.Driver");
			conn=DriverManager.getConnection(url);
			
			//タスクの更新
			String sql="DELETE FROM tasks WHERE id = ?";
			PreparedStatement stmt=conn.prepareStatement(sql);
			
			for (Task task : tasklist) {
				stmt.setInt(1, task.getId());
				int r=stmt.executeUpdate();
				
				if (r!=0) {
					
				}else {
					System.out.println("更新に失敗");
				}
			}
			
			
			
			
			
			
			stmt.close();
			
		}catch (ClassNotFoundException e) {
			throw new IllegalStateException("ドライバのロードに失敗しました");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//終了処理
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
