package com.igustudio.tmanager.util;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 */
public class RuntimeInfoAccessorBean {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static RuntimeInformation getRuntimeInformation() throws Exception {
		MBeanServer mBeanServer =  ManagementFactory.getPlatformMBeanServer();
		RuntimeInformation ri = new RuntimeInformation();

		try {
			ObjectName runtimeOName = new ObjectName("java.lang:type=Runtime");
			ri.setStartTime(JmxTools.getLongAttr(mBeanServer, runtimeOName,
					"StartTime"));
			ri.setUptime(JmxTools.getLongAttr(mBeanServer, runtimeOName,
					"Uptime"));
			ri.setVmVendor(JmxTools.getStringAttr(mBeanServer, runtimeOName,
					"VmVendor"));

			ObjectName osOName = new ObjectName(
					"java.lang:type=OperatingSystem");
			ri.setOsName(JmxTools.getStringAttr(mBeanServer, osOName, "Name"));
			ri.setOsVersion(JmxTools.getStringAttr(mBeanServer, osOName,
					"Version"));

			if (!ri.getVmVendor().startsWith("IBM Corporation")) {
				ri.setTotalPhysicalMemorySize(JmxTools.getLongAttr(mBeanServer,
						osOName, "TotalPhysicalMemorySize"));
				ri.setCommittedVirtualMemorySize(JmxTools.getLongAttr(
						mBeanServer, osOName, "CommittedVirtualMemorySize"));
				ri.setFreePhysicalMemorySize(JmxTools.getLongAttr(mBeanServer,
						osOName, "FreePhysicalMemorySize"));
				ri.setFreeSwapSpaceSize(JmxTools.getLongAttr(mBeanServer,
						osOName, "FreeSwapSpaceSize"));
				ri.setTotalSwapSpaceSize(JmxTools.getLongAttr(mBeanServer,
						osOName, "TotalSwapSpaceSize"));
				ri.setProcessCpuTime(JmxTools.getLongAttr(mBeanServer, osOName,
						"ProcessCpuTime"));
			} else {
				ri.setTotalPhysicalMemorySize(JmxTools.getLongAttr(mBeanServer,
						osOName, "TotalPhysicalMemory"));
			}

			return ri;
		} catch (Exception e) {
			
//			AppLog.getLogger().warn("OS information is unavailable, " + e, e);
			return null;
		}
	}
}
