package com.zl.base.core.fileserver.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Server {
	public static final int DEFAULT_CONNECTION = 20;
	private Map listeners = null;
	private Set connections = null;
	private ThreadGroup threadGroup = null;
	int maxConnections = 20;

	public synchronized void SetMaxConnection(int paramInt) {
		this.maxConnections = paramInt;
	}

	public synchronized int GetMacConnection() {
		return this.maxConnections;
	}

	public Server() {
		this.threadGroup = new ThreadGroup(Server.class.getName());
		this.listeners = new HashMap();
		this.connections = new HashSet(this.maxConnections);
	}

	public Server(int paramInt) {
		this.maxConnections = paramInt;
		this.threadGroup = new ThreadGroup(Server.class.getName());
		this.listeners = new HashMap();
		this.connections = new HashSet(this.maxConnections);
	}

	public synchronized void addService(Service paramService, int paramInt)
			throws IOException {
		Integer localInteger = new Integer(paramInt);
		if (this.listeners.get(localInteger) != null)
			throw new IllegalArgumentException("Port " + paramInt
					+ " already in use.");
		Listener localListener = new Listener(this.threadGroup, paramInt,
				paramService);
		this.listeners.put(localInteger, localListener);
		localListener.start();
	}

	public synchronized void removeService(int paramInt) {
		Integer localInteger = new Integer(paramInt);
		Listener localListener = (Listener) this.listeners.get(localInteger);
		if (localListener == null)
			return;
		localListener.End();
		this.listeners.remove(localInteger);
	}

	protected synchronized void addConnection(Socket paramSocket,
			Service paramService) {
		if (this.connections.size() >= this.maxConnections) {
			try {
				PrintWriter localPrintWriter = new PrintWriter(
						paramSocket.getOutputStream());
				localPrintWriter
						.print("Connection refused. the server is busy, please try again later.\n");
				localPrintWriter.flush();
				paramSocket.close();
			} catch (IOException localIOException) {
			}
		} else {
			Connection localConnection = new Connection(paramSocket,
					paramService);
			this.connections.add(localConnection);
			localConnection.start();
		}
	}

	protected synchronized void endConnection(Connection paramConnection) {
		this.connections.remove(paramConnection);
	}

	public synchronized void dispalyStatus(PrintWriter paramPrintWriter) {
		Iterator localIterator = this.listeners.keySet().iterator();
		while (localIterator.hasNext()) {
			Integer localInteger = (Integer) localIterator.next();
			Listener localListener = (Listener) this.listeners
					.get(localInteger);
			paramPrintWriter.print("SERVICE "
					+ localListener.service.getClass().getName() + " ON PORT "
					+ localInteger + "\n");
		}
	}

	public class Connection extends Thread {
		private Socket socket = null;
		private Service service = null;

		public Connection(Socket socket1, Service service1) {
			socket = null;
			service = null;
			socket = socket1;
			service = service1;
		}

		// public Connection(Socket paramService, Service arg3)
		// {
		// this.socket = paramService;
		// Object localObject;
		// this.service = localObject;
		// }

		public void run() {
			try {
				InputStream localInputStream = this.socket.getInputStream();
				OutputStream localOutputStream = this.socket.getOutputStream();
				this.service.service(localInputStream, localOutputStream);
				localInputStream.close();
				localOutputStream.close();
			} catch (Exception localException1) {
				System.out.println(localException1.getMessage());
			} finally {
				try {
					this.socket.close();
				} catch (Exception localException2) {
				}
				Server.this.endConnection(this);
			}
		}
	}

	public class Listener extends Thread {
		private ServerSocket socket = null;
		private int port;
		private Service service = null;
		private volatile boolean stop = false;

		public Listener(ThreadGroup threadgroup, int i, Service service1)
				throws IOException {
			super(threadgroup, "Listener:" + i);
			socket = null;
			service = null;
			stop = false;
			socket = new ServerSocket(i);
			port = i;
			service = service1;
		}

		// public Listener(ThreadGroup paramInt, int paramService, Service arg4)
		// throws IOException
		// {
		// super("Listener:" + paramService);
		// this.socket = new ServerSocket(paramService);
		// this.port = paramService;
		// Object localObject;
		// this.service = localObject;
		// }

		public void run() {
			while (!this.stop)
				try {
					Socket localSocket = this.socket.accept();
					Server.this.addConnection(localSocket, this.service);
				} catch (InterruptedIOException localInterruptedIOException) {
				} catch (IOException localIOException) {
				}
		}

		public void End() {
			this.stop = true;
			interrupt();
			try {
				this.socket.close();
			} catch (IOException localIOException) {
			}
		}
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.fileserver.socket.Server JD-Core Version: 0.6.1
 */