package com.lbgongfu.commdebug.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPaneBuilder;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TitledPaneBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lbgongfu.commdebug.comm.SerialConnection;
import com.lbgongfu.commdebug.comm.SerialConnectionException;
import com.lbgongfu.commdebug.file.FileHelper;
import com.lbgongfu.commdebug.model.SerialParameter;

public class Main extends Application {
	private SerialParameter parameter;
	private SerialConnection connection;
	private ParameterPane paramPane;
	private ReceivePane receivePane;
	private SendPane sendPane;
	private FilePane filePane;
	
	private TitledPane left_top;
	private TitledPane left_bottom;
	private TitledPane right_top;
	private TitledPane right_bottom;
	
	Scene sceneRef;

	@Override
	public void start(Stage primaryStage) throws Exception {
		parameter = new SerialParameter();
		receivePane = new ReceivePane();
		connection = new SerialConnection(parameter, receivePane);
		filePane = new FilePane(new FileHelper());
		sendPane = new SendPane(connection, filePane);
		paramPane = new ParameterPane(connection, parameter);
		
		sceneRef = SceneBuilder.create()
				.width(700)
				.height(500)
				.fill(Color.LIGHTBLUE)
				.root(createRoot())
				.stylesheets(Main.class.getResource("index.css").toExternalForm())
				.build();
		primaryStage.setTitle("串口调试");
		primaryStage.setMinWidth(sceneRef.getWidth());
		primaryStage.setMinHeight(sceneRef.getHeight());
		primaryStage.setScene(sceneRef);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				if (connection.isOpen()) {
					connection.closeConnection();
				}
			}});
		primaryStage.setResizable(false);
		primaryStage.show();
		
		//left.prefWidth(sceneRef.getWidth() * 0.29);
		//left.maxWidth(sceneRef.getWidth() * 0.29);
		left_top.prefHeightProperty().bind(sceneRef.heightProperty().divide(2));
		left_bottom.prefHeightProperty().bind(sceneRef.heightProperty().divide(2));
		//right_top.prefWidthProperty().bind(sceneRef.widthProperty().subtract(left.getPrefWidth()));
		right_top.prefHeightProperty().bind(sceneRef.heightProperty().divide(2));
		//right_bottom.prefWidthProperty().bind(sceneRef.widthProperty().subtract(left.getPrefWidth()));
		right_bottom.prefHeightProperty().bind(sceneRef.heightProperty().divide(2));
		
		paramPane.setParameter();
		try {
			connection.openConnection();
		} catch (SerialConnectionException e) {
			e.printStackTrace();
		}
	}

	private Parent createRoot() {
		SplitPane root = SplitPaneBuilder.create()
				.orientation(Orientation.HORIZONTAL)
				.dividerPositions(new double[]{0.29})
				.items(
						VBoxBuilder.create()
							.children(
									left_top = TitledPaneBuilder.create()
										.text("串口参数设置")
										.content(paramPane)
										.build(),
									left_bottom = TitledPaneBuilder.create()
										.text("文件浏览")
										.content(filePane)
										.build()
									)
							.build(),
						VBoxBuilder.create()
							.children(
									right_top = TitledPaneBuilder.create()
										.text("数据接收区")
										.content(receivePane)
										.build(),
									right_bottom = TitledPaneBuilder.create()
										.text("数据发送区")
										.content(sendPane)
										.build()
									)
							.build()
						)
				.build();
		return root;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
