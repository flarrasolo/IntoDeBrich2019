package Logica.Hilos;

import java.util.ArrayList;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class Movimiento extends Thread{
	
	protected LogicaJuego miLogica;
	protected ArrayList<ComponenteGrafico> balas;
	protected ArrayList<ComponenteGrafico> enemigos;
	
	
	public Movimiento(LogicaJuego logic)
	{
		miLogica = logic;
		balas=new ArrayList<ComponenteGrafico>();
		enemigos=new ArrayList<ComponenteGrafico>();
	}
	
	public void addBala(ComponenteGrafico x){}
	
	public void addEnemigo(ComponenteGrafico x){}
	
	public  ArrayList<ComponenteGrafico> getBalas(){
		return balas;
	}
	
	public  ArrayList<ComponenteGrafico> getEnemigos(){
		return enemigos;
	}

}
