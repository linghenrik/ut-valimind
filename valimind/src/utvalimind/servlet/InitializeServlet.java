package utvalimind.servlet;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import utvalimind.helpers.Election;
public class InitializeServlet {

	private static final long serialVersionUID = -2498064273169045029L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		Random rand = new Random();
		String channelKey = "" + rand.nextInt();
		String token = channelService.createChannel(channelKey);
		String type = req.getParameter("type");

		if (type.equals("general")) {
			Election.generalResultsChannelKeys.add(channelKey);
		} else {
			Election.areaOrPartyResultsChannelKeys.add(channelKey);
		}

		PrintWriter out = resp.getWriter();
	    resp.setContentType("text/plain");
	    out.write(token);
	    out.flush();
}
}
