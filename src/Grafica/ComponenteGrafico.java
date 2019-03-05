package Grafica;

import javax.swing.JLabel;

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
	protected int vida;
	
	protected boolean movimientoPosible;
	protected boolean movimientoPosibleDisparo;
	
	protected LogicaJuego logica;
	
	/*Constructor*/
	public ComponenteGrafico(int x, int y){	
		super();
		miX = x;
		miY = y;
		pixelX=miX*ancho;
		pixelY=miY*alto;
		setBounds(pixelX ,pixelY , ancho, alto);
		setVisible(true);
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
	
	public void setX(int x){
		pixelX=x;
	}
	
	public void setY(int y){
		pixelY=y;
	}

	public ComponenteGrafico crearDisparo(){
		return null;
	}
	
	public void setMovimientoPosible(boolean x){
		movimientoPosible=x;
	}
	
	public void setDireccion(int d){}
	
	public void aumentarVida(){}
	
	public void posicionImagen(int i){}

	public void mover(int direccion){}
	
	public void subirNivel(){}
	
	public void bajarNivel(){}
	
	public void setEjecutor(ComponenteGrafico tanque){}
	
	public void setState(int i){}
	
	public void usaCasco(boolean x){}
	
	public void puedeMover(){}
	
	public void reducirDisparoAndando(){}
	
	public abstract void colicion(ComponenteGrafico e);
	
	/*--------------------------------------------Consultas------------------------------------------------------*/
	
	public int getDireccion(){
		return direccion;
	}
	
	public int getX(){
		return pixelX;
	}
	
	public int getPosicionX(){
		return miX;
	}
	
	public int getPosicionY(){
		return miY;
	}
	
	public int getY(){
		return pixelY;
	}
	
	public int getPuntos(){
		return puntaje;
	}

	public int getVida(){
		return vida;
	}
	
	public ComponenteGrafico getEjecutor(){
		return null;
	}
	
	public int getDepth(){
		return profundidad;
	}
	
	public int getVelMovimiento(){
		return 0;
	}
	
	public int getVelocidadDisparo(){
		return 0;
	}
	
	public int getDisparosSimultaneos(){
		return 0;
	}
	/*
	public int getNivel(){
		return 0;
	}
	*/
	public boolean getPuedeMover(){
		return false;
	}
	
	public boolean afectaAcero(){
		return false;
	}
	
	public boolean daniaEnemigo(){
		return false;
	}
	
	public abstract boolean movimientoPosible();
	
	public abstract boolean movimientoPosibleDisparo();
	
	public abstract boolean movimientoPosibleEnemigo();
	
}
