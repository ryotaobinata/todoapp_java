package todoapp;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;

//import com.fasterxml.jackson.annotation.JsonFormat;

public class Task {
	private String title;
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate deadLine;
	private String info;
	private int id;
	private boolean isCompleted;
	
	public Task() {
		setCompleted(false);
	}
	
	public Task(String title,LocalDate deadLine,String info) {
		this.title=title;
		this.deadLine=deadLine;
		this.info=info;
	}
	
	public Task(String title,LocalDate deadLine,String info,int id) {
		this(title, deadLine, info);
		this.id=id;
		this.isCompleted=false;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDate getDeadLine() {
		return this.deadLine;
	}
	public void setDeadLine(LocalDate deadLine) {
		this.deadLine = deadLine;
	}
	public String getInfo() {
		return this.info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public boolean isCompleted() {
		return this.isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this,o);
	}
	
	
}
