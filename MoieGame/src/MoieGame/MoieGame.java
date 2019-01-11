package MoieGame;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
class score{
	int cnt;
	public score() {
		cnt = 0;
	}
	public void start() {
		cnt++;
	}
	public String getscore() {
		return " 현재 점수: "+cnt+"점 !";
	}
	public String getscore_end() {
		return cnt+"";
	}
}
class TimerThread0 extends Thread{
	private JButton hole;
	ImageIcon moie2 = new ImageIcon("images/moie2.jpg");
	public TimerThread0(JButton hole) {
		this.hole = hole;
	}
	public void run() {
		int n=0;
		int random = (int)(Math.random()*7500);
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

class TimerThread1 extends Thread{
	private JButton hole;
	ImageIcon moie3 = new ImageIcon("images/moie3.jpg");
	public TimerThread1(JButton hole) {
		this.hole = hole;
	}
	public void run() {
		int n=0;
		int random = (int)(Math.random()*7000);
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

class TimerThread3 extends Thread{
	private JLabel time;
	private JPanel moiePane;
	private JLabel end;
	private String cnt;
	public TimerThread3(JLabel time, JPanel moiePane, JLabel end) {
		this.time = time;
		this.moiePane=moiePane;
		this.end= end;
	}
	public void run() {
		int n=60;
		while(true) {
			if(n==0) {
				moiePane.removeAll();
				moiePane.add(end);
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
	Container c = getContentPane();
	JPanel score = new JPanel(new GridLayout(1,3,10,10));
	JPanel moiePane = new JPanel(new GridLayout(3,3,10,10));
	JButton hole[]=new JButton[9];
	TimerThread0 th0[]=new TimerThread0[9];	
	TimerThread1 th1[]=new TimerThread1[9];
	ImageIcon moie1 = new ImageIcon("images/moie1.jpg");
	ImageIcon moie2 = new ImageIcon("images/moie2.jpg");
	ImageIcon moie3 = new ImageIcon("images/moie3.jpg");
	JLabel hammer = new JLabel(new ImageIcon("images/뿅망치.jpg"));
	JLabel scorecount = new JLabel(s.getscore());
	JLabel tab = new JLabel("");
	JLabel time = new JLabel("");
	JLabel end = new JLabel("");
	public frame() {
		setTitle("두더지 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setLayout(new BorderLayout());
		c.setBackground(Color.GREEN);
		score.setBackground(Color.PINK);
		moiePane.setBackground(Color.GREEN);
		
		for(int i=0; i<9; i++) {
			hole[i]= new JButton(moie1);
			hole[i].setBackground(Color.GREEN);
			hole[i].addActionListener(new MyActionListener());
			moiePane.add(hole[i]);
			th0[i] = new TimerThread0(hole[i]);
			th1[i] = new TimerThread1(hole[i]);
			th0[i].start();
			th1[i].start();
			moiePane.add(hole[i]);
		}

		TimerThread3 timethread = new TimerThread3(time, moiePane, end);
		score.add(scorecount);
		score.add(tab);
		score.add(time);
		
		c.add(score, BorderLayout.NORTH);
		c.add(moiePane, BorderLayout.CENTER);
		setSize(700,700);
		setVisible(true);
		timethread.start();
	}
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<9; i++) {
				if(e.getSource()==hole[i]) {	
					if(hole[i].getIcon().toString().equals("images/moie3.jpg")) {
						s.start();
						hole[i].setIcon(moie1);
					}
			}
		}
			scorecount.setText(s.getscore());
			end.setText("당신의 총 점수는 "+s.getscore_end()+"점입니다!");
		}
	}
}
public class MoieGame {
	public static void main(String[] args) {
		frame Frame = new frame();
	}
}
