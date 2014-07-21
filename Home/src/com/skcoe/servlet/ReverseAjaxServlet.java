package com.skcoe.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.AsyncContext;

import org.json.JSONArray;

import java.util.Date;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Servlet implementation class ReverseAjaxServlet
 */
@WebServlet("/ReverseAjaxServlet")
public class ReverseAjaxServlet extends HttpServlet {

       
    /**
	 * 
	 */
	private static final long serialVersionUID = 8029239770482477563L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ReverseAjaxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }



    private final Queue<AsyncContext> asyncContexts = new ConcurrentLinkedQueue<AsyncContext>();

    private final Random random = new Random();
    private final Thread generator = new Thread("Event generator") {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(random.nextInt(5000));
                    while (!asyncContexts.isEmpty()) {
                        AsyncContext asyncContext = asyncContexts.poll();
                        HttpServletResponse peer = (HttpServletResponse) asyncContext.getResponse();
                        peer.getWriter().write(new JSONArray().put("At " + new Date()).toString());
                        peer.setStatus(HttpServletResponse.SC_OK);
                        peer.setContentType("application/json");
                        asyncContext.complete();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    };

    @Override
    public void init() throws ServletException {
        generator.start();
    }

    @Override
    public void destroy() {
        generator.interrupt();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(0);
        asyncContexts.offer(asyncContext);
    }

}
