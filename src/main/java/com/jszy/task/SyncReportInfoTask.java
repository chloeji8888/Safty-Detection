package com.jszy.task;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jszy.safetyDetection.sd_report_info.entity.SdReportInfoEntity;
import com.jszy.safetyDetection.sd_report_info.service.SdReportInfoServiceI;
import com.jszy.util.DateFormatInfo;

/***
 * 
 * @author zhj
 *
 */
@Service("syncReportInfoTask")
public class SyncReportInfoTask implements Job {
	@Autowired
	private SdReportInfoServiceI sdReportInfoService;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println(
				"开始执行  将未成功同步的订单同步至12350。开始时间：" + DateFormatInfo.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		syncReports();
		System.out
				.println("结束执行 将未成功同步的订单同步至12350。结束时间：" + DateFormatInfo.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}

	private void syncReports() {
		List<SdReportInfoEntity> reports = sdReportInfoService.findByProperty(SdReportInfoEntity.class, "isSync", "0");
		if (reports != null && reports.size() > 0) {
			for (SdReportInfoEntity report : reports) {
				try {
					sdReportInfoService.syncReport(report);
				} catch (Exception e) {
					continue;
				}
			}
		}
	}

}
