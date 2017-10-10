package com.wsc.qa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.wsc.qa.constants.ServerInfo;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;


public class  SshUtil{

    /**
     * 遠程ssh執行命令，最多返回1000行
     *
     */
    public static String remoteRunCmd(String hostname,String username,String password,String  cmd)
    {

            Connection conn = new Connection(hostname);
            Session sess = null;

            try
            {
                conn.connect();
                boolean isAuthenticated = conn.authenticateWithPassword(username, password);
                if (isAuthenticated == false)
                        throw new IOException("Authentication failed.");

                sess = conn.openSession();
                sess.execCommand( cmd);
                InputStream stdout =    sess.getStdout() ;
                BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
                StringBuilder sb = new StringBuilder();
                int linenum=0;
                while (true)
                {
                    String line = br.readLine();
                    if (line == null || linenum>1000)
                        break;
                    sb.append(line);
                    sb.append('\n');
                    linenum++;
                }
               return sb.toString();
            }catch (Exception e){
                return "false";
            }finally{
                sess.close();
                conn.close();
            }
        }


    /**
     * 遠程ssh執行命令，最多返回1000行
     * mock的shell命令后台运行没有返回值
     *
     */
    public static String remoteRunCmd(String hostname,String username,String password,String  cmd,boolean readflag )
    {

            Connection conn = new Connection(hostname);
            Session sess = null;

            try
            {
                conn.connect();
                boolean isAuthenticated = conn.authenticateWithPassword(username, password);
                if (isAuthenticated == false)
                        throw new IOException("Authentication failed.");

                sess = conn.openSession();
                sess.execCommand( cmd);
                InputStream stdout =    sess.getStdout() ;
                BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
                StringBuilder sb = new StringBuilder();
                int linenum=0;
                if (readflag)
                {
                    while (true) {
						String line = br.readLine();
						if (line == null || linenum > 1000)
							break;
						sb.append(line);
						sb.append('\n');
						linenum++;
					}
                }else {
					Thread.sleep(5000);
				}
               return sb.toString();
            }catch (Exception e){
                return "false";
            }finally{
                sess.close();
                conn.close();
            }
        }

    public static void main(String[] args) {
//    	remoteRunCmd("10.200.141.38", "root", "Tairan@2017",
//				"/usr/local/dubbo-quartz-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart",true);
    	SshUtil.remoteRunCmd(ServerInfo.quartzIpadd, ServerInfo.sshname, ServerInfo.sshpwd,
				ServerInfo.restartquartzCmd,false);
	}


}