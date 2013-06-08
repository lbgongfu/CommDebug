package com.lbgongfu.commdebug.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.control.SeparatorBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;

import com.lbgongfu.commdebug.comm.SerialConnection;
import com.lbgongfu.commdebug.util.BinaryUtil;

public class SendPane extends VBox {
	private SerialConnection connection;
	private FilePane filePane;
	
	private TextArea inputArea;
	private CheckBox hexBox;
	private Button sendBtn;
	private Button clearBtn;

	public SendPane(SerialConnection connection, FilePane filePane) {
		this.connection = connection;
		this.filePane = filePane;
		init();
	}

	private void init() {
		inputArea = TextAreaBuilder.create()
				.build();
		hexBox = CheckBoxBuilder.create()
				.text("十六进制")
				.selected(true)
				.build();
		sendBtn = ButtonBuilder.create()
				.id("sendBtn")
				.text("发送")
				.onAction(new MyActionEventHandler())
				.build();
		clearBtn = ButtonBuilder.create()
				.id("clearInputBtn")
				.text("清空发送区")
				.onAction(new MyActionEventHandler())
				.build();
		HBox hBox = HBoxBuilder.create()
				.spacing(10)
				.alignment(Pos.CENTER_LEFT)
				.children(
						hexBox,
						sendBtn, 
						SeparatorBuilder.create()
							.orientation(Orientation.VERTICAL)
							.build(),
						clearBtn
						)
				.build();
		setSpacing(10);
		setPadding(new Insets(10));
		getChildren().addAll(hBox, inputArea);
	}
	
	private void sendData() {
		String text = inputArea.getText();
		filePane.appendContent(text + "\n");
		byte[] data = null;
		if (hexBox.isSelected()) {
			data = BinaryUtil.HexString2Bytes(text);
		} else {
			data = text.getBytes();
		}
		try {
			connection.writeData(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class MyActionEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent ae) {
			Node source = (Node) ae.getSource();
			switch (source.getId()) {
			case "sendBtn" :
				inputArea.requestFocus();
				sendData();
				break;
			case "clearInputBtn" :
				inputArea.clear();
				inputArea.requestFocus();
				break;
			default :
				break;
			}
		}

	}
	
}
