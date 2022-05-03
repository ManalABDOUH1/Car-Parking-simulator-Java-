import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Voiture extends JLabel implements Runnable {
    String nom;
    Parking park;
    ImageIcon carImg ;
    int posx;
    int posy;
    int positionY;
    int positionX;
    String side;
    public static Thread MesVoitures[];


    public Voiture(String name, Parking park,int x,int y){
        this.nom    = name;
        this.park   = park;
        this.posx   = x;
        this.posy   = y ;
        this.positionY = y;
        this.positionX = x;
        this.carImg = new ImageIcon("images/v1.png");
        this.setIcon(carImg);
        Dimension size = this.getPreferredSize();
        this.setBounds(posx, posy, size.width,size.height);

    }
    public boolean rentrer() throws InterruptedException{

        if(this.park.accept(this)){

            return true;

        }else{

            return false;
        }
    }
    public void changeImage(String name){

        this.setIcon(new ImageIcon(name));
    }

    public String toString(){

        return "car Name Is : "+this.nom + " posX = "+posx + " posY = "+this.posy + " car Side : "+this.side;
    }
    public void run(){
        System.out.println(this);
        try {
            while(true){
                Thread.sleep((long)  (500* Math.random()));
                System.out.format("[%s]: Je demande à rentrer  \n", this.nom);
//               Check An Access
                this.park.semaphore.acquire();
                if(!(this.rentrer())){

                    System.out.format("[%s]  : Car want Go Inside A Gars  \n", this.nom);

                }
                this.park.semaphore.release();
                Thread.sleep((long)  (500* Math.random()));
                if(this.park.infoVoitures.contains(this)){
                    System.out.format("[%s]: Je demande à sortir  \n", this.nom);
                    this.park.semaphore.acquire();
                    this.park.leave(this);
                    this.park.semaphore.release();

                }

            }

        }catch(InterruptedException e){

            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        int TailleParking=7;
        JFrame frame = new JFrame("PARKING Manal");
        Parking  leParking = new Parking(TailleParking);
        frame.setContentPane(leParking);
        leParking.setLayout(null);
        frame.setSize(1540,824);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        int nbVoitures = 10 ;
        String CarName = "myVoit";
        JButton startAnimation = new JButton("Start");
        leParking.add(startAnimation);
        startAnimation.setVisible(true);
        startAnimation.setBounds(500, 400, 150, 50);
        startAnimation.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startAnimation.setBackground(Color.pink);
        startAnimation.setForeground(Color.BLACK);
        startAnimation.setBorder(null);

        Thread MesVoitures[][] = new Thread[7][2];
        int x ;
        int y = 150;
        for (int i = 0; i<7; i++){
            x = 10;
            for(int j=0;j<2;j++){

                Voiture myVoit= new Voiture(CarName +" " + i+j,leParking,x,y);
                if(j == 0){
                    myVoit.side = "l";

                }else{

                    myVoit.side = "r";
                }
                MesVoitures[i][j]=  new Thread(myVoit);

                leParking.add(myVoit);
                leParking.allCars.add(myVoit);

                x= x + 80;

            }

            y= y + 150;

        }

        ActionListener actionListenerForButoon = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                //            Run Thread
                for (int i = 0; i<7; i++){
                    for(int j=0;j<2;j++){

                        MesVoitures[i][j].start();
                    }

                }
            }
        };
        startAnimation.addActionListener(actionListenerForButoon);
    }
}





