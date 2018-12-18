package geometrija;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public class Krug extends Figura implements Serializable 
{
	private static final long serialVersionUID = -3256449345963257501L;
	
	Point centar;
	Duz precnik;
	
	public Point getCentar()
	{
		return this.centar;
	}
	
	public Duz getPrecnik()
	{
		return this.precnik;
	}

	public Krug(Point c, Duz p)
	{
		this.centar = c;
		this.precnik = p;
	}
	
	public double povrsina()
	{
		return Math.PI * Math.pow(precnik.vratiDuzinu(), 2);
	}
	
	public double obim()
	{
		return Math.PI * precnik.vratiDuzinu() * 2;
	}

	@Override
	public void iscrtajSe(Graphics povrsinaZaCrtanje) 
	{
		povrsinaZaCrtanje.drawOval(this.centar.x, this.centar.y, (int)this.precnik.vratiDuzinu(), (int)this.precnik.vratiDuzinu());	
	}
}
