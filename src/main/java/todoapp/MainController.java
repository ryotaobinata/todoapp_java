package todoapp;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class MainController {
	@FXML private Button addTaskButton=new Button();
	@FXML private Button removeTaskButton=new Button();
	@FXML private ListView<Task> taskListView=new ListView<Task>();
	@FXML private TextField taskInputField= new TextField();
	
	//詳細カードのコンポーネント
	@FXML private AnchorPane taskCard=new AnchorPane();
	@FXML private Label taskTitle=new Label();
	@FXML private TextArea description=new TextArea();
	@FXML private DatePicker taskDeadline=new DatePicker();
	@FXML private Button closeButton=new Button();
	
	private ObservableList<Task> itemList=FXCollections.observableArrayList();
	
	/*
	 * タスクの追加、削除、完了をチェックしたとき
	 * 詳細の閉じるボタンを押す
	 * 以上のタイミングで
	 * データベースの更新処理を行う予定
	 * */
	
	
	@FXML public void initialize() {
		taskListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		taskListView.setItems(itemList);
		taskListView.setCellFactory(lv->{
			return new ListCell<Task>(){
				private final CheckBox checkBox=new CheckBox();
				private final HBox content=new HBox(10);
				//private final Button deleteButton=new Button("詳細");
				private final Region spacer=new Region();
				private final Label taskName=new Label();
				
				{
					content.setAlignment(Pos.CENTER_LEFT);
					HBox.setHgrow(spacer, Priority.ALWAYS);
					content.getChildren().addAll(taskName,spacer,checkBox);
					
					checkBox.setOnAction(e->{
						Task task=getItem();
						if (task!=null) {
							task.setCompleted(checkBox.isSelected());
							if (checkBox.isSelected()) {
								checkBox.setText("できた!!!");
							}else {
								checkBox.setText("未完了");
							}
						}
						
					});
					
					
				}
				
				@Override
				protected void updateItem(Task item,boolean empty) {
					super.updateItem(item, empty);
					if (empty||item ==null) {
						setGraphic(null);
						setText(null);
					}else {
						
						if(!item.isCompleted()) checkBox.setText("未完了");
						else checkBox.setText("できた!!!");
							
						checkBox.setSelected(item.isCompleted());
						taskName.setText(item.getTitle());
						setGraphic(content);
						
					}
				}
			
			};
		});
		
	}
	
	@FXML public void onAddTaskClicked() {
		if (!taskInputField.getText().isEmpty()) {
			Task newTask=new Task();
			newTask.setTitle(taskInputField.getText());
			itemList.add(newTask);
			System.out.println(taskInputField.getText());
			taskInputField.clear();
		}
	}
	
	@FXML public void onRemoveTaskClicked() {
		//削除する際にインデックスが変わらないように、逆順に並べる
		List<Integer> selectedItems=new ArrayList<Integer>(taskListView.getSelectionModel().getSelectedIndices().sorted(Comparator.reverseOrder()));
		
		if (selectedItems!=null) {			
			for (int index : selectedItems) {
				itemList.remove(index);
			}
		}
	}
	
	@FXML public void onPressedKey(KeyEvent event) {
		if (event.getCode()==KeyCode.ENTER) {
			onAddTaskClicked();
			taskInputField.clear();
		}
	}
	
	//詳細カードの設定
	@FXML public void onCloseTaskCard() {
		taskCard.setVisible(false);
		
		//必要であればタスクを保存する処理を記述
	}
	
	
}
