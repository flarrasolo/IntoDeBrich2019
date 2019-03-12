package Grafica;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

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
	 private JLabel msjUsuario, lblVidas;
	 private JFrame yo=this;
	 private JLabel label;
	 private JLabel lblEdificios;
	    
	 /**
	  * Create the frame.
	 */
	 public GUI() {
	    	mapaLogica = new LogicaJuego(this);
	    	
	        //Seteo Inicial
	        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	        this.setTitle("IntoDeBrich 2019");
	        this.setResizable(false);
	        
	        //Setteo del Panel Contenedor
	        
	        setBounds(400,150,486,630);
	        contentPane = new JLayeredPane();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        contentPane.setVisible(true);
	        setContentPane(contentPane);
	        contentPane.setLayout(null);
	        getContentPane().setLayout(null);
	        contentPane.setLayout(null);
	        JLabel fondo = new JLabel();
	        ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/faintBlack.png"));
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

			msjUsuario = new JLabel("Seleccione la ubicación donde desea ubicar el Tanque");
			msjUsuario.setForeground(Color.YELLOW);
			msjUsuario.setBounds(20, 491, 400, 14);
			contentPane.add(msjUsuario,new Integer(2));
			
			lblVidas = new JLabel("Tanque: 10  -  Robot: 10  -  Escarabajo: 10  -  Escarabajo: 10  -  Avispa: 10");
			
			lblVidas.setForeground(Color.WHITE);
			lblVidas.setBounds(20, 549, 427, 22);
			contentPane.add(lblVidas,new Integer(2));
			
			label = new JLabel("PUNTOS DE ENERGIA");
			label.setForeground(Color.WHITE);
			label.setBounds(20, 524, 266, 14);
			contentPane.add(label,new Integer(2));
			
			lblEdificios = new JLabel("Edificios: ");
			lblEdificios.setForeground(Color.WHITE);
			lblEdificios.setBounds(20, 577, 400, 14);
			contentPane.add(lblEdificios,new Integer(2));
			
			mapaLogica.generarPanel();			
	        
	        setVisible(true);
	       	        
	 }
	 
	 /**
	  * Launch the application.
	  */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						GUI frame = new GUI();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
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
	 
	 public void repintarPanel() {
		 contentPane.repaint();
	 }
	 
	 public void setMsjUsuario(String msj) {
		 msjUsuario.setText(msj);
	 }
	 
	 public void setVidas(String vidas) {
		 lblVidas.setText(vidas);

	 }
	 public void setVidasEdificios(String vidas) {
		 lblEdificios.setText("Edificios :"+vidas);
	 }
}
