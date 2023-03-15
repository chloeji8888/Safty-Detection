package com.jszy.task;

import java.util.Date;

import org.jeecgframework.core.util.ResourceUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import com.jszy.util.DateFormatInfo;
import com.jszy.util.Sync12350Util;

/***
 * 
 * @author zhj
 *
 */
@Service("syncTokenTask")
public class SyncTokenTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("开始执行 每隔20分钟 获取token。开始时间：" + DateFormatInfo.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		queryToken();
		System.out.println("结束执行 每隔20分钟 获取token。结束时间：" + DateFormatInfo.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 执行定时获取token
	 */
	private void queryToken() {
		try {
			// 放入全局缓存
			ResourceUtil.token12350 = Sync12350Util.getToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
