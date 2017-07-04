
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;



/**
 *
 * @author Tony Stark
 */

public class AndroidLockScreenNew extends JPanel implements Runnable{

    private Thread th;
    private Graphics2D g;
    private int DOTS=9;
    private boolean trues[]= new boolean[DOTS];
    private Rectangle rect[] = new Rectangle[DOTS];
    private int pattern[]= new int[DOTS];
    private List<Line2D.Double> lines = new ArrayList<>();
    private Color ink= new Color(255,144,0);
    private Color dot=Color.RED;    
    private int startx,starty,endx,endy,enddx,enddy;
    private int end,start,index=1,stroke=2,time=5,rdots=(int)Math.sqrt(DOTS);
    private int incw=20,oncw;
    private boolean drawing=false;
    private String patt="";
    private Timer timer;
    private JLabel output;

     AndroidLockScreenNew(){

         try{
             oncw=incw+40;
             th = new Thread(this);
             setOpaque(false);
        for(LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){
            if(info.getName().equals("Nimbus")){
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        
        ActionListener al = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(time==0){
                    resetScreen();
                    timer.stop();
                }
                --time;
            }
        };
        timer= new Timer(1000,al);
         //Listener starts
             MouseListener ml = new MouseAdapter(){
                @Override
                 public void mousePressed(MouseEvent me){
                     resetScreen();
                     index=1;
                     startx=me.getX();
                     starty=me.getY();
                     for(int i=0;i<rect.length;++i){
                         if(rect[i].contains(me.getPoint())){
                             startx=(int)rect[i].getCenterX();
                             starty=(int)rect[i].getCenterY();
                             endx=startx;
                             endy=starty;
                             trues[i]=true;
                             pattern[i]=index;
                             start=i;
                             drawing=true;
                             break;
                           
                         }
                     }
                 }
                @Override
                 public void mouseReleased(MouseEvent me){
                     drawing=false;
                     printPattern();
                     time=5;
                     timer.start();
                 }
             };

             MouseMotionListener mll = new MouseAdapter(){
                @Override
                 public void mouseDragged(MouseEvent me){
                    if(drawing==true){
                        endx=me.getX();
                        endy=me.getY();
                     for(int i=0;i<rect.length;++i){
                         if(trues[i]!=true){
                             if(rect[i].contains(me.getPoint())){
                                 index+=1;
                                 enddx=(int)rect[i].getCenterX();
                                 enddy=(int)rect[i].getCenterY();
                                 lines.add(new Line2D.Double(startx, starty, enddx, enddy));
                                 startx=enddx;
                                 starty=enddy;
                                 end=i;
                                 if((start==0&&end==2)||(start==2&&end==0)){
                                     if(trues[1]==false){
                                     trues[1]=true;pattern[1]=index;index+=1;}}
                                 if((start==3&&end==5)||(start==5&&end==3)){
                                     if(trues[4]==false){
                                     trues[4]=true;pattern[4]=index;index+=1;}}
                                 if((start==6&&end==8)||(start==8&&end==6)){
                                     if(trues[7]==false){
                                     trues[7]=true;pattern[7]=index;index+=1;}}
                                 if((start==0&&end==6)||(start==6&&end==0)){
                                     if(trues[3]==false){
                                     trues[3]=true;pattern[3]=index;index+=1;}}
                                 if((start==1&&end==7)||(start==7&&end==1)){
                                     if(trues[4]==false){
                                     trues[4]=true;pattern[4]=index;index+=1;}}
                                 if((start==2&&end==8)||(start==8&&end==2)){
                                     if(trues[5]==false){
                                     trues[5]=true;pattern[5]=index;index+=1;}}
                                 if((start==0&&end==8)||(start==8&&end==0)){
                                     if(trues[4]==false){
                                     trues[4]=true;pattern[4]=index;index+=1;}}
                                 if((start==2&&end==6)||(start==6&&end==2)){
                                     if(trues[4]==false){
                                     trues[4]=true;pattern[4]=index;index+=1;}}
                                 start=i;
                                 trues[i]=true;
                                 pattern[i]=index;
                                 break;
                             }
                             
                         }
                     }
                      
                        
                 }
                }
             };
             addMouseListener(ml);
             addMouseMotionListener(mll);
             //Listener ends
             th.start();
         }catch(Exception e){
          System.out.println(e.getMessage());
         }
    }

    @Override
     public void paint(Graphics g2){
         g=(Graphics2D)g2;
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         super.paint(g);
         int ind=0;
         g.setColor(ink);
         g.setStroke(new BasicStroke(incw,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
         //AlphaComposite c=(AlphaComposite) g.getComposite();
         //AlphaComposite ac = AlphaComposite.SrcOver;
         //ac =AlphaComposite.getInstance(ac.getRule(),0.5f);
         //g.setComposite(ac);
         if(drawing==true){
             g.drawLine(startx, starty, endx, endy);
         }
         for(int i=0;i<lines.size();++i){
             g.draw(lines.get(i));
         }
         g.setColor(dot);
         g.setStroke(new BasicStroke(stroke));
         //g.setComposite(c);
         for(int i=(getWidth()/rdots)/2;i<getWidth();i+=getWidth()/rdots){
             for(int j=(getHeight()/rdots)/2;j<getHeight();j+=getHeight()/rdots){
                 g.fillOval(i-incw/2, j-incw/2, incw, incw);
                 if(trues[ind]==true){
                     g.drawOval(i-(oncw)/2, j-(oncw)/2, oncw, oncw);
                 }
                 rect[ind]=new Rectangle();
                 rect[ind].setLocation(i-(oncw)/2,  j-(oncw)/2);
                 rect[ind].setSize(oncw+stroke/2, oncw+stroke/2);
                 ind+=1;
             }
         }
     }
    
    public void resetScreen(){
        if(timer.isRunning()){
            timer.stop();
        }
        clearPattern();
        lines.clear();
        makeFalse();
    }
    
    public void makeFalse(){
         for(int i=0;i<trues.length;++i){
             trues[i]=false;
         }
     }
    

     public void printPattern(){
         String s="";
         for(int i=0;i<pattern.length;++i){
             s+=","+pattern[i];
         }
         patt=s.substring(1);
         System.out.println("Pattern = "+patt);
         output.setText("Pattern = "+patt);
     }
     
     public void setOutputComponent(JLabel l){
         output=l;
     }

     public void clearPattern(){
         for(int i=0;i<pattern.length;++i){
             pattern[i]=0;
         }
     }

    @Override
    public void run() {
       try{
           while(true){
               th.sleep(15);
               repaint();
           }
       }catch(InterruptedException e){
           System.out.println(e.getMessage());
       }
    }

    public static void main(String args[]){
        JFrame jfm = new JFrame();
        jfm.setTitle("Android Lock Screen By Tony Stark");
        jfm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfm.setSize(400, 550);
        jfm.setLocation(300,50);
        jfm.getContentPane().setBackground(Color.WHITE);
        AndroidLockScreenNew an= new AndroidLockScreenNew();
        jfm.setLayout(new BorderLayout());
        jfm.add(an,BorderLayout.CENTER);
        JLabel lb = new JLabel("Pattern Will Be Displayed Here");
        lb.setFont(new Font("Arial",lb.getFont().getStyle(),20));
        lb.setBorder(BorderFactory.createTitledBorder(null, "Output", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Tahoma", 1, 24), new Color(204, 0, 0)));
        jfm.add(lb,BorderLayout.SOUTH);
        an.setOutputComponent(lb);
        jfm.setVisible(true);
    }
}
