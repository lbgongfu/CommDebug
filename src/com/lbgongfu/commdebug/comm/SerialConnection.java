package com.lbgongfu.commdebug.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.comm.CommPortIdentifier;
import javax.comm.CommPortOwnershipListener;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import com.lbgongfu.commdebug.model.SerialParameter;
import com.lbgongfu.commdebug.view.ReceivePane;

public class SerialConnection implements SerialPortEventListener,
		CommPortOwnershipListener {
	private ReceivePane receivePane;
	private SerialParameter parameter;
	private CommPortIdentifier identifier;
	private SerialPort port;
	private static InputStream in;
	private static OutputStream out;
	private BooleanProperty open = new SimpleBooleanProperty(false);
	public static final int timeout = 1000;

	public SerialConnection(SerialParameter parameter, ReceivePane receivePane) {
		this.receivePane = receivePane;
		this.parameter = parameter;
	}

	public void openConnection() throws SerialConnectionException {
		try {
			identifier = CommPortIdentifier.getPortIdentifier(parameter
					.getPortName());
		} catch (NoSuchPortException e) {
			throw new SerialConnectionException("没有发现" + parameter.getPortName() + "串口！");
		}
		try {
			port = (SerialPort) identifier.open("串口调试", timeout);
		} catch (PortInUseException e) {
			throw new SerialConnectionException("串口"+parameter.getPortName()+"被其他程序占用！");
		}

		try {
			setParameters();
		} catch (SerialConnectionException e) {
			port.close();
			throw e;
		}

		try {
			in = port.getInputStream();
			out = port.getOutputStream();
		} catch (IOException e) {
			port.close();
			throw new SerialConnectionException("打开I/O流出错！");
		}

		try {
			port.addEventListener(this);
		} catch (TooManyListenersException e) {
			port.close();
			throw new SerialConnectionException("too many listeners added");
		}

		port.notifyOnDataAvailable(true);
		port.notifyOnBreakInterrupt(true);

		try {
			port.enableReceiveTimeout(30);
		} catch (UnsupportedCommOperationException e) {
		}

		identifier.addPortOwnershipListener(this);
		open.set(true);
	}

	private void setParameters() throws SerialConnectionException {
		int oldBaudRate = port.getBaudRate();
		int oldFlowControlModel = port.getFlowControlMode();
		int oldDatabits = port.getDataBits();
		int oldStopbits = port.getStopBits();
		int oldParity = port.getParity();

		try {
			port.setSerialPortParams(parameter.getBaudRate(),
					parameter.getDatabits(), parameter.getStopbits(),
					parameter.getParity());
		} catch (UnsupportedCommOperationException e) {
			System.out.println(parameter);
			parameter.setBaudRate(oldBaudRate);
			parameter.setDatabits(oldDatabits);
			parameter.setStopbits(oldStopbits);
			parameter.setParity(oldParity);
			throw new SerialConnectionException("当前串口不支持该参数设置！");
		}

		try {
			port.setFlowControlMode(parameter.getFlowControlIn()
					| parameter.getFlowControlOut());
		} catch (UnsupportedCommOperationException e) {
			throw new SerialConnectionException("当前串口不支持该输入输出设置！");
		}
	}

	public void closeConnection() {
		if (!isOpen()) {
			return;
		}
		if (port != null) {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				System.err.print(e.getMessage());
			}
			port.close();
		}
		open.set(false);
		identifier.removePortOwnershipListener(this);
	}

	public boolean isOpen() {
		return open.get();
	}

	public BooleanProperty openProperty() {
		return open;
	}

	public void writeData(byte[] data) throws IOException {
		out.write(data);
	}

	@Override
	public void serialEvent(SerialPortEvent spe) {
		switch (spe.getEventType()) {
		case SerialPortEvent.DATA_AVAILABLE:
			handler();
			break;
		case SerialPortEvent.BI:

		}
	}

	private void handler() {
		try {
			receivePane.updateList(Reader.readData());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void ownershipChange(int type) {
		if (type == CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED) {
			System.out.println("ownershipChange.");
		}
	}

	private static class Reader {
		public static byte[] readData() throws IOException {
			byte[] newData = new byte[in.available()];
			in.read(newData);
			return newData;
		}
	}
}
