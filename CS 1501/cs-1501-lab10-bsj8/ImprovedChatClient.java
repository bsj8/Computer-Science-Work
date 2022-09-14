import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/** Primitive chat client.
 * This client connects to a server so that messages can be typed and forwarded
 * to all other clients.  Try it out in conjunction with ImprovedChatServer.java.
 * You will need to modify / update this program to incorporate the secure
 * elements as specified in the Assignment description.  Note that the PORT used
 * below is not the one required in the assignment -- for your SecureChatClient
 * be sure to change the port that so that it matches the port specified for the
 * secure  server.
 * Adapted from Dr. John Ramirez's CS 1501 Assignment 4
 */
public class ImprovedChatClient extends JFrame implements Runnable, ActionListener {

    public static final int PORT = 5678;

    BufferedReader myReader;
    PrintWriter myWriter;
    JTextArea outputArea;
    JLabel prompt;
    JTextField inputField;
    String myName, serverName;
	Socket connection;

    public ImprovedChatClient ()
    {
        try {

        myName = JOptionPane.showInputDialog(this, "Enter your user name: ");
        serverName = JOptionPane.showInputDialog(this, "Enter the server name: ");
        InetAddress addr =
                InetAddress.getByName(serverName);
        connection = new Socket(addr, PORT);   // Connect to server with new
                                               // Socket
        myReader =
             new BufferedReader(
                 new InputStreamReader(
                     connection.getInputStream()));   // Get Reader and Writer

        myWriter =
             new PrintWriter(
                 new BufferedWriter(
                     new OutputStreamWriter(connection.getOutputStream())), true);

        //TODO: Add handshaking. Please check the TODO comment in the server code.
        String handshake = myReader.readLine();
        if(handshake.equals("hello")){
          myWriter.println("hello");
          myWriter.println(myName);   // Send name to Server.  Server will need
                                      // this to announce sign-on and sign-off
                                      // of clients

          this.setTitle(myName);      // Set title to identify chatter

          Box b = Box.createHorizontalBox();  // Set up graphical environment for
          outputArea = new JTextArea(8, 30);  // user
          outputArea.setEditable(false);
          b.add(new JScrollPane(outputArea));

          outputArea.append("Welcome to the Chat Group, " + myName + "\n");

          inputField = new JTextField("");  // This is where user will type input
          inputField.addActionListener(this);

          prompt = new JLabel("Type your messages below:");
          Container c = getContentPane();

          c.add(b, BorderLayout.NORTH);
          c.add(prompt, BorderLayout.CENTER);
          c.add(inputField, BorderLayout.SOUTH);

          Thread outputThread = new Thread(this);  // Thread is to receive strings
          outputThread.start();                    // from Server

          addWindowListener(
                new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    { myWriter.println("CLIENT CLOSING");
                      System.exit(0);
                     }
                }
            );

          setSize(500, 200);
          setVisible(true);
        }


        }
        catch (Exception e)
        {
            System.out.println("Problem starting client!");
        }
    }

    public void run()
    {
        while (true)
        {
             try {
                String currMsg = myReader.readLine();
			          outputArea.append(currMsg+"\n");
             }
             catch (Exception e)
             {
                System.out.println(e +  ", closing client!");
                break;
             }
        }
        System.exit(0);
    }

    public void actionPerformed(ActionEvent e)
    {
        String currMsg = e.getActionCommand();      // Get input value
        inputField.setText("");
        myWriter.println(myName + ":" + currMsg);   // Add name and send it
    }                                               // to Server

    public static void main(String [] args)
    {
         ImprovedChatClient JR = new ImprovedChatClient();
         JR.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
