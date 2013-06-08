package com.lbgongfu.commdebug.view;

import java.io.File;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import com.lbgongfu.commdebug.file.FileHelper;

public class FilePane extends VBox {
	private TreeView<File> fileList;
	private TextArea fileContent;
	private Button saveBtn;
	private BooleanProperty isSaved = new SimpleBooleanProperty(false);
	
	private FileHelper fileHelper;
	
	public FilePane(FileHelper fileHelper) {
		this.fileHelper = fileHelper;
		init();
		saveBtn.disableProperty().bind(isSaved);
	}

	private void init() {
		fileList = new TreeView<File>();
		fileList.setPrefHeight(130);
		fileList.setPrefWidth(200);
		fileContent = TextAreaBuilder.create()
				.prefWidth(200)
				.build();
		saveBtn = ButtonBuilder.create()
				.text("保存")
				.onAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						
					}})
				.build();
		setSpacing(5);
		setPadding(new Insets(10));
		getChildren().addAll(
				LabelBuilder.create()
					.text("文件列表")
					.build(),
				fileList,
				LabelBuilder.create()
					.text("文件内容")
					.build(),
				fileContent,
				saveBtn
				);
		VBox.setVgrow(fileContent, Priority.ALWAYS);
	}
	
	public void appendContent(String content) {
		String text = fileContent.getText();
		if (text.contains(content)) {
			return;
		}
		fileContent.appendText(content);
	}
	
	public void setHasSavedSaved(boolean isSaved) {
		this.isSaved.set(isSaved);
	}
	
	public BooleanProperty isSavedProperty() {
		return isSaved;
	}
}
