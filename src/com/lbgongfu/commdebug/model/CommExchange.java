package com.lbgongfu.commdebug.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CommExchange {
	private StringProperty receive;
	private StringProperty send;

	public CommExchange() {
		receive = new SimpleStringProperty();
		send = new SimpleStringProperty();
	}

	public void setReceive(String receive) {
		this.receive.set(receive);
	}

	public StringProperty receiveProperty() {
		return receive;
	}

	public StringProperty sendProperty() {
		return send;
	}

}
