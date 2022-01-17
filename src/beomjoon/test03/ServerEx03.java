package beomjoon.test03;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class ServerEx03 extends Thread {
	// 다중 접속자들의 접속 정보를 저장하는 컬렉션 준비
	// 접속한 아이디의 소켓을 저장 하거나 연결 스트리밍을 저장한다.
	static Hashtable<String, PrintWriter> map = new Hashtable<String, PrintWriter>();
	BufferedReader br;
	String userId;
	
	public ServerEx03(String userId, BufferedReader br) {
		this.userId = userId;
		this.br = br;
	}

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket();
			System.out.println("서버에서 클라이언트 접속 대기 중 ...");
			
			while(true) {
				socket = serverSocket.accept(); //접속 들어올때까지 대기
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String userId = br.readLine();
				System.out.println(userId + "님이 접속 하였습니다.");
				PrintWriter pw = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
				map.put(userId, pw);
				// 서버 생성및 쓰래드 실행
				new ServerEx03(userId, br).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null) socket.close();
				if(serverSocket != null) serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	@Override
	public void run() {
		
	}

}
