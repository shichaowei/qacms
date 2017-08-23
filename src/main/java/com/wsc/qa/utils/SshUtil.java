package com.wsc.qa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
 

public class  SshUtil{
    
    /**
     * 遠程ssh執行命令，最多返回1000行
     * 
     */
	@Deprecated
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
                Thread.sleep(2000);
                while (readflag)
                {
                    String line = br.readLine();
                    System.out.println(line);
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
    
    public static void main(String[] args) {
    	remoteRunCmd("172.30.248.31", "root", "Tairantest@123098",
				"/usr/local/dubbo-quartz-0.0.1.M1-SNAPSHOT/sbin/demo.sh restart");
	}
    
    
}