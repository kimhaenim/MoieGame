package MoieGame;
import java.util.Scanner;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.event.*;
class score{
	int cnt;
	public score() {
		cnt = 0;
	}
	public void start() {
		cnt++;
	}
	public void fever() {
		cnt+=2;
	}
	public String getscore() {
		return " 현재 점수: "+cnt+"점 !";
	}
	public String getscore_end() {
		return cnt+"";
	}
}
class feverstarttime extends Thread{
	int timer;
	Container c;
	JPanel feverstartPane;
	JPanel feverPane;
	JPanel moiePane;
	JLabel counter;
	public feverstarttime(int timer,Container c, JPanel feverstartPane, JPanel feverPane, JPanel moiePane, JLabel counter) {
		this.timer=timer;
		this.c=c;
		this.feverstartPane=feverstartPane;
		this.feverPane=feverPane;
		this.counter=counter;
	}
	public void run() {
		while(true) {
			if(timer==0) {
				counter.setText("0");
				break;
			}
			timer--;
			if(timer==10){
				feverstartPane.hide();
				c.add(feverPane);
			}
			System.out.println(timer);
			counter.setText(String.valueOf(timer));
		try {
			Thread.sleep(1000);
		}catch(InterruptedException e) {
			return;
		}
	}
}
}

class change_moie2 extends Thread{
	private JButton hole;
	ImageIcon moie2 = new ImageIcon("images/moie2.jpg");
	public change_moie2(JButton hole) {
		this.hole = hole;
	}
	public void run() {
		int n=0;
		int random = (int)(Math.random()*5000);
		while(true) {
			
			hole.setIcon(moie2);
			n++;
		try {
			Thread.sleep(random);
		}catch(InterruptedException e) {
			return;
		}
	}
}
}
class change_moie3 extends Thread{
	private JButton hole;
	ImageIcon moie3 = new ImageIcon("images/moie3.jpg");
	public change_moie3(JButton hole) {
		this.hole = hole;
	}
	public void run() {
		int n=0;
		int random = (int)(Math.random()*5000);
		while(true) {
			hole.setIcon(moie3);
			n++;
		try {
			Thread.sleep(random);
		}catch(InterruptedException e) {
			return;
		}
	}
}
}
class timeover extends Thread{
	private JLabel time;
	private JPanel moiePane;
	private JLabel end;
	public timeover(JLabel time, JPanel moiePane, JLabel end) {
		this.time = time;
		this.moiePane=moiePane;
		this.end= end;
	}
	public void run() {
		int n=30;
		while(true) {
			if(n==0) {
				moiePane.removeAll();
				moiePane.add(end); //남은시간이 0초가 되었을때 총점수를 알려주는 화면 깔끔하게 변경할것
				end.setSize(500,500);
				end.setLocation(250,250);
			}
			if(n<0)
				break;
			time.setText("남은시간 : "+n+"초");
			n--;
		try {
			Thread.sleep(1000);
		}catch(InterruptedException e) {
			return;
		}
	}
}
}
class frame extends JFrame{
	score s = new score();
	static Container c;
		JPanel score = new JPanel(new GridLayout(1,3,10,10));
		JPanel startPane = new JPanel();
			BufferedImage startimage = null;
			JButton start = new JButton("게임시작");
		static JPanel moiePane = new JPanel(new GridLayout(3,3,10,10));
			static JButton hole[]=new JButton[9];
			change_moie2 m2[]=new change_moie2[9];	
			change_moie3 m3[]=new change_moie3[9];
			change_moie3 m3_2[]=new change_moie3[8];
			ImageIcon moie1 = new ImageIcon("images/moie1.jpg");
			ImageIcon moie2 = new ImageIcon("images/moie2.jpg");
			ImageIcon moie3 = new ImageIcon("images/moie3.jpg");
			JLabel scorecount = new JLabel(s.getscore());
			JLabel tab = new JLabel("");
			JLabel time = new JLabel("");
			JLabel end = new JLabel("");
		static JPanel feverstartPane =new JPanel(new GridLayout(1,1,10,10));
			JLabel fevernotify = new JLabel(new ImageIcon("images/fevertimestart.jpg"));
		static JPanel feverPane = new JPanel(new GridLayout(3,3,0,0));
			static JButton feverhole[]=new JButton[8];
			static JLabel counter = new JLabel("13");
			Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image image = toolkit.getImage("images/hammer1.jpg");
	Image image2 = toolkit.getImage("images/hammer2.jpg");
	Point hotspot = new Point(0,0);
	Cursor cursor = toolkit.createCustomCursor(image, hotspot, "hammer1");
	class MyPanel extends JPanel{
		public void paint(Graphics g) {
			g.drawImage(startimage,0,0,null);
		}
	}
	public frame() {
		 c= getContentPane();
		setTitle("두더지 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setLayout(new BorderLayout());
		c.setBackground(Color.GREEN);
		score.setBackground(Color.PINK);
		moiePane.setBackground(Color.GREEN);
		setCursor(cursor);
		
		try {
			startimage = ImageIO.read(new File("images/gamestart.jpg"));
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "이미지 불러오기 실패");
			System.exit(0);
		}
		MyPanel panel = new MyPanel();
		panel.setSize(700,700);
		startPane.add(panel);
		startPane.setLayout(null);
		start.setFont(start.getFont().deriveFont(39.0f));
		start.setBackground(Color.PINK);
		start.addActionListener(new MyActionListener());
		start.setSize(200,100);
		start.setLocation(230, 500);
		startPane.add(start);
		
		for(int i=0; i<9; i++) {
			hole[i]= new JButton(moie1);
			hole[i].setBackground(Color.GREEN);
			hole[i].addActionListener(new MyActionListener());
			moiePane.add(hole[i]);
			m2[i] = new change_moie2(hole[i]);
			m3[i] = new change_moie3(hole[i]);
			m2[i].start();
			m3[i].start();
			moiePane.add(hole[i]);
		}
		timeover timethread = new timeover(time, moiePane, end);
		score.add(scorecount);
		score.add(tab);
		score.add(time);
		
		feverstartPane.add(fevernotify);
		for(int i=0; i<8; i++) {
			feverhole[i]= new JButton(moie3);
			feverhole[i].setBackground(Color.GREEN);
			feverhole[i].addActionListener(new MyActionListener());
			m3_2[i] = new change_moie3(feverhole[i]);
			m3_2[i].start();
		}
		feverPane.setBackground(Color.MAGENTA);
		counter.setFont(counter.getFont().deriveFont(120.0f));
		counter.setForeground(Color.WHITE);
		counter.setHorizontalAlignment(JLabel.CENTER);

		for(int i=0; i<4; i++) {
			feverPane.add(feverhole[i]);
			feverhole[i].addActionListener(new MyActionListener());
		}
		feverPane.add(counter); //피버타임 시간초
		for(int i=4; i<8; i++) {
			feverPane.add(feverhole[i]);
			feverhole[i].addActionListener(new MyActionListener());
		}
		c.add(startPane,BorderLayout.CENTER);
		setSize(700,700);
		setVisible(true);
		timethread.start();
		
	}
	class MyActionListener implements ActionListener{
		//두더지를 잡을 때마다 잡았다는 표시 할것
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==start) {
				startPane.hide();
				c.add(score, BorderLayout.NORTH);
				c.add(moiePane, BorderLayout.CENTER);
			}
			for(int i=0; i<9; i++) {
				if(e.getSource()==hole[i]) {	
					if(hole[i].getIcon().toString().equals("images/moie3.jpg")) {
						s.start();
						hole[i].setIcon(moie1);
					}
				}
			}
			for(int i=0; i<4; i++) {
				if(e.getSource()==feverhole[i]) {
					if(feverhole[i].getIcon().toString().equals("images/moie3.jpg")) {
						s.fever();
						feverhole[i].setIcon(moie1);
					}
				}
			}
			for(int i=4; i<8; i++) {
				if(e.getSource()==feverhole[i]) {
					if(feverhole[i].getIcon().toString().equals("images/moie3.jpg")) {
						s.fever();
						feverhole[i].setIcon(moie1);
					}
				}
			}
			scorecount.setText(s.getscore());
			end.setText("당신의 총 점수는 "+s.getscore_end()+"점입니다!");
			if(s.getscore_end().equals("20")||s.getscore_end().equals("120")){
				fever();
			}
			if(counter.getText().equals("0")) {
				feverPane.hide();
				moiePane.show();
			}
		}
	}
	class MyMouseListener extends MouseAdapter{
		public void mouseclicked(MouseEvent e) {
			System.out.println("click");
			Cursor cursor = toolkit.createCustomCursor(image, hotspot, "hammer2");
			setCursor(cursor); //마우스 모양 수정해야함. 뿅망치를 눌렀을때 변경 해결중
		}
	}
	public static void fever() {
		moiePane.hide();
		c.add(feverstartPane);
		int timer=13;
		feverstarttime ft = new feverstarttime(timer,c,feverstartPane, feverPane, moiePane, counter);
		ft.start();	
	}
}

public class MoieGame {
	public static void main(String[] args) {
		frame Frame = new frame();
	}
}
