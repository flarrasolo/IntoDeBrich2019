package Logica.Hilos;

import Grafica.ComponenteGrafico;
import Grafica.Terreno.Piso;
import Logica.LogicaJuego;

public class MovimientoFluidoEnemigo extends Movimiento {
		
	protected int x;
	protected int y;
	protected int d;
	protected int velocidad;
	protected int pixel;
	
	protected ComponenteGrafico componente;

	public MovimientoFluidoEnemigo(ComponenteGrafico x,int d,LogicaJuego l){
		super(l);
		this.x=x.getPosicionX();
		this.y=x.getPosicionY();
		this.d=d;
		if(x.getVelMovimiento()==1)
			velocidad=17;
		else
			if(x.getVelMovimiento()==2)
				velocidad =14;
			else
				velocidad=11;
		pixel=2;
		componente=x;
	}
	
	/**
	 * mueve el Tanque de A a B
	 */
	public void run()
	{
		try
		{
			int auxX=0;
			int auxY=0;
			boolean movio=false;
			miLogica.getComponente(x, y).setDireccion(d);
			switch (d){
				case 1: if(x<(20-1))
							if(miLogica.getComponente(x+1, y).movimientoPosibleEnemigo()){
								movio=true;
								miLogica.getComponente(x+1, y).setMovimientoPosible(false);
								auxX=x+1;
								auxY=y;
							}
						break;
				case 2: if(x>0)
							if(miLogica.getComponente(x-1, y).movimientoPosibleEnemigo()){
								movio=true;
								miLogica.getComponente(x-1, y).setMovimientoPosible(false);
								auxX=x-1;
								auxY=y;
							}
						break;
				case 3: if(y>0)
							if(miLogica.getComponente(x, y-1).movimientoPosibleEnemigo()){
								movio=true;
								miLogica.getComponente(x, y-1).setMovimientoPosible(false);
								auxX=x;
								auxY=y-1;
							}
						break;
				case 4: if(y<(20-1))
							if(miLogica.getComponente(x, y+1).movimientoPosibleEnemigo()){
								movio=true;
								miLogica.getComponente(x, y+1).setMovimientoPosible(false);
								auxX=x;
								auxY=y+1;
							}
						break;
			}
			
			componente.posicionImagen(d);
			if(movio){
				int pixelX=componente.getX();
				int pixelY=componente.getY();
				for(int i=0;i<7;i++){
					if(componente.getVida()==0){
						i=7;
						miLogica.getComponente(auxX, auxY).setMovimientoPosible(true);
						d=0;
					}
					else
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
				case 1:
					componente.setPosicionX(x+1);
					componente.setX(pixelX);
					miLogica.setComponente(componente);
					miLogica.setComponente(new Piso(x,y,miLogica));
					break;
				case 2: 
					componente.setPosicionX(x-1);
					componente.setX(pixelX);
					miLogica.setComponente(componente);
					miLogica.setComponente(new Piso(x,y,miLogica));
					break;
				case 3: 
					componente.setPosicionY(y-1);
					componente.setY(pixelY);
					miLogica.setComponente(componente);
					miLogica.setComponente(new Piso(x,y,miLogica));
					break;
				case 4: 
					componente.setPosicionY(y+1);
					componente.setY(pixelY);
					miLogica.setComponente(componente);
					miLogica.setComponente(new Piso(x,y,miLogica));
					break;
				}
				if(componente.getVida()!=0){
					for(int i=0;i<8;i++){
						if(componente.getVida()==0)
							i=7;
						else
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
			
			componente.puedeMover();
			sleep(5);
			stop();
		}catch(InterruptedException e){ e.printStackTrace();}
		
	}

}