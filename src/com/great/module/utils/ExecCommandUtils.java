/**
 * @(#) ExecCommand.java Created on 2012-5-7
 *
 * Copyright (c) 2012 Aspire. All Rights Reserved
 */
package com.great.module.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.util.Log;

/**
 * The class <code>ExecCommand</code>
 * 
 * @author zhenghui
 * @version 1.0
 */
public class ExecCommandUtils {
    public final static String TAG = "ExecCommand";
    private static final int Length_ProcStat = 9;

    public static void execCommandsAsRoot(String[] cmds) throws Exception {
        execCommands(Runtime.getRuntime().exec("su"), cmds);
    }

    public static String execCommandAsRoot(String cmd) throws Exception {
        return execCommand(Runtime.getRuntime().exec("su"), cmd);
//    	execCommandNoResul(Runtime.getRuntime().exec("su"), cmd);
    }

    public static void killProcess(String processName) throws Exception {
        String result = execCommand(Runtime.getRuntime().exec("top -n 1"));
        String[] rows = result.split("[\n]+");
        String[] columns = null;
        for (String tempString : rows) {
            if (tempString.indexOf("PID") == -1) {
                tempString = tempString.trim();
                columns = tempString.split("[ ]+");
                if (columns.length == Length_ProcStat) {
                    if (columns[8].contains(processName)) {
                        Log.d(TAG, "processName: " + processName);
                        execCommandAsRoot("kill " + columns[0]);
                    }
                }
                if (columns.length == 10) {
                    if (columns[9].contains(processName)) {
                        Log.d(TAG, "processName: " + processName);
                        execCommandAsRoot("kill " + columns[0]);
                    }
                }
                
                
                if (columns.length == 8) {
                    if (columns[7].contains(processName)) {
                        Log.d(TAG, "processName: " + processName);
                        execCommandAsRoot("kill " + columns[0]);
                    }
                }
            }
        }
    }
    
    public static String getProcessPID(String processName)  throws Exception{
    	String result = execCommand(Runtime.getRuntime().exec("top -n 1"));
        String[] rows = result.split("[\n]+");
        String[] columns = null;
        String Pid = null;
        for (String tempString : rows) {
        	if (tempString.indexOf("PID") == -1) {
        		tempString = tempString.trim();
        		columns = tempString.split("[ ]+");
        		if (columns[columns.length -1].contains(processName)) {
        			Pid = columns[0];
        			Pid = Pid.trim();
        			break;
                }
        	}
        }
    	return Pid;
    }
    
    public static void killStrongProcess(String processName) throws Exception {
        String result = execCommand(Runtime.getRuntime().exec("top -n 1"));
        String[] rows = result.split("[\n]+");
        String[] columns = null;
        for (String tempString : rows) {
            if (tempString.indexOf("PID") == -1) {
                tempString = tempString.trim();
                columns = tempString.split("[ ]+");
                if (columns.length == Length_ProcStat) {
                    if (columns[8].contains(processName)) {
                        Log.d(TAG, "processName: " + processName);
                        execCommandAsRoot("kill -9 " + columns[0]);
                    }
                }
                if (columns.length == 10) {
                    if (columns[9].contains(processName)) {
                        Log.d(TAG, "processName: " + processName);
                        execCommandAsRoot("kill -9 " + columns[0]);
                    }
                }
                
                if (columns.length == 8) {
                    if (columns[7].contains(processName)) {
                        Log.d(TAG, "processName: " + processName);
                        execCommandAsRoot("kill -9 " + columns[0]);
                    }
                }
            }
        }
    }

    public static void execCommands(Process proc, String[] cmds)
            throws Exception {
        DataOutputStream os = null;
        BufferedReader br = null;
        try {
            os = new DataOutputStream(proc.getOutputStream());
            for (String cmd : cmds) {
                Log.i(TAG, "Exec shell command: " + cmd);
                os.writeBytes(cmd + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
         
            //htc802t,huawei9510 需要以下代码，但是可能导致其它手机卡死
            br = new BufferedReader(
                    new InputStreamReader(proc.getErrorStream()));
            String line = null;

            if (proc.waitFor() != 0) {
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                Log.i(TAG, "InputStream: " + sb.toString());
                throw new Exception(sb.toString());
            }
            //end
            
            Log.i(TAG, "Exec command Success.");
        } finally {
        	Log.i(TAG, "proc.destroy()");
            proc.destroy();
        }
    }
    
    public static void execCommandNoResul(Process proc, String cmd) throws Exception{
    	proc.getOutputStream();
    	Log.i(TAG, "Exec shell command: " + cmd); 
        DataOutputStream os = null;    
        try {
            os = new DataOutputStream(proc.getOutputStream());
            os.writeBytes("\n");
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            os.close();           
            proc.getInputStream().close();
            proc.getOutputStream().close();
            proc.getErrorStream().close();
        } finally {
            proc.destroy();
        	
        }
    }

/*
    public static String execCommand(Process proc, String cmd) throws Exception {
    	proc.getOutputStream();
    	Log.i(TAG, "Exec shell command: " + cmd); 
        DataOutputStream os = null;
        BufferedReader brErr = null;
        BufferedReader br = null;
      
        try {
            os = new DataOutputStream(proc.getOutputStream());
            os.writeBytes("\n");
//            Log.d(TAG, "0000000000");
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            os.close();
//            Log.d(TAG, "111111111");
            brErr = new BufferedReader(new InputStreamReader(
                    proc.getErrorStream()));
            Log.e(TAG, "read brErr="+brErr);
            br = new BufferedReader(
                    new InputStreamReader(proc.getInputStream()));
            
            String line = null;


          //htc802t,huawei9510 需要以下代码，但是可能导致其它手机卡死,暂时先打开代码
            if (proc.waitFor() != 0) {
                StringBuilder sbErr = new StringBuilder();
                while ((line = brErr.readLine()) != null) {
                    sbErr.append(line).append("\n");
                }
//                Log.d(TAG, "sbErr.toString = "+sbErr.toString());
            }

            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
            	System.out.print(line+"\n");
                sb.append(line).append("\n");
            }
//            Log.d(TAG, "sb.toString = "+sb.toString());
            
            proc.getInputStream().close();
            proc.getOutputStream().close();
            proc.getErrorStream().close();
            
            return sb.toString();
//            Log.i(TAG, "Exec command Success.");
//            return "";
        } finally {
            proc.destroy();
        	
        }
    }
*/
    public static String execCommand(Process proc, String cmd) throws Exception{
    	proc.getOutputStream();
    	Log.i(TAG, "Exec shell command: " + cmd); 
        DataOutputStream os = null;
        BufferedReader brErr = null;
        BufferedReader br = null;
        InputStream input=null;
        OutputStream output=null;
        InputStream err=null;
        
      
        try {
        	output=proc.getOutputStream();
            os = new DataOutputStream(output);
            os.writeBytes("\n");
//            Log.d(TAG, "0000000000");
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
//            os.close();
//            os=null;
            safeClose(os);
            err=proc.getErrorStream();
//            Log.d(TAG, "111111111");
            brErr = new BufferedReader(new InputStreamReader(err));
            Log.d(TAG, "read brErr="+brErr);
            input=proc.getInputStream();
            br = new BufferedReader( new InputStreamReader(input));
            
            String line = null;

            
         //    由于华为机型出现安装成功waitfor值为非0，所以注释这块代码
            
          //htc802t,huawei9510 需要以下代码，但是可能导致其它手机卡死,暂时先打开代码
            if (proc.waitFor() != 0) {
                StringBuilder sbErr = new StringBuilder();
                while ((line = brErr.readLine()) != null) {
                    sbErr.append(line).append("\n");
                }
//                Log.d(TAG, "sbErr.toString = "+sbErr.toString());
            }

            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
            	Log.e("ExecCommand", line+"\n");
            	System.out.print(line+"\n");
                sb.append(line).append("\n");
            }
//            Log.d(TAG, "sb.toString = "+sb.toString());       
//            proc.getInputStream().close();
//            proc.getOutputStream().close();
//            proc.getErrorStream().close();
//            brErr.close();
//            brErr=null;
            safeClose(input);
            safeClose(output);
            safeClose(err);
            safeClose(brErr);
            Log.e(TAG, "Exec command Success.");
            return sb.toString();
        

        }
        catch(Exception io)
        {
        	Log.e("ExecCommand111", io.getMessage(), io);
        	  Log.e(TAG, "fail  "+cmd);
        	throw new Exception();
        }finally {
            proc.destroy();
           
        	
        }
    }
   
    public static String execCommandNoDestroy(Process proc, String cmd) throws Exception {
    	proc.getOutputStream();
    	Log.i(TAG, "Exec shell command: " + cmd); 
        DataOutputStream os = null;
        BufferedReader brErr = null;
        BufferedReader br = null;
      
        try {
            os = new DataOutputStream(proc.getOutputStream());
            os.writeBytes("\n");
//            Log.d(TAG, "0000000000");
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            os.close();
//            Log.d(TAG, "111111111");
            brErr = new BufferedReader(new InputStreamReader(
                    proc.getErrorStream()));
            Log.d(TAG, "read brErr="+brErr);
            br = new BufferedReader(
                    new InputStreamReader(proc.getInputStream()));
            Log.d(TAG, "proc.getInputStream ="+br.readLine());
            String line = null;

            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
            	System.out.print(line+"\n");
                sb.append(line).append("\n");
            }
            Log.d(TAG, "sb.toString = "+sb.toString());
            
            proc.getInputStream().close();
            proc.getOutputStream().close();
            proc.getErrorStream().close();
            
            return sb.toString();
        } finally {
//            proc.destroy();
        	
        }
    }

    public static String execCommand(final Process proc) throws Exception {
        Log.i(TAG, "Exec shell command: ");
        DataOutputStream os = null;
        BufferedReader brErr = null;
        BufferedReader br = null;
        try {
            os = new DataOutputStream(proc.getOutputStream());
            os.flush();

            brErr = new BufferedReader(new InputStreamReader(
                    proc.getErrorStream()));
            br = new BufferedReader(
                    new InputStreamReader(proc.getInputStream()));
            String line = null;

            if (proc.waitFor() != 0) {
                StringBuilder sbErr = new StringBuilder();
                while ((line = brErr.readLine()) != null) {
                    sbErr.append(line).append("\n");
                }
                throw new Exception(sbErr.toString());
            }

            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } finally {
            try {
                proc.destroy();
            } catch (Exception e) {
                Log.e(TAG, "proc.destroy Error: " + e.getMessage());
            }
        }
    }
    
    public static void safeClose(Closeable io) {
    	try 
    	{
    		if (io != null) 
    		{
    			io.close();
    		}
    	}
    	catch (IOException e) {
    	}
    		io = null;
    }
    
    
  
}
