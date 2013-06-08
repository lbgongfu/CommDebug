package com.lbgongfu.commdebug.util;

import java.util.Enumeration;

import javax.comm.CommPortIdentifier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommParamsUtil {
	public static final ObservableList<Integer> BAUDRATES = FXCollections
			.observableArrayList(300, 2400, 9600, 14400, 28800, 38400, 57600,
					152000);
	public static final ObservableList<String> FLOW_CONTROL_INS = FXCollections
			.observableArrayList("None", "Xon/Xoff In", "RTS/CTS In");
	public static final ObservableList<String> FLOW_CONTROL_OUTS = FXCollections
			.observableArrayList("None", "Xon/Xoff Out", "RTS/CTS Out");
	public static final ObservableList<Integer> DATABITS = FXCollections
			.observableArrayList(5, 6, 7, 8);
	public static final ObservableList<Integer> STOPBITS = FXCollections
			.observableArrayList(1, 2);
	public static final ObservableList<String> PARITYS = FXCollections
			.observableArrayList("None", "Old", "Even");

	public static ObservableList<String> listLocalPorts() {
		ObservableList<String> ports = FXCollections.observableArrayList();
		CommPortIdentifier identifier;
		Enumeration<?> enums = CommPortIdentifier.getPortIdentifiers();
		while (enums.hasMoreElements()) {
			identifier = (CommPortIdentifier) enums.nextElement();
			if (identifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				ports.add(identifier.getName());
			}
		}
		return ports;
	}
}
