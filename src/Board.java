import java.awt.*;
public class Board
{
   private Object[] values;
   private int factor;
   private int solve=0;
   /*
   0 1 2
   3 4 5
   6 7 8   
   */
   public Board(int layer)
   {
      factor=(int)Math.pow(3,layer);
      if(layer==0)
      {
         values = new Integer[9];
         for(int i=0;i<values.length;i++)
         {
            values[i] = new Integer(0);
         }
      }
      else
      {
         values = new Board[9];
         for(int i=0;i<values.length;i++)
         {
            values[i] = new Board(layer-1);
         }
      }
   }
   public Object get(int[] path)
   {
      if(values instanceof Integer[])
      {
         return (Integer)values[path[0]];
      }
      int index=path[0];
      if(path.length==1)
      {
         return values[path[0]];
      }
      else
      {
         int[] newPath = new int[path.length-1];
         for(int i=0;i<newPath.length;i++)
         {
            newPath[i]=path[i+1];
         }
         return ((Board)(values[index])).get(newPath);
      }
   }
   public boolean set(int[] path,boolean isX)
   {
      if(values instanceof Integer[])
      {
         if((Integer)(values[path[0]])==0)
         {
            if(isX)//X=1, O=-1
               values[path[0]]=1;
            else
               values[path[0]]=-1;
            checkSolved();
            return true;
         }
         return false;
      }
      int index=path[0];
      int[] newPath = new int[path.length-1];
      for(int i=0;i<newPath.length;i++)
      {
         newPath[i]=path[i+1];
      }
      return ((Board)(values[index])).set(newPath,isX);
   }
   public void checkSolved()
   {
      if(values instanceof Integer[])
      {
         Integer[] temp=(Integer[])values;
         for(int i=0;i<9;i+=3)
         {
            if(Math.abs(temp[i]+temp[i+1]+temp[i+2])==3)
            {
               solve=temp[i];
            }
         }
         for(int i=0;i<3;i++)
         {
            if(Math.abs(temp[i]+temp[i+3]+temp[i+6])==3)
            {
               solve=temp[i];
            }
         }
         if(Math.abs(temp[0]+temp[4]+temp[8])==3)
         {
            solve=temp[0];
         }
         if(Math.abs(temp[2]+temp[4]+temp[6])==3)
         {
            solve=temp[2];
         }
      }
      else
      {
         Integer[] temp=new Integer[9];
         for(int i=0;i<temp.length;i++)
         {
            ((Board)values[i]).checkSolved();
            temp[i]=((Board)values[i]).getSolve();
         }
         for(int i=0;i<9;i+=3)
         {
            if(Math.abs(temp[i]+temp[i+1]+temp[i+2])==3)
            {
               solve=temp[i];
            }
         }
         for(int i=0;i<3;i++)
         {
            if(Math.abs(temp[i]+temp[i+3]+temp[i+6])==3)
            {
               solve=temp[i];
            }
         }
         if(Math.abs(temp[0]+temp[4]+temp[8])==3)
         {
            solve=temp[0];
         }
         if(Math.abs(temp[2]+temp[4]+temp[6])==3)
         {
            solve=temp[2];
         }
      }
   }
   public int getSolve()
   {
      return solve;
   }
   public void draw(Graphics g,int y,int x)
   {
      g.setColor(Color.black);
      int yCo=y;
      int xCo=x;
      if(values instanceof Integer[])
      {//lowest level
         for(int i=0;i<values.length;i++)
         {
            if(i<3)
            {
               x+=i*MetaBoard.DIM;
            }
            else
               if(i<6)
               {
                  y+=MetaBoard.DIM;
                  x+=(i-3)*MetaBoard.DIM;
               }
               else
               {
                  y+=MetaBoard.DIM*2;
                  x+=(i-6)*MetaBoard.DIM;
               }
            if((Integer)values[i]==1)
            {
               g.drawLine(x,y,x+MetaBoard.DIM,y+MetaBoard.DIM);
               g.drawLine(x,y+MetaBoard.DIM,x+MetaBoard.DIM,y);
            }
            if((Integer)values[i]==-1)
            {
               g.drawOval(x,y,MetaBoard.DIM,MetaBoard.DIM);
            }
            y=yCo;
            x=xCo;
         }
      }
      else
      {
         ((Board)values[0]).draw(g,y,x);
         ((Board)values[1]).draw(g,y,factor*MetaBoard.DIM+x);
         ((Board)values[2]).draw(g,y,factor*2*MetaBoard.DIM+x);
         ((Board)values[3]).draw(g,y+factor*MetaBoard.DIM,x);
         ((Board)values[4]).draw(g,y+factor*MetaBoard.DIM,x+factor*MetaBoard.DIM);
         ((Board)values[5]).draw(g,y+factor*MetaBoard.DIM,x+factor*2*MetaBoard.DIM);
         ((Board)values[6]).draw(g,y+factor*MetaBoard.DIM*2,x);
         ((Board)values[7]).draw(g,y+factor*MetaBoard.DIM*2,x+factor*MetaBoard.DIM);
         ((Board)values[8]).draw(g,y+factor*2*MetaBoard.DIM,x+factor*MetaBoard.DIM*2);
      }
      if(solve==1)
      {
         g.setColor(Color.red.darker());
         g.drawLine(x,y,x+MetaBoard.DIM*factor*3,y+MetaBoard.DIM*factor*3);
         g.drawLine(x,y+MetaBoard.DIM*factor*3,x+MetaBoard.DIM*factor*3,y);
      }
      if(solve==-1)
      {
         g.setColor(Color.blue.darker());
         g.drawOval(x,y,MetaBoard.DIM*factor*3,MetaBoard.DIM*factor*3);
      }
   }
}