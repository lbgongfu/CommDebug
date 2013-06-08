package com.lbgongfu.commdebug.view;

import java.util.Calendar;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;

import com.lbgongfu.commdebug.util.BinaryUtil;

public class ReceivePane extends VBox {
	private TextArea receiveArea;
	private CheckBox hexBox;
	private CheckBox stopShowBox;
	private Button clearBtn;
	
	public ReceivePane() {
		init();
	}
	
	private void init() {
		receiveArea = TextAreaBuilder.create()
				.build();
		hexBox = CheckBoxBuilder.create()
				.text("十六进制")
				.selected(true)
				.build();
		stopShowBox = CheckBoxBuilder.create()
				.text("停止显示")
				.build();
		clearBtn = ButtonBuilder.create()
				.id("clearReceiveBtn")
				.text("清空接收区")
				.onAction(new MyActionEventHandler())
				.build();
		HBox hBox = HBoxBuilder.create()
			.spacing(10)
			.alignment(Pos.CENTER_LEFT)
			.children(hexBox, stopShowBox, clearBtn)
			.build();
		setSpacing(10);
		setPadding(new Insets(10));
		getChildren().addAll(hBox, receiveArea);
	}

	public void updateList(final byte[] readData) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (!stopShowBox.isSelected()) {
					String text = String.format("%tF %<tT\t", Calendar.getInstance());
					if (hexBox.isSelected()) {
						text += BinaryUtil.Bytes2HexString(readData);
					} else {
						text += new String(readData);
					}
					receiveArea.appendText(text + "\n");
				}
			}});
	}
	
	private class MyActionEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent ae) {
			Node source = (Node) ae.getSource();
			switch (source.getId()) {
			case "clearReceiveBtn" :
				receiveArea.clear();
				break;
			default :
				break;
			}
		}
		
	}

}
