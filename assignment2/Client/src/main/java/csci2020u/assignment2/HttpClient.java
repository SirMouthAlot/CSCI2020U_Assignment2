package csci2020u.assignment2;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpClient
{
	private Socket _socket;
	private BufferedReader _in;
	private PrintWriter _out;

	private String _dirPath;
	private List<String> _filesInDir = new ArrayList<>();

	private String _hostName;
	private int _port;

	//Both server and client must be in debug mode for debug to function properly.
	//Otherwise it will quite probably break the files that you are transferring.
	private Boolean _debugMode = false;

	public HttpClient(String host, int port, String dirPath) 
	{
		//Sets hostName to host
		_hostName = host;
		//Sets port to port
		_port = port;

		//Sets DirPath to DirPath
		//Makes sure the right slash is used!
		_dirPath = dirPath.replace("/", "\\");
	}

	public List<String> SendDirCommand()
	{
		//To store the directory list
		List<String> fileList;

		//Connects to server
		ConnectToServer();
		//Sends request for directory
		SendRequest("DIR", "");
		//Stores the directory list
		fileList = ReceiveDirResponse();
		//Disconnects from server
		DisconnectFromServer();

		//Returns the directory list
		return fileList;
	}

	public void SendDownloadCommand(String fileName)
	{
		//Connects to server
		ConnectToServer();
		//Sends request for download
		SendRequest("DOWNLOAD", fileName);
		//Downloads the file
		ReceiveDownloadResponse(fileName.replace(".\\www", _dirPath));
		//Disconnects from server
		DisconnectFromServer();
	}

	public void SendUploadCommand(String fileName)
	{
		//Connects to server
		ConnectToServer();
		//Sends request for upload
		SendRequest("UPLOAD", fileName.replace(_dirPath, ".\\www"));
		//Uploads the file
		ReceiveUploadResponse(fileName);
		//Disconnects from Server
		DisconnectFromServer();
	}

	private void ConnectToServer() 
	{
		try 
		{
			System.out.println("Connecting to server");
			_socket = new Socket(_hostName, _port);

			_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
			_out = new PrintWriter(_socket.getOutputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	private void SendRequest(String command, String fileName) 
	{
		// send the request
		System.out.println("Sending Request: " + command);
		_out.print(command + " " + fileName + "\r\n");

		//Only send this message in debug
		if (_debugMode)
		{
			_out.print("Host: " + _hostName + "\r\n\r\n");
		}

		_out.flush();
	}

	private List<String> ReceiveDirResponse()
	{
		//Creates filelist that is empty
		List<String> fileList = new ArrayList<>();

		try 
		{
			// read and print the response
			String line;
			while ((line = _in.readLine()) != null)
			{
				//Adds filenames to list
				fileList.add(line);
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		//Returns directory list
		return fileList;
	}

	private void ReceiveDownloadResponse(String fileName)
	{
		try 
		{
			//Creates new file
			String line;
			List<String> list = new ArrayList<>();

			//Read in the response information lines if in debug
			if (_debugMode)
			{
				System.out.println(_in.readLine());
				System.out.println(_in.readLine());
				System.out.println(_in.readLine());
				System.out.println(_in.readLine());
				System.out.println(_in.readLine());
				System.out.println(_in.readLine());
				System.out.println(_in.readLine());
			}

			while ((line = _in.readLine()) != null) 
			{
				list.add(line);
			}
			File newFile = new File(fileName);
			PrintWriter output = new PrintWriter(newFile);
			for(String string:list)
			{
				output.println(string);
			}
			output.flush();
			output.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	private void ReceiveUploadResponse(String fileName)
	{
		try 
		{
			File file = new File(fileName);

			if (!file.exists()) 
			{
				System.out.println("404, Not Found. The file '" + file.getName() + "' could not be located.");
			} 
			else 
			{
				byte[] content = new byte[(int) file.length()];
				FileInputStream fileIn = new FileInputStream(file);
				fileIn.read(content);
				fileIn.close();
				String message = new String(content, StandardCharsets.UTF_8);
				_out.print(message);
				_out.flush();
				System.out.println("File Sent!");
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	private void DisconnectFromServer() 
	{
		try {
			// close everything
			System.out.println("Disconnecting from server");
			_in.close();
			_out.close();
			_socket.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public List<String> GetDirList()
	{
		File dir = new File(_dirPath);

		_filesInDir.clear();

		ListDir(dir);

		return _filesInDir;
	}

	//list all files in project directory
	private void ListDir(File dir)
	{
		//Recursively go through the directories
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
			//Store the file path
			_filesInDir.add(dir.getPath());
		}
	}
}