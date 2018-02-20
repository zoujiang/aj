package com.frame.ifpr.util;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;















import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
/**
 *create by datao.wang 2013-12-20-下午5:10:58	
 */
public class SSHClient {

    Session session = null;
    Channel channel = null;

    private String server;
	private int port;
	private String username;
	private String password;
	private int connectTimeout;
	
	
    public Channel getChannel(String shell) throws JSchException {
        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(username, server, port); // 根据用户名，主机ip，端口获取一个Session对象
        if (password != null) {
            session.setPassword(password); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(connectTimeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        //sftp :SFTP通道   exec:命令执行
        channel = session.openChannel(shell); // 
//        channel.connect(); // 建立SFTP通道的连接
        return channel;
    }

    public void disconnect() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
    
    /**
     * 读取远程文件
     * create by datao.wang  2013-12-20
     * @param remotefold
     * @param remotefile
     * @return
     * @throws Exception
     */
    public InputStream readFile(String remotefold, String remotefile) throws Exception {
		InputStream in = null;
		ChannelSftp sftp=(ChannelSftp) this.getChannel("sftp");
		if (StringUtils.isNotBlank(remotefold)) {
			sftp.cd(remotefold);
		}
		in=sftp.get(remotefile);
		return in;
	}
    
    public String exec(String cmd){
    	StringBuffer buf = new StringBuffer();  
    	try {
	    	  ChannelExec channelExec = (ChannelExec)this.getChannel("exec");  
	          channelExec.setCommand(cmd);  
	          channelExec.setInputStream(null);  
	          channelExec.connect();  
	          InputStream in = channelExec.getInputStream();  
	          BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("utf-8")));  
	          String line="";
	          while ((line = reader.readLine()) != null){  
	              buf.append(line);
	          }  
	          reader.close();  
	          channelExec.disconnect();  
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return  buf.toString();
    }

    public void exeShell( String cmd)  throws Exception{  
        ChannelShell channelShell = (ChannelShell)this.getChannel( "shell" );  
        PipedInputStream pipeIn = new PipedInputStream();  
        PipedOutputStream pipeOut = new PipedOutputStream( pipeIn );  
        FileOutputStream fileOut = new FileOutputStream("G:\\workspace\\12.txt" );  
        channelShell.setInputStream( pipeIn );  
        channelShell.setOutputStream( fileOut );  
        channelShell.connect();  
        pipeOut.write(cmd.getBytes());  
        Thread.sleep(3000);  
        pipeOut.close();  
        pipeIn.close();  
        fileOut.close();  
        channelShell.disconnect();  
    }  
    
    
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getConnectTimeout() {
		if (connectTimeout < 0)
			connectTimeout = 0;
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
    
}

