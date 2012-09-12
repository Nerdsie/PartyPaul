package PartyPaul;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

public class PartyPaul extends Applet implements Runnable  {
	private static final long serialVersionUID = 1L;
	public int width = 640;
	public int height = 480;
	public int yFocOff = 0, yFocDir = 1;
	
	public Image backBuffer;
	public static Graphics2D canvas;
	
	public boolean running = false;
	public static boolean focus = false;
	public int latestLevel = 1;
	
	public static Player player = new Player();
	public Enemy enemy = new Enemy(this);
	public ArrayList<Object> tokens = new ArrayList<Object>();
	public static ArrayList<Particle> parts = new ArrayList<Particle>();
	public static ArrayList<Particle> toDie = new ArrayList<Particle>();
	
	public static boolean sounds = true;
	
	public static int xD = 1, yD = 0;
	
	//0 = in game, 1 = just lost;
	public int state = 0;
	
	public int level = 1;
	public int score = 0;
	
	public void init(){
		
	}
	
	public void start(){
		this.setSize( width, height );
		
		running = true;
		
		backBuffer = createImage(this.width, this.height);
		canvas = (Graphics2D) backBuffer.getGraphics();
		
		this.addKeyListener(new PartyKeyListener(this));
		this.addFocusListener(new PartyPaulFocusListener());
		
		new Thread(this).start();

		player.reset();
		enemy.reset();
		
		this.requestFocus();
	}
	
	public void render(){
		canvas.drawImage(Images.BG, 0, 0, 640, 480, null);
		
		canvas.setFont(new Font("Arial", 1, 28));
		if(state == 1){
			canvas.setColor(new Color(0, 0, 0, 200));
			canvas.fillRect(0, 0, width, height);
			
			drawC3DString("You made it to level " + latestLevel + "!", Color.RED, height / 2 - 16, 1);
			drawC3DString("You got " + score + " points!", Color.RED, height / 2 + 16, 1);
			drawC3DString("Press 'R' to restart.", Color.RED, height / 2 + 32 + 16, 1);
			
			return;
		}
		
		for(Object i: tokens){
			i.render(canvas);
		}

		draw3DString("Remaining: " + tokens.size() + " | Score: " + score, Color.GRAY, 8, 30, 1);
		draw3DString("Remaining: " + tokens.size() + " | Score: " + score, Color.CYAN, 6, 28, 1);
		
		player.render(canvas);
		enemy.render(canvas);
		
		for(Particle p: parts){
			p.render();
		}

		if(!focus){
			canvas.setColor(new Color(0, 0, 0, 200));
			canvas.fillRect(0, 0, width, height);

			int xOff = -135, yOff = yFocOff - 10;
			
			canvas.setFont(new Font("Arial", 1, 30));
			canvas.setColor(Color.GRAY);
			canvas.drawString("Click Here to Play!", width / 2 + 2 + xOff, height / 2 + 2 + yOff);
			canvas.setColor(Color.WHITE);
			canvas.drawString("Click Here to Play!", width / 2 + xOff, height / 2 + yOff);
			
			repaint();
			
			return;
		}
	}
	
	public void setup(){
		tokens.clear();
		
		for(int i = 0; i < 10 * level; i++){
			Random r = new Random();
			int x = r.nextInt(width - 50);
			int y = r.nextInt(height - 50);
			
			tokens.add(new Object(x, y));
		}
	}
	
	public void checkTouching(){
		ArrayList<Object> toKill = new ArrayList<Object>();
		for(Object x: tokens){
			if(isTouching(player, x)){
				toKill.add(x);
				
				parts.add(new Particle());
				
				score+=10;
			}
		}
		
		for(Object o: toKill){
			tokens.remove(o);
		}
		
		if(toKill.size()>0)
			Sound.coin.play();
		
		toKill.clear();
	}
	
	public void checkWin(){
		if(tokens.size()==0){
			//You win!
			Sound.win.play();
			level++;
			latestLevel = level;
			setup();
			enemy.reset();
			player.reset();
			enemy.speed += 0.4;
			score+=100;
			parts.add(new Particle("+100", Color.BLUE));
		}
	}
	
	public void checkLose(){
		if(isTouching(player, enemy)){
			Sound.die.play();
			enemy.reset();
			player.reset();
			level = 1;
			enemy.speed = 1;
			state = 1;
			setup();
			//You lose :(
		}
	}
	
	public void updatePosition(){
		if(xD > 0)
			player.x += player.speed;
		if(xD < 0)
			player.x -= player.speed;
		if(yD > 0)
			player.y += player.speed;
		if(yD < 0)
			player.y -= player.speed;
	}
	
	public void update(){
		checkTouching();
		checkWin();
		checkLose();
		updatePosition();
		
		player.tick();
		enemy.tick();
		
		for(Particle p: parts){
			p.tick();
		}
		
		for(Particle p: toDie){
			parts.remove(p);
		}
		
		toDie.clear();
	}
	
	public void tick(){
		if(!focus){
			yFocOff+=yFocDir;
			
			if(yFocOff > 20){
				yFocDir = -yFocDir;
			}
			if(yFocOff < 0){
				yFocDir = -yFocDir;
			}
			
			return;
		}

		if(state == 1){
			return;
		}
		
		if(player.x <= -50){
			player.x = width - 1;
		}
		
		if(player.y <= -50){
			player.y = height - 1;
		}
		
		if(player.x >= width){
			player.x = -49;
		}
		
		if(player.y >= height){
			player.y = -49;
		}
		
		update();
	}
	
	public int getSW(String s){
		FontMetrics FM = getFontMetrics(canvas.getFont());
		return FM.stringWidth(s);
	}
	
	public void drawCString(String m, int y){
		canvas.drawString(m, getWidth() / 2 - getSW(m) / 2, y);
	}
	
	public void drawCString(String m, int x, int y){
		canvas.drawString(m, getWidth() / 2 - getSW(m) / 2 + x, y);
	}
	
	public void drawC3DString(String m, int y){
		canvas.setColor(Color.GRAY);
		drawCString(m, 2, y + 2);
		canvas.setColor(Color.WHITE);
		drawCString(m, y);
	}

	public void drawC3DString(String m, Color f, int y, int sep){
		canvas.setColor(f.darker().darker().darker());
		drawCString(m, sep, y + sep);
		canvas.setColor(f);
		drawCString(m, y);
	}

	public void draw3DString(String m, int x, int y, int sep){
		canvas.setColor(Color.GRAY);
		canvas.drawString(m, x + sep, y + sep);
		canvas.setColor(Color.WHITE);
		canvas.drawString(m, x, y);
	}

	public void draw3DString(String m, Color f, int x, int y, int sep){
		canvas.setColor(f.darker().darker().darker());
		canvas.drawString(m, x + sep, y + sep);
		canvas.setColor(f);
		canvas.drawString(m, x, y);
	}
	
	public void run(){
		setup();
		
		while(running){
			tick();
			render();
			repaint();
			
			try{
				Thread.sleep(17);
			}catch(Exception e){}
		}
	}
	
	public void paint(Graphics g){
		g.drawImage(backBuffer, 0, 0, null);
	}
	
	public void update(Graphics g){	
		paint(g);
	}
	
	public boolean isTouching(Entity i, Entity ii){
		if(i.x + i.w > ii.x && i.x < ii.x + ii.w){
			if(i.y > ii.y && i.y < ii.y + ii.h){
				return true;
			}
			if(i.y + i.h > ii.y && i.y< ii.y + ii.h){
				return true;
			}
		}
		
		return false;
	}
}
