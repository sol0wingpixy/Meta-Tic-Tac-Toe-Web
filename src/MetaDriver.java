import javax.swing.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;
public class MetaDriver
{
   public static File folder;
   public static void main(String[] arg)
   {
      MetaBoard board;
      folder = new File("Games/");
      final int SIZE=900;
      int file=JOptionPane.showConfirmDialog(null,"Read from file?","File?",JOptionPane.YES_NO_OPTION);
      if(file==JOptionPane.YES_OPTION&&folder.listFiles().length>0)
      {
         Object[] list = folder.listFiles();
         String obj = ((File)JOptionPane.showInputDialog(null,"","",JOptionPane.INFORMATION_MESSAGE, null,list, list[0])).getName();
         try
         {
            ArrayList<String> toSend = new ArrayList<String>();
            Scanner input = new Scanner(new FileReader("Games/"+obj));
            while(input.hasNextLine())
            {
               toSend.add(input.nextLine());
            }
            board = new MetaBoard(toSend,SIZE);
         }
         catch(IOException e)
         {
            e.printStackTrace();
            board = new MetaBoard(0,SIZE);
         }
      }
      else
      {
         Object[] options = {"Regular","Meta-TTT","Meta^2","Meta^3"};//possible player numbers
         String sel = (String)JOptionPane.showInputDialog(null,"What type of Tic-Tac-Toe?","Choices",JOptionPane.INFORMATION_MESSAGE, null,options, options[0]);
         int layers=2;
         switch(sel)
         {
            case "Regular":
               layers=0;
               break;
            case "Meta-TTT":
               layers=1;
               break;
            case "Meta^2":
               layers=2;
               break;
            case "Meta^3":
               layers=3;
               break;
         }
         board = new MetaBoard(layers,SIZE);
      }
      JFrame frame = new JFrame("Meta-Tic-Tac-Toe");
      frame.setSize(SIZE+20,SIZE+50);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(board);
      frame.setVisible(true);
   }
   public static boolean makeFile(List<String> record)
   {
      try{
      String name = (String)JOptionPane.showInputDialog(null,"What to call the file?","File Title",JOptionPane.QUESTION_MESSAGE);
      File newFile = new File(folder.getPath()+"/"+name+".txt");
      Path path=newFile.toPath();
      Files.write(path,record);
      return true;
      }
      catch(Exception e)
      {
         return false;
      }
   }
}