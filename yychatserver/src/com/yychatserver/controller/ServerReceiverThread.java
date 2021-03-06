package com.yychatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.yychat.model.Message;

public class ServerReceiverThread extends Thread{//代码复用
	Socket s;
	public ServerReceiverThread(Socket s){//s是与发送者相对应的服务器Socket对象
		this.s=s;
	}
	
	public void run(){
		ObjectInputStream ois;
		ObjectOutputStream oos;
		Message mess;
		while (true) {
			try {
				ois = new ObjectInputStream(s.getInputStream());
				mess=(Message)ois.readObject();//接收聊天信息，阻塞
				System.out.println(mess.getSender()+"对"+mess.getReceiver()+"说："+mess.getContent());
				
				if(mess.getMessageType().equals(Message.message_Common)){
					Socket s1=(Socket)StartServer.hmSocket.get(mess.getReceiver());//得到了与接收者相对应的服务器Socket对象
					oos=new ObjectOutputStream(s1.getOutputStream());
					oos.writeObject(mess);//转发聊天信息
				}
				
				//第2步：服务器接收到该请求后发送在线好友信息(类型：message_OnlineFriend)
				if(mess.getMessageType().equals(Message.message_RequestOnlineFriend)){
					
				}
				
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
}
