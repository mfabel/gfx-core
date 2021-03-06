package fabe0940.gfx;

import fabe0940.gfx.core.Viewport;
import fabe0940.gfx.core.Window;
import fabe0940.gfx.core.World;
import fabe0940.gfx.math.Point3;
import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;

public class Graph extends World {
	private static double SECTIONS = 42.0;
	private static double X_FROM = -2.0 * Math.PI;
	private static double X_TO = 2.0 * Math.PI;
	private static double X_INTERVAL = (X_TO - X_FROM) / SECTIONS;
	private static double Y_FROM = -2.0 * Math.PI;
	private static double Y_TO = 2.0 * Math.PI;
	private static double Y_INTERVAL = (Y_TO - Y_FROM) / SECTIONS;
	private static double Z_FROM = -5.0;
	private static double Z_TO = 5.0;

	/* Constructor */
	public Graph(Viewport v, Window w) {
		super(v, w);

		return;
	}

	/* Render */
	public void draw(Graphics g) {
		double x;
		double y;
		double z;

		/* Set up window for drawing */
		drawBackground(g);
		drawTitle(g);

		/* Scan in Y direction */
		x = X_FROM;
		y = Y_FROM;
		z = z(x, y);
		moveTo3D(g, x, z, y);
		for (x = X_FROM; x <= X_TO; x += X_INTERVAL) {
			for (y = Y_FROM; y <= Y_TO; y += Y_INTERVAL) {
				z = z(x, y);
				g.setColor(calcColor(x, y, z));
				drawTo3D(g, x, z, y);
			}
			y = Y_FROM;
			z = z(x, y);
			moveTo3D(g, x, z, y);
		}

		/* Scan in X direction */
		x = X_FROM;
		y = Y_FROM;
		z = z(x, y);
		moveTo3D(g, x, z, y);
		for (y = Y_FROM; y <= Y_TO; y += Y_INTERVAL) {
			for (x = X_FROM; x <= X_TO; x += X_INTERVAL) {
				z = z(x, y);
				g.setColor(calcColor(x, y, z));
				drawTo3D(g, x, z, y);
			}
			x = Y_FROM;
			z = z(x, y);
			moveTo3D(g, x, z, y);
		}

		/* NOTE:
		 * The axis are drawn after the graph so that they appear in the
		 * foreground of the rendered scene. This is to maximize the
		 * legibility of the labels
		 */

		/* Draw axis */
		g.setColor(Color.WHITE);
		moveTo3D(g, X_FROM, 0, 0);
		drawTo3D(g, X_TO, 0, 0);
		moveTo3D(g, 0, 0, Y_FROM);
		drawTo3D(g, 0, 0, Y_TO);
		moveTo3D(g, 0, Z_FROM, 0);
		drawTo3D(g, 0, Z_TO, 0);

		/* Draw labels */
		moveTo3D(g, X_FROM - 0.3, 0, 0);
		writeTo3D(g, "-x");
		moveTo3D(g, X_TO, 0, 0);
		writeTo3D(g, "+x");
		moveTo3D(g, 0, 0, Y_FROM);
		writeTo3D(g, "-y");
		moveTo3D(g, 0, 0, Y_TO);
		writeTo3D(g, "+y");
		moveTo3D(g, 0, Z_FROM, 0);
		writeTo3D(g, "-z");
		moveTo3D(g, 0, Z_TO, 0);
		writeTo3D(g, "+z");

		/* Draw units */
		moveTo3D(g, 1, 0, 0);
		writeTo3D(g, "1");
		moveTo3D(g, 2, 0, 0);
		writeTo3D(g, "2");
		moveTo3D(g, 3, 0, 0);
		writeTo3D(g, "3");
		moveTo3D(g, 0, 1, 0);
		writeTo3D(g, "1");
		moveTo3D(g, 0, 2, 0);
		writeTo3D(g, "2");
		moveTo3D(g, 0, 3, 0);
		writeTo3D(g, "3");
		moveTo3D(g, 0, 0, 1);
		writeTo3D(g, "1");
		moveTo3D(g, 0, 0, 2);
		writeTo3D(g, "2");
		moveTo3D(g, 0, 0, 3);
		writeTo3D(g, "3");

		return;
	}

	/* Function to graph */
	public double z(double x, double y) {
		double r;
		double res;

		r = (x * x) + (y * y);
		res = (Math.sin(r) / r) / (9 * Math.cos(x / (y + 0.02)));

		return res;
	}

	/* Point color as a function of location */
	public Color calcColor(double x, double y, double z) {
		Color res;

		/* NOTE:
		 * Coloration is done in Hue Saturation Brightness (HSB, also known as
		 * HSV) in order to keep all the colors vibrant. RGB as a function of
		 * (x, y, z) resulted in washed out colors that made the whole graph
		 * difficult to read.
		 */

		res = new Color(Color.HSBtoRGB(
			(float) ((x * y) / (4.0 * Math.PI * Math.PI)),
			(float) 1.0,
			(float) 1.0
		));

		return res;
	}
}
