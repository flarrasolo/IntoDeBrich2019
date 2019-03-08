package Logica.Hilos;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class MovimientoFluidoDisparo extends Movimiento{
	
	protected int x;
	protected int y;
	protected int d;
	protected int velocidad;
	protected int pixel;	
	
	protected ComponenteGrafico componente;
	
	public MovimientoFluidoDisparo(ComponenteGrafico x,int d,LogicaJuego l){
		super(l);
		this.x=x.getPosicionX();
		this.y=x.getPosicionY();
		this.d=d;
		if(x.getVelocidadDisparo()==1)
			velocidad=15;
		else
			if(x.getVelocidadDisparo()==2)
				velocidad=12;
			else
				velocidad=9;
		pixel=2;
		componente=x;
	}
	
	/**
	 * Mueve un disparo de A a B, si coliciona ejecuta la colicion.
	 */
	public void run()
	{	
		try{
			boolean movio=true;
			boolean seCreo=true;
			switch (d){
				case 1: if(x<(19)){
							if(!miLogica.getComponente(x+1, y).movimientoPosibleDisparo()){
								movio=false;
								x++;
							}
						}else
							seCreo=false;
						break;
				case 2: if(x>0){
							if(!miLogica.getComponente(x-1, y).movimientoPosibleDisparo()){
								movio=false;
								x--;
							}
						}else
							seCreo=false;
						break;
				case 3: if(y>0){
							if(!miLogica.getComponente(x, y-1).movimientoPosibleDisparo()){
								movio=false;
								y--;
							}
						}else
							seCreo=false;
						break;
				case 4: if(y<(19)){
							if(!miLogica.getComponente(x, y+1).movimientoPosibleDisparo()){
								movio=false;
								y++;
							}
						}else
							seCreo=false;
						break;
			}
			if(movio&&seCreo){
				int pixelX=componente.getX();
				int pixelY=componente.getY();
				for(int i=0;i<7;i++){
					switch (d) {
						case 1:
							pixelX+=pixel;
							componente.setX(pixelX);
							sleep(velocidad);
							break;
						case 2:
							pixelX-=pixel;
							componente.setX(pixelX);
							sleep(velocidad);
							break;
						case 3:
							pixelY-=pixel;
							componente.setY(pixelY);
							sleep(velocidad);
							break;
						case 4:
							pixelY+=pixel;
							componente.setY(pixelY);
							sleep(velocidad);
							break;
					}
					miLogica.actualizarPanel();
				}
				switch (d){
				case 1: if(x<(19)){
							if(!miLogica.getComponente(x+1, y).movimientoPosibleDisparo()){
								movio=false;
								x++;
							}
						}else
							seCreo=false;
						break;
				case 2: if(x>0){
							if(!miLogica.getComponente(x-1, y).movimientoPosibleDisparo()){
								movio=false;
								x--;
							}
						}else
							seCreo=false;
						break;
				case 3: if(y>0){
							if(!miLogica.getComponente(x, y-1).movimientoPosibleDisparo()){
								movio=false;
								y--;
							}
						}else
							seCreo=false;
						break;
				case 4: if(y<(19)){
							if(!miLogica.getComponente(x, y+1).movimientoPosibleDisparo()){
								movio=false;
								y++;
							}
						}else
							seCreo=false;
						break;
				}
				if(movio){
					switch(d){
					case 1:
						x++;
						componente.setPosicionX(x);
						break;
					case 2:
						x--;
						componente.setPosicionX(x);
						break;
					case 3:
						y--;
						componente.setPosicionY(y);
						break;						
					case 4:
						y++;
						componente.setPosicionY(y);
						break;	
					}
					miLogica.actualizarPanel();
					for(int i=0;i<8;i++){
						switch (d) {
							case 1:
								pixelX+=pixel;
								componente.setX(pixelX);
								sleep(velocidad);
								break;
							case 2:
								pixelX-=pixel;
								componente.setX(pixelX);
								sleep(velocidad);
								break;
							case 3:
								pixelY-=pixel;
								componente.setY(pixelY);
								sleep(velocidad);
								break;
							case 4:
								pixelY+=pixel;
								componente.setY(pixelY);
								sleep(velocidad);
								break;
						}
						miLogica.actualizarPanel();
						}
					}
				}
			if(!movio && seCreo){
				miLogica.eliminarColicion(x,y,componente.getEjecutor());
				miLogica.eliminarGrafico(componente);
				componente.colicion(componente.getEjecutor());
			}else
				if(!seCreo){
					miLogica.eliminarGrafico(componente);
					componente.colicion(componente.getEjecutor());
				}else{
					componente.puedeMover();
				}
			miLogica.actualizarPanel();
		
		stop();			
		}catch(InterruptedException e){ e.printStackTrace();}
	}

}