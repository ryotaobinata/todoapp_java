package todoapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBManager implements DBManagerInterface{
	private static Connection conn=null;
	private String url="jdbc:h2:~/todoapp";
	private String user="";
	private String password="";
	
	public DBManager () {
		
	}
	
	//タスクの追加 戻り値は追加したタスクのID
	@Override
	public int insert(Task task) {
		
		String sql="INSERT INTO tasks (title,due_date,description,is_done) VALUES(?,?,?,?)";
		PreparedStatement stmt=null;
		int id = 0;
		
		try {
			Class.forName("org.h2.Driver");
			conn=DriverManager.getConnection(url,user,password);
			
			//タスクの追加
			stmt=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, task.getTitle());
			//LocalDateをjava.sql.Dateに変換(Nullチェック)
			stmt.setDate(2, task.getDeadLine()!=null ?java.sql.Date.valueOf(task.getDeadLine()):null );
			stmt.setString(3, task.getInfo());
			stmt.setBoolean(4, task.isCompleted());
			
			int r=stmt.executeUpdate();
			
			if (r!=0) {
				//追加したタスクのIDを取得
				ResultSet rs=stmt.getGeneratedKeys();
				if (rs.next()) {
					id=rs.getInt(1);
					return id;
				}
			}else {
				System.out.println("追加に失敗");
			}
			
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
				if (stmt!=null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		// 0の場合は何らかの原因で追加に失敗している
		return id;
		
	}
	
	//タスクの更新
	@Override
	public void update(Task task) {
		
		String sql="UPDATE tasks SET title=?,due_date=?,description=?,is_done=? WHERE id=?";
		PreparedStatement stmt=null;
		
		try {
			Class.forName("org.h2.Driver");
			conn=DriverManager.getConnection(url,user,password);
			
			//タスクの更新
			stmt=conn.prepareStatement(sql);
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
				if (stmt!=null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	//タスクの削除
	@Override
	public void delete(List<Task>tasklist) {
		
		//String sql="DELETE FROM tasks WHERE id = ?";
		//sql文の構築
		if (!tasklist.isEmpty()) {			
			StringBuilder sql=new StringBuilder("DELETE FROM tasks WHERE id IN(");
			sql.append("?,".repeat(tasklist.size()));
			sql.setLength(sql.length()-1);
			sql.append(")");
			
			PreparedStatement stmt=null;
			
			try {
				Class.forName("org.h2.Driver");
				conn=DriverManager.getConnection(url,user,password);
				
				//タスクの更新
				stmt=conn.prepareStatement(sql.toString());
				for(int i=0;i<tasklist.size();i++) {
					stmt.setInt(i+1, tasklist.get(i).getId());
				}
				
				int r=stmt.executeUpdate();
				
				if (r!=0) {
					
				}else {
					System.out.println("更新に失敗");
				}
				
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("ドライバのロードに失敗しました");	
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				//終了処理
				try {
					if (conn!=null) {
						conn.close();
					}
					if (stmt!=null) {
						stmt.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}else {
			System.out.println("tasklist is empty");
		}
		
	}
	
	
	//タスク一覧の取得
	@Override
	public List<Task> getAll() {
		
		List<Task> tasks=new ArrayList<Task>();
		String sql="SELECT * FROM tasks ORDER BY id";
		PreparedStatement stmt=null;
		ResultSet rSet=null;
		
		try {
			Class.forName("org.h2.Driver");
			conn=DriverManager.getConnection(url,user,password);
			
			//全てのタスクを取得
			stmt=conn.prepareStatement(sql);
			rSet=stmt.executeQuery();
			
			while (rSet.next()) {
				tasks.add(convertResultSetToTask(rSet));
			}
			
			return tasks;
			
			
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
				if (stmt!=null) {
					stmt.close();
				}
				if (rSet!=null) {
					rSet.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return tasks;
	}
	
	//データベースの結果をTaskに変換
	private Task convertResultSetToTask(ResultSet rSet) throws SQLException {
		
		int id=rSet.getInt(1);
		String title=rSet.getString(2);
		LocalDate due_date=rSet.getDate(3)!=null ? rSet.getDate(3).toLocalDate() : null;
		String description=rSet.getString(4);
		boolean isDone=rSet.getBoolean(5);
		
		return new Task(id,title,due_date,description,isDone);
		
	}
}
