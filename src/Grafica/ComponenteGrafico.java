package Grafica;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Grafica.Jugadores.Jugador;
import Logica.LogicaJuego;

public abstract class ComponenteGrafico extends JLabel {
	
	/*Variables*/
	
	protected final int alto  = 60;
	protected final int ancho = 60;
	protected int miX,miY;

	protected int pixelX;
	protected int pixelY;
	protected int profundidad;
	protected int direccion;
	protected int puntaje;
	protected int energia;
	
	protected boolean puedoPonerJugador;
	
	protected LogicaJuego logica;
	
	/*Constructor*/
	public ComponenteGrafico(int x, int y, LogicaJuego miLogica){	
		super();
		miX = x;
		miY = y;
		pixelX=miX*ancho;
		pixelY=miY*alto;
		setBounds(pixelX ,pixelY , ancho, alto);
		setVisible(true);
		
		logica = miLogica;
	}
	
	/*--------------------------------------------------Comandos------------------------------------------------------*/
	
	public void setPosicionX(int x){
		miX=x;	
		pixelX=miX*ancho;
	}

	public void setPosicionY(int y){
		miY=y;
		pixelY=miY*alto;
	}
	
	public void setPixelX(int x){
		pixelX=x;
	}
	
	public void setPixelY(int y){
		pixelY=y;
	}
	
	public void setDireccion(int d){}
		
	public void posicionImagen(int i){}
			
	public void puedeMover(){}
	
	public abstract void morir();
	
	/**
	 * Recibe el ataque del jugador de turno.
	 * @param j
	 * @return Verdadero si recibio daño (o sea es una componente grafica dañable), falso caso contrario
	 */
	public abstract boolean recibirAtaque(Jugador j);
	/*--------------------------------------------Consultas------------------------------------------------------*/
	
	public int getDireccion(){
		return direccion;
	}
	
	public int getPixelX(){
		return pixelX;
	}
	
	public int getPixelY(){
		return pixelY;
	}
	
	public int getPosicionX(){
		return miX;
	}
	
	public int getPosicionY(){
		return miY;
	}
	
	
	public int getEnergia(){
		return energia;
	}
	
	public int getDepth(){
		return profundidad;
	}
	
	public void setPuedoPonerJugador(boolean puedo) {
		puedoPonerJugador = puedo;
	}

	public boolean getPuedoPonerJugador() {
		return puedoPonerJugador;
	}
	
	public boolean getPuedeMover(){
		return false;
	}
	
	public boolean daniaEnemigo(){
		return false;
	}
	
	public void setDibujo(String nombre) {
		ImageIcon fot = new ImageIcon(getClass().getResource(nombre));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
	}
	
}
