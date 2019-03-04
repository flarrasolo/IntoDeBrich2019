package Grafica;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logica.LogicaJuego;

public class GUI extends JFrame{
	 private JLayeredPane contentPane;
	 private LogicaJuego mapaLogica;
	 private boolean disparo;
	 private boolean movio;
	 private JLabel btnRestart;
	 //private JLabel panelRestantes;
	 //private JLabel panelVidas;
	 //private JLabel panelNivel;
	 //private AudioClip musicaJuego;
	 private boolean teclado=true;
	 private JFrame yo=this;
	    
	 /**
	  * Create the frame.
	 */
	 public GUI() {
	    	disparo=false;
	    	movio=false;
	    	mapaLogica = new LogicaJuego(this);
	    	
	        //seteo inicial
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setTitle("Battle Star");
	        this.setResizable(false);
	        
	        //setteo del panel contenedor
	        
	        setBounds(100,100,900,630);
	        contentPane = new JLayeredPane();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        contentPane.setVisible(true);
	        setContentPane(contentPane);
	        contentPane.setLayout(null);
	        getContentPane().setLayout(null);
	        contentPane.setLayout(null);
	        JLabel fondo = new JLabel();
	        ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/fondo.jpg"));
			Icon icono = new ImageIcon(fot.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));
	        fondo.setVisible(true);
	        fondo.setIcon(icono);
	        fondo.setBounds(0,0,600,600);
	        contentPane.add(fondo);
	        contentPane.setLayer(fondo, 1);
	 
	 
	        JPanel panel = new JPanel();
			panel.setBounds(600, 0, 321, 600);
			contentPane.add(panel);
			panel.setLayout(null);
			
			mapaLogica.generarPanel();
	        mapaLogica.crearYUbicarEnemigos();
	        mapaLogica.crearJugador();
	        
	        setVisible(true);
	        
	        //Agrego el oyente al teclado en el panel contenedor
	        this.addKeyListener( new KeyListener() {
	        	

				public void keyTyped1(KeyEvent arg0) {}

				public void keyPressed(KeyEvent e) {
					if (teclado){
						
					 switch(e.getKeyCode()){
					 	case KeyEvent.VK_UP :
					 			mapaLogica.mover(3);		//Mover el Jugador hacia Arriba
						 		movio=true;
						 		
					 		break;
						case KeyEvent.VK_DOWN :
								mapaLogica.mover(4);		//Mover el Jugador hacia Abajo
						 		movio=true;
							break;
	        			case KeyEvent.VK_RIGHT :
						 		mapaLogica.mover(1);		//Mover el Jugador hacia Derecha
						 		movio=true;
	        				break;
	 					case KeyEvent.VK_LEFT :
	 							mapaLogica.mover(2);		//Mover el Jugador hacia Izquierda
						 		movio=true;
	 						break;
	 					case KeyEvent.VK_SPACE:
	 							mapaLogica.crearDisparoJugador();	//Creo disparo del jugador
	 							disparo=true;
	 						break; 
					 }}
				}

				/*public void keyReleased(KeyEvent e){
					switch(e.getKeyCode()){	
						case KeyEvent.VK_SPACE:
							disparo=false;
							break;
						case KeyEvent.VK_UP : 
					 		movio=false;
							break;
						case KeyEvent.VK_DOWN :
							movio=false;
							break;
	        			case KeyEvent.VK_RIGHT :
	        				movio=false;
	        				break;
	 					case KeyEvent.VK_LEFT :
	 						movio=false;
	 						break;
					}
				}*/

				public void keyTyped(KeyEvent arg0) {}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			
	        }
				);	       	        
	    	}
 
	 public void eliminarGrafico(ComponenteGrafico x){
	  	contentPane.remove(x);
	 }
	   
	 public void agregarGrafico(ComponenteGrafico x){
	  	contentPane.add(x,new Integer(x.getDepth()));
	 }
	 
	 public	JLayeredPane getContentPane(){
	  	return contentPane;
	 }
	    
	 public void terminarJuego(boolean Victoria){
		 contentPane.removeAll();
		 teclado= false;
		 contentPane.add(btnRestart);
		 btnRestart.enable();
		 btnRestart.setVisible(true);
		 yo=this;
		if(Victoria){
			ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/you_win.png"));
			Icon icono = new ImageIcon(fot.getImage().getScaledInstance(this.getWidth()+200, this.getHeight(), Image.SCALE_DEFAULT));
			JLabel algo = new JLabel();
		    
			this.setBounds(100, 100, this.getWidth()+200, this.getHeight());
			algo.setBounds(0, 0, this.getWidth(), this.getHeight());
			algo.setPreferredSize(contentPane.getPreferredSize());
			algo.setIcon(icono);
			contentPane.add(algo);
			
		}else{
			
			ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/gameover.png"));
			Icon icono = new ImageIcon(fot.getImage().getScaledInstance(this.getWidth()+200, this.getHeight(), Image.SCALE_DEFAULT));
			JLabel algo = new JLabel();
			this.setBounds(100, 100, this.getWidth()+200, this.getHeight());
			algo.setBounds(0, 0, this.getWidth(), this.getHeight());
			algo.setPreferredSize(contentPane.getPreferredSize());
			algo.setIcon(icono);
			contentPane.add(algo);
			
		}
		contentPane.repaint();
	 }
			

	 
}
