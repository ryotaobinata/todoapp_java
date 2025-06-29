package todoapp;

import java.util.List;

public interface DBManagerInterface {
	public int insert(Task task);
	public void update(Task task);
	public void delete(List<Task> tasks);
	public List<Task> getAll();
	//public Task getById(int id);
}
