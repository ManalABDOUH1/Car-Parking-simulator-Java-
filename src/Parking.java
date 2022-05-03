import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Parking extends JPanel{
    int PlacesOccupees ;
    int capacite ;
    ImageIcon image;
    boolean Place[] ;
    public  Semaphore semaphore;
    public ArrayList<Voiture> allCars;
    public HashSet<Voiture> infoVoitures = new HashSet<Voiture>();


    Parking(int size){

        capacite = size;
        this.PlacesOccupees = 0;
        this.allCars = new ArrayList();
        this.semaphore = new Semaphore(4,true);
        Place = new boolean[7];
        for(int i=0 ; i<Place.length;i++)
        {
            Place[i]=false ;
        }
    }

    int places(){
        return (capacite - this.PlacesOccupees);
    }


    boolean  accept(Voiture myVoit) {
        if  (this.places() > 0 ){
            this.PlacesOccupees ++ ;
            this.checkFreePlaceForMove(myVoit);
            infoVoitures.add(myVoit);
            System.out.format("[Parking] :%s acceptée, il reste %d places \n", myVoit.nom,this.places());
            System.out.format("Voiture Inside The Garees\n");
            System.out.println(infoVoitures);
            return (true) ;
        }else{
            System.out.format("Parking : %s refusée, il reste  %d places \n", myVoit.nom,this.places());
            goAroundTheParking(myVoit,myVoit.posx,myVoit.posy);
            return(false);
        }
    }

    public void goAroundTheParking(Voiture myVoit,int x,int y){

        while(y > 70){
            y-=10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if(myVoit.side =="l"){

            while(y > 0){
                y-=10;
                myVoit.setLocation(x,y);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        myVoit.changeImage("images/v4.png");
        myVoit.setBounds(x, y, 150,190);
        while(x < 940){
            x+=10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(myVoit.side == "l"){
            x+=70;
            myVoit.setLocation(x,y);
        }

        myVoit.changeImage("images/v3.png");
        myVoit.setBounds(x,y ,190,190);
        while(y < 605){
            y += 10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        myVoit.changeImage("images/v2.png");
        myVoit.setBounds(x, y, 150,190);

        while(x > myVoit.positionX){

            x-=10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        myVoit.changeImage("images/v1.png");
        while(y > myVoit.positionY){
            y -= 10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        myVoit.posx = x;
        myVoit.posy = y;

    }
    void leave(Voiture myVoit) {
        PlacesOccupees --;
        infoVoitures.remove(myVoit);
        System.out.format("Parking :[%s] est sortie, reste  %d places and posY = %d\n", myVoit.nom, places(),myVoit.posy);

        switch (myVoit.posy) {
            case 0 :
                Place[0]=false;

                break;
            case 150:
                Place[1]=false;

                break;
            case 300:
                Place[2]=false;

                break;

            case 450:
                Place[3]=false;
                break;

            case 600:
                Place[4]=false;
                break;
        }
        this.moveCarByRoundOutside(myVoit,myVoit.posx,myVoit.posy);
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        image = new ImageIcon("images/bg.jpg");
        image.paintIcon(this, g, -130,-40);
    }

    public void checkFreePlaceForMove(Voiture myVoit) {

        //--------Check If place One Empty---------
        if(!Place[0]){
            Place[0] = true;
            this.moveCarByRound(myVoit,myVoit.posx,myVoit.posy,0);
        }
        //--------Check If place Two Empty--------
        else if(!Place[1]){
            Place[1] = true;
            this.moveCarByRound(myVoit, myVoit.posx,myVoit.posy,150);
        }
        //--------Check If place Two Empty--------
        else if(!Place[2]){
            Place[2] = true;
            this.moveCarByRound(myVoit, myVoit.posx,myVoit.posy,300);
        }
        //--------Check If place Two Empty--------
        else if(!Place[3]){
            Place[3] = true;
            this.moveCarByRound(myVoit, myVoit.posx,myVoit.posy,450);
        }
        //--------Check If place Three Empty
        else if(!Place[4]){
            Place[4] = true;
            this.moveCarByRound(myVoit, myVoit.posx,myVoit.posy,600);

        }
        else{

            System.out.println("All place Filled");
        }

    }

    public void moveCarByRound(Voiture myVoit,int x,int y,int Distance){

        while(y > 70){
            y-=10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if(myVoit.side =="l"){

            while(y > 0){
                y-=10;
                myVoit.setLocation(x,y);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        myVoit.changeImage("images/v4.png");
        myVoit.setBounds(x,y, 190,190);
        while(x < 940){
            x+=10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if(myVoit.side == "l"){
            x+=70;
            myVoit.setLocation(x,y);
        }

        myVoit.changeImage("images/v3.png");
        myVoit.setBounds(x,y ,190,190);
        while(y < Distance){

            y += 10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        if(myVoit.side == "r"){

            for(Voiture cl:this.allCars){

                if(y == (int)cl.getLocation().getY()){

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Parking.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
        myVoit.changeImage("images/v4.png");
        myVoit.setBounds(x,y ,190,190);

        while(x < 1200){

            x+=10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        myVoit.posx = x;
        myVoit.posy = y;

    }


    public void moveCarByRoundOutside(Voiture myVoit, int x, int y) {

        while(x > 1500){
            x-=10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for(Voiture cl:this.allCars){

            if(y == (int)cl.getLocation().getY()){

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Parking.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        while(x > 1020){
            x-=10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        myVoit.changeImage("images/v3.png");
        myVoit.setBounds(x,y ,190,190);
        while( y < 690){

            y += 10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
        myVoit.changeImage("images/v2.png");
        myVoit.setBounds(x,y, 150,190);
        while(x >  myVoit.positionX){
            x-=10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        myVoit.changeImage("images/v1.png");
        myVoit.setBounds(x,y ,190,190);
        while(y > myVoit.positionY){
            y-=10;
            myVoit.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Voiture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        myVoit.posx = x;
        myVoit.posy = y;

    }
}
