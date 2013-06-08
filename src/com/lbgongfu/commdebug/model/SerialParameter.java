package com.lbgongfu.commdebug.model;

import javax.comm.SerialPort;

public class SerialParameter {
	private String portName;
	private int baudRate;
	private int flowControlIn;
	private int flowControlOut;
	private int databits;
	private int stopbits;
	private int parity;

	public SerialParameter() {
		this("", 9600, SerialPort.FLOWCONTROL_NONE,
				SerialPort.FLOWCONTROL_NONE, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	}

	public SerialParameter(String portName, int baudRate, int flowControlIn,
			int flowControlOut, int databits, int stopbits, int parity) {
		this.portName = portName;
		this.baudRate = baudRate;
		this.flowControlIn = flowControlIn;
		this.flowControlOut = flowControlOut;
		this.databits = databits;
		this.stopbits = stopbits;
		this.parity = parity;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	public int getFlowControlIn() {
		return flowControlIn;
	}
	
	public String getFlowControlInString() {
		return flowToString(flowControlIn);
	}

	private String flowToString(int flowControlIn2) {
		switch (flowControlIn2) {
		case SerialPort.FLOWCONTROL_NONE :
			return "None";
		case SerialPort.FLOWCONTROL_RTSCTS_IN :
			return "RTS/CTS In";
		case SerialPort.FLOWCONTROL_RTSCTS_OUT :
			return "RTS/CTS Out";
		case SerialPort.FLOWCONTROL_XONXOFF_IN :
			return "Xon/Xoff In";
		case SerialPort.FLOWCONTROL_XONXOFF_OUT :
			return "Xon/Xoff Out";
		default :
			return "None";
		}
	}

	public void setFlowControlIn(int flowControlIn) {
		this.flowControlIn = flowControlIn;
	}
	
	public void setFlowControlIn(String flowControlIn) {
		this.flowControlIn = stringToFlow(flowControlIn);
	}

	private int stringToFlow(String flowControlIn2) {
		int flow = 0;
		switch (flowControlIn2) {
		case "None" :
			flow = SerialPort.FLOWCONTROL_NONE;
			break;
		case "Xon/Xoff Out" :
			flow = SerialPort.FLOWCONTROL_XONXOFF_OUT;
			break;
		case "Xon/Xoff In" :
			flow = SerialPort.FLOWCONTROL_XONXOFF_IN;
			break;
		case "RTS/CTS In" :
			flow = SerialPort.FLOWCONTROL_RTSCTS_IN;
			break;
		case "RTS/CTS Out" :
			flow = SerialPort.FLOWCONTROL_RTSCTS_OUT;
			break;
		default :
			flow = SerialPort.FLOWCONTROL_NONE;
			break;
		}
		return flow;
	}

	public int getFlowControlOut() {
		return flowControlOut;
	}

	public void setFlowControlOut(int flowControlOut) {
		this.flowControlOut = flowControlOut;
	}
	
	public void setFlowControlOut(String flowControlOut) {
		this.flowControlOut = stringToFlow(flowControlOut);
	}

	public int getDatabits() {
		return databits;
	}

	public void setDatabits(int databits) {
		this.databits = databits;
	}

	public int getStopbits() {
		return stopbits;
	}

	public void setStopbits(int stopbits) {
		this.stopbits = stopbits;
	}

	public int getParity() {
		return parity;
	}
	
	public String getParityString() {
		switch (parity) {
		case SerialPort.PARITY_NONE :
			return "None";
		case SerialPort.PARITY_EVEN :
			return "Even";
		case SerialPort.PARITY_ODD :
			return "Odd";
		default :
			return "None";
		}
	}

	public void setParity(int parity) {
		this.parity = parity;
	}

	public void setParity(String parity) {
		this.parity = stringToParity(parity);
	}

	private int stringToParity(String parity2) {
		int parity = 0;
		switch (parity2) {
		case "None" :
			parity = SerialPort.PARITY_NONE;
			break;
		case "Even" :
			parity = SerialPort.PARITY_EVEN;
			break;
		case "Odd" :
			parity = SerialPort.PARITY_ODD;
			break;
		default :
			parity = SerialPort.PARITY_NONE;
			break;
		}
		return parity;
	}

	@Override
	public String toString() {
		return "SerialParameter [portName=" + portName + ", baudRate="
				+ baudRate + ", flowControlIn=" + flowControlIn
				+ ", flowControlOut=" + flowControlOut + ", databits="
				+ databits + ", stopbits=" + stopbits + ", parity=" + parity
				+ "]";
	}
	
}
