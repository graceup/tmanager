package com.igustudio.tmanager.model;

public class MonitorModel {

	
	private final static int MB = 1024 * 1024;

	/**
	 * 操作系统名称
	 */
	String osName;

	/**
	 * 操作系统运行时长，单位秒
	 */
	long osUptime;

	/**
	 * RAM物理内存总量，单位MB
	 */
	long ramTotal;

	/**
	 * RAM物理内存空闲总量，单位MB
	 */
	long ramFree;

	/**
	 * SWAP内存总量，单位MB
	 */
	long swapTotal;

	/**
	 * SWAP内存空闲总量，单位MB
	 */
	long swapFree;

	/**
	 * CPU的核数
	 */
	int cpuNum;

	/**
	 * CPU频率
	 */
	long cpuFrequency;

	/**
	 * CPU的使用率
	 */
	float cpuUsage;

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public long getOsUptime() {
		return osUptime;
	}

	public void setOsUptime(long osUptime) {
		this.osUptime = osUptime;
	}

	public long getRamTotal() {
		return ramTotal;
	}

	public void setRamTotal(long ramTotal) {
		this.ramTotal = ramTotal;
	}

	public long getRamFree() {
		return ramFree;
	}

	public void setRamFree(long ramFree) {
		this.ramFree = ramFree;
	}

	public long getSwapTotal() {
		return swapTotal;
	}

	public void setSwapTotal(long swapTotal) {
		this.swapTotal = swapTotal;
	}

	public long getSwapFree() {
		return swapFree;
	}

	public void setSwapFree(long swapFree) {
		this.swapFree = swapFree;
	}

	public int getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(int cpuNum) {
		this.cpuNum = cpuNum;
	}

	public long getCpuFrequency() {
		return cpuFrequency;
	}

	public void setCpuFrequency(long cpuFrequency) {
		this.cpuFrequency = cpuFrequency;
	}

	public float getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(float cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	
	private static String secsInDaysAndHours(long seconds) {
		long days = seconds / (60 * 60 * 24);
		long hours = (seconds / (60 * 60)) - (days * 24);
		return days + " days " + hours + " hours";
	}

	@Override
	public String toString() {
		return toXmlString();
	}
	
	public String toReadableString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n\tosName:");
		builder.append(osName);
		builder.append("\n\tosUptime:");
		builder.append(secsInDaysAndHours(osUptime));
		builder.append("\n\tramTotal:");
		builder.append(ramTotal/MB + "MB");
		builder.append("\n\tramFree:");
		builder.append(ramFree/MB + "MB");
		builder.append("\n\tswapTotal:");
		builder.append(swapTotal/MB + "MB");
		builder.append("\n\tswapFree:");
		builder.append(swapFree/MB + "MB");
		builder.append("\n\tcpuNum:");
		builder.append(cpuNum);
		builder.append("\n\tcpuFrequency:");
		builder.append(cpuFrequency/(1000 * 1000) + " MHz");
		builder.append("\n\tcpuUsage:");
		builder.append(cpuUsage);
		return builder.toString();
	}

	public String toJsonString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{osName:");
		builder.append(osName);
		builder.append(", osUptime:");
		builder.append(osUptime);
		builder.append(", ramTotal:");
		builder.append(ramTotal);
		builder.append(", ramFree:");
		builder.append(ramFree);
		builder.append(", swapTotal:");
		builder.append(swapTotal);
		builder.append(", swapFree:");
		builder.append(swapFree);
		builder.append(", cpuNum:");
		builder.append(cpuNum);
		builder.append(", cpuFrequency:");
		builder.append(cpuFrequency);
		builder.append(", cpuUsage:");
		builder.append(cpuUsage);
		builder.append("}");
		return builder.toString();
	}

	public String toXmlString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version='1.0' encoding='UTF-8'?>");
		builder.append("<system>");
		builder.append("	<osName>").append(osName).append("</osName>");
		builder.append("	<osUptime>").append(osUptime).append("</osUptime>");
		builder.append("	<ramTotal>").append(ramTotal).append("</ramTotal>");
		builder.append("	<ramFree>").append(ramFree).append("</ramFree>");
		builder.append("	<swapTotal>").append(swapTotal).append("</swapTotal>");
		builder.append("	<swapFree>").append(swapFree).append("</swapFree>");
		builder.append("	<cpuNum>").append(cpuNum).append("</cpuNum>");
		builder.append("	<cpuFrequency>").append(cpuFrequency).append("</cpuFrequency>");
		builder.append("	<cpuUsage>").append(cpuUsage).append("</cpuUsage>");
		builder.append("</system>");
		return builder.toString();
	}



}
