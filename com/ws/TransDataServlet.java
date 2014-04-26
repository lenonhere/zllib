package com.ws;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.ws.transport.util.TransportSchedule;

public class TransDataServlet extends HttpServlet {

	public void init() throws ServletException {
		// TODO Auto-generated method stub
		/*
		 * Timer t = new Timer(); Calendar c = Calendar.getInstance();
		 * c.set(Calendar.HOUR_OF_DAY, 2); //t.schedule(new ManageSyGXCData(),
		 * new Date(c.getTimeInMillis()), 24 * 60 * 60 * 1000);
		 * t.scheduleAtFixedRate(new ManageSyGXCData(), new
		 * Date(c.getTimeInMillis()), 24 * 60 * 60 * 1000);
		 */

		TransportSchedule schedule = new TransportSchedule();
		try {
			String xml = getServletContext().getRealPath(
					"/WEB-INF/transport.xml");
			schedule.readSchedule(xml);
			// schedule.startTask("getSyGXCData");
			// schedule.startTask("getSyStockData");
			schedule.startAllTasks();
		} catch (ServletException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		TransportSchedule schedule = new TransportSchedule();
		schedule.readSchedule();
		schedule.startTask("getSyGXCData");
		schedule.startTask("getSyStockData");
		System.out.println("Over");
	}
}
