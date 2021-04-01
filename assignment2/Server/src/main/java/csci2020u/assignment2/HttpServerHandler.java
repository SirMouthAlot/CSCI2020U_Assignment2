package csci2020u.assignment2;

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpServerHandler implements Runnable 
{
	private Socket socket = null;
	private BufferedReader requestInput = null;
	private DataOutputStream responseOutput = null;

	public HttpServerHandler(Socket socket) throws IOException 
	{
		this.socket = socket;
		requestInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		responseOutput = new DataOutputStream(socket.getOutputStream());
	}


	public void run() 
	{
		String line = null;
		try 
		{
			line = requestInput.readLine();
			handleRequest(line);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				requestInput.close();
				responseOutput.close();
				socket.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

	}

	public void handleRequest(String request) throws IOException 
	{
		try 
		{
			StringTokenizer tokenizer = new StringTokenizer(request);
			String command = tokenizer.nextToken();
			String fileName = "";
			if (tokenizer.hasMoreTokens())
			{
				fileName = tokenizer.nextToken();
			}

			if (fileName.length() < 1)
			{
				fileName = "images/social/amazon.png";
			}

			if (command.equalsIgnoreCase("DIR")) 
			{
				File baseDir = new File("./www/");
				ListDir(baseDir);
			}
			else if (command.equalsIgnoreCase("UPLOAD")) 
			{
				String fileDir = fileName;
				ReceiveFile(fileDir);
			}
			else if (command.equalsIgnoreCase("DOWNLOAD")) 
			{
				File fileDir = new File(fileName);
				SendFile(fileDir);
			}
			else 
			{
				sendError(405, "Method Not Allowed", "You cannot use the '" + command + "' command on this server.");
			}
		} 
		catch (NoSuchElementException e) 
		{
			e.printStackTrace();
		}

	}
//list all files in project directory
	private void ListDir(File dir)
	{
		if (dir.isDirectory())
		{
			File[] fileList = dir.listFiles();
			for(File current: fileList)
			{
				ListDir(current);
			}
		}
		else 
		{
			try 
			{
				responseOutput.writeBytes(dir.getPath() + "\n");
				responseOutput.flush();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void SendFile(File fileDir) throws IOException 
	{
		File file = fileDir;

		if (!file.exists()) 
		{
			sendError(404, "Not Found", "The file '"
					+ fileDir.getName() + "' could not be located.");
		} 
		else 
		{

			String contentType = getContentType(file.getName() + fileDir.getName());
			byte[] content = new byte[(int) file.length()];
			System.out.println("file size: " + file.length());
			FileInputStream fileIn = new FileInputStream(file);
			fileIn.read(content);
			fileIn.close();
			sendResponse("HTTP/1.1 200 Ok\r\n", contentType, content);
		}
	}
	
	private void ReceiveFile(String filename)
	{
		try 
		{
			String line;
			List<String> list = new ArrayList<>();

			//Ignore the localhost line and empty space lines
			requestInput.readLine();
			requestInput.readLine();

			while ((line = requestInput.readLine()) != null) 
			{
				System.out.println(line);
				list.add(line);
			}
			File newFile = new File(filename);
			PrintWriter output = new PrintWriter(newFile);
			for(String string:list)
			{
				output.println(string);
			}
			output.flush();
			output.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private String getContentType(String filename) 
	{
		if (filename.endsWith(".html") || filename.endsWith(".htm")) 
		{
			return "text/html";
		}
		else if (filename.endsWith(".css")) 
		{
			return "text/css";
		} 
		else if (filename.endsWith(".js")) 
		{
			return "text/javascript";
		} 
		else if (filename.endsWith(".png")) 
		{
			return "image/png";
		} 
		else if (filename.endsWith(".gif")) 
		{
			return "image/gif";
		} 
		else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) 
		{
			return "image/jpeg";
		} 
		else 
		{
			return "unknown";
		}
	}

	private void sendResponse(String responseCode, String contentType, byte[] content) throws IOException 
	{
		responseOutput.writeBytes(responseCode);

		responseOutput.writeBytes("Content-Type: " + contentType + "\r\n");
		responseOutput.writeBytes("Date: " + (new Date()) + "\r\n");
		responseOutput.writeBytes("Server: Simple-Http-Server v1.0.0\r\n");
		responseOutput.writeBytes("Content-Length: " + content.length + "\r\n");
		responseOutput.writeBytes("Connection: Close\r\n\r\n");

		responseOutput.write(content);
		responseOutput.flush();
	}

	private void sendError(int errorCode,
						   String errorMessage,
						   String description) throws IOException 
	{
		String responseCode = "HTTP/1.1 " + errorCode + " " + errorMessage + "\r\n";
		String content = "Error reading file";
		sendResponse(responseCode, "text/html", content.getBytes());
	}
}