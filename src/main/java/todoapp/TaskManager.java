package todoapp;

import java.util.List;

public class TaskManager {
	/*
	 * 必要な機能
	 * タスクの追加
	 * タスクの保存(更新)
	 * タスク一覧の取得
	 * タスクの削除
	 * 
	 * このクラスに必要なフィールド
	 * データベースのインスタンス
	 * タスク一覧
	 */
	private List<Task> taskList;	
	private DBManagerInterface dbManagerInterface;
	
	
	/*
	 * 初期化
	 * データベースに接続をする
	 */
	public TaskManager() {
		dbManagerInterface=new DBManager();
		updateTaskList();
	}
	
	public List<Task> geTasks() {
		return taskList;
	}
	
	
	//データベース操作
	public int insert(Task task) {
		int id;
		id = dbManagerInterface.insert(task);
		updateTaskList();
		return id;
	}
	public void update(Task task) {
		dbManagerInterface.update(task);
		updateTaskList();
	}
	public void delete(List<Task> tasks) {
		dbManagerInterface.delete(tasks);
		updateTaskList();
	}
	public List<Task> getAll(){
		return dbManagerInterface.getAll();
	}
	
	private void updateTaskList() {
		taskList=getAll();
	}
}
