package com.customia.jdbctest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mysql.jdbc.Driver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        final String server = "164.132.199.202:3306";//"192.168.1.9:3306";
        final String db = "picking";
        final String user = "root"; // username of oracle database
        final String pwd = "pickingroot"; //"Cus4sql*"; // password of oracle database
        final String url = "jdbc:mysql://"+server+"/"+db;
        final String url_full = url+"?user="+user+"&password="+pwd;

//        String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + db; // connectOracle is the data
//        // source name
//        Connection con = null;
//        ServerSocket serverSocket = null;
//        Socket socket = null;
//        DataInputStream dataInputStream = null;
//        DataOutputStream dataOutputStream = null;
//
//        String driver = "oracle.jdbc.driver.OracleDriver"; //
//
//        try {
//            Class.forName(driver);// for loading the jdbc driver
//
//            System.out.println("JDBC Driver loaded");
//
//            con = DriverManager.getConnection(url, user, pwd);// for
//            // establishing
//            // connection
//            // with database
//            Statement stmt = con.createStatement();
//
//            serverSocket = new ServerSocket(8888);
//            System.out.println("Listening :8888");
//
//            while (true) {
//                try {
//
//                    socket = serverSocket.accept();
//                    System.out.println("Connection Created");
//                    dataInputStream = new DataInputStream(
//                            socket.getInputStream());
//                    dataOutputStream = new DataOutputStream(
//                            socket.getOutputStream());
//                    System.out.println("ip: " + socket.getInetAddress());
//                    // System.out.println("message: " +
//                    // dataInputStream.readUTF());
//
//                    ResultSet res=stmt.executeQuery("select * from person");
//                    while(res.next()){
//                        System.out.println(res.getString(1));
//                    }
//
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//                if (dataInputStream != null) {
//                    try {
//                        dataInputStream.close();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//
//                if (dataOutputStream != null) {
//                    try {
//                        dataOutputStream.close();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    
    
        final Connection[] conn = new Connection[1];
        Thread sqlThread = new Thread() {
            public void run() {
                try {
                    com.mysql.jdbc.Driver o = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Properties pro = new Properties();
                    pro.put("user", user);
                    pro.put("password", pwd);
                    Connection connect = o.connect(url, pro);
                    // "jdbc:mysql://IP:PUERTO/DB", "USER", "PASSWORD");
                    // Si estás utilizando el emulador de android y tenes el mysql en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
//                    conn[0] = DriverManager.getConnection("jdbc:mysql://"+server+"/"+db+"?user="+user+"&password="+pwd);
                    conn[0] = DriverManager.getConnection(url, user, pwd);

                    //En el stsql se puede agregar cualquier consulta SQL deseada.
                    String stsql = "Select * FROM fos_user";
                    Statement st = conn[0].createStatement();
                    ResultSet rs = st.executeQuery(stsql);
                    rs.next();
                    System.out.println( rs.getString(1) );
                    conn[0].close();
                } catch (SQLException se) {
                    System.out.println("oops! No se puede conectar. Error: " + se.toString());
                }
                catch (ClassNotFoundException e) {
                    System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        };

        sqlThread.run();
        
    }
}
