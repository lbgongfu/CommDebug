package com.lbgongfu.commdebug.view;

import org.controlsfx.dialog.Dialogs;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;

import com.lbgongfu.commdebug.comm.SerialConnection;
import com.lbgongfu.commdebug.comm.SerialConnectionException;
import com.lbgongfu.commdebug.model.SerialParameter;
import com.lbgongfu.commdebug.util.CommParamsUtil;

public class ParameterPane extends GridPane {
	private SerialConnection connection;
	private SerialParameter parameter;
	
	private Circle statusCircle;
	private ComboBox<String> portNameBox;
	private ComboBox<Integer> baudRateBox;
	private ComboBox<String> flowInBox;
	private ComboBox<String> flowOutBox;
	private ComboBox<Integer> databitsBox;
	private ComboBox<Integer> stopbitsBox;
	private ComboBox<String> parityBox;
	private Button closeBtn;
	
	
	private BooleanProperty open = new SimpleBooleanProperty();

	public ParameterPane(SerialConnection connection, SerialParameter parameter) {
		this.connection = connection;
		this.parameter = parameter;
		init();
		
		open.bind(connection.openProperty());
		open.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean oldValue, Boolean newValue) {
				if (newValue) {
					statusCircle.setFill(Color.web("#00FF00"));
				} else {
					statusCircle.setFill(Color.web("#ff0000"));
				}
			}});
	}
	
	public void setParameter() {
		parameter.setPortName(portNameBox.getValue());
		parameter.setBaudRate(baudRateBox.getValue());
		parameter.setFlowControlIn(flowInBox.getValue());
		parameter.setFlowControlOut(flowOutBox.getValue());
		parameter.setDatabits(databitsBox.getValue());
		parameter.setStopbits(stopbitsBox.getValue());
		parameter.setParity(parityBox.getValue());
	}

	private void init() {
		statusCircle = CircleBuilder.create()
				.radius(10)
				.fill(Color.web("#ff0000"))
				.effect(new Lighting())
				.build();
		Label label1 = LabelBuilder.create()
				.text("串口")
				.build();
		Label label2 = LabelBuilder.create()
				.text("波特率")
				.build();
		Label label3 = LabelBuilder.create()
				.text("输入")
				.build();
		Label label4 = LabelBuilder.create()
				.text("输出")
				.build();
		Label label5 = LabelBuilder.create()
				.text("数据位")
				.build();
		Label label6 = LabelBuilder.create()
				.text("停止位")
				.build();
		Label label7 = LabelBuilder.create()
				.text("奇偶校验")
				.build();
		portNameBox = new ComboBox<>();
		portNameBox.setPrefWidth(115);
		portNameBox.setItems(CommParamsUtil.listLocalPorts());
		portNameBox.getSelectionModel().selectFirst();
		portNameBox.valueProperty().addListener(new ValueChangedListener<String>());
		baudRateBox = new ComboBox<>();
		baudRateBox.setPrefWidth(115);
		baudRateBox.setItems(CommParamsUtil.BAUDRATES);
		baudRateBox.getSelectionModel().select(Integer.valueOf(9600));
		baudRateBox.valueProperty().addListener(new ValueChangedListener<Integer>());
		flowInBox = new ComboBox<>();
		flowInBox.setPrefWidth(115);
		flowInBox.setItems(CommParamsUtil.FLOW_CONTROL_INS);
		flowInBox.getSelectionModel().select("None");
		flowInBox.valueProperty().addListener(new ValueChangedListener<String>());
		flowOutBox = new ComboBox<>();
		flowOutBox.setPrefWidth(115);
		flowOutBox.setItems(CommParamsUtil.FLOW_CONTROL_OUTS);
		flowOutBox.getSelectionModel().select("None");
		flowOutBox.valueProperty().addListener(new ValueChangedListener<String>());
		databitsBox = new ComboBox<>();
		databitsBox.setPrefWidth(115);
		databitsBox.setItems(CommParamsUtil.DATABITS);
		databitsBox.getSelectionModel().select(Integer.valueOf(8));
		databitsBox.valueProperty().addListener(new ValueChangedListener<Integer>());
		stopbitsBox = new ComboBox<>();
		stopbitsBox.setPrefWidth(115);
		stopbitsBox.setItems(CommParamsUtil.STOPBITS);
		stopbitsBox.getSelectionModel().select(Integer.valueOf(1));
		stopbitsBox.valueProperty().addListener(new ValueChangedListener<Integer>());
		parityBox = new ComboBox<>();
		parityBox.setPrefWidth(115);
		parityBox.setItems(CommParamsUtil.PARITYS);
		parityBox.getSelectionModel().select("None");
		parityBox.valueProperty().addListener(new ValueChangedListener<String>());
		closeBtn = ButtonBuilder.create()
				.id("close")
				.text("关闭串口")
				.onAction(new MyActionEventHandler())
				.build();
		
		setAlignment(Pos.TOP_CENTER);
		setHgap(10);
		setVgap(5);
		setPadding(new Insets(10));
		getChildren().addAll(statusCircle, label1, portNameBox, label2, baudRateBox,
				label3, flowInBox, label4, flowOutBox, label5, databitsBox, label6, 
				stopbitsBox, label7, parityBox, closeBtn);
		GridPane.setConstraints(statusCircle, 0, 0);
		GridPane.setConstraints(closeBtn, 1, 0);
		GridPane.setConstraints(label1, 0, 1);
		GridPane.setConstraints(portNameBox, 1, 1);
		GridPane.setConstraints(label2, 0, 2);
		GridPane.setConstraints(baudRateBox, 1, 2);
		GridPane.setConstraints(label3, 0, 3);
		GridPane.setConstraints(flowInBox, 1, 3);
		GridPane.setConstraints(label4, 0, 4);
		GridPane.setConstraints(flowOutBox, 1, 4);
		GridPane.setConstraints(label5, 0, 5);
		GridPane.setConstraints(databitsBox, 1, 5);
		GridPane.setConstraints(label6, 0, 6);
		GridPane.setConstraints(stopbitsBox, 1, 6);
		GridPane.setConstraints(label7, 0, 7);
		GridPane.setConstraints(parityBox, 1, 7);
	}
	
	private class ValueChangedListener<T> implements ChangeListener<T> {

		@Override
		public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {
			if (connection.isOpen()) {
				connection.closeConnection();
			}
			try {
				setParameter();
				connection.openConnection();
			} catch (SerialConnectionException e) {
				Dialogs.create()
					.title("提示")
					.message(e.getMessage())
					.showWarning();
				e.printStackTrace();
			}
		}
		
	}
	
	private class MyActionEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent ae) {
			Node source = (Node) ae.getSource();
			switch (source.getId()) {
			case "close" :
				Button close = (Button) source;
				if (connection.isOpen()) {
					connection.closeConnection();
					close.setText("打开串口");
				} else {
					try {
						connection.openConnection();
						close.setText("关闭串口");
					} catch (SerialConnectionException e) {
						e.printStackTrace();
					}
				}
				break;
			default :
				break;
			}
		}
		
	}
}
