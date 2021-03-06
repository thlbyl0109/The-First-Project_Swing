package com.javalec.admin;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.javalec.base.MainMenu;
import com.javalec.base.ShareVar;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

public class AdminNoticeWrite extends JDialog {
	private JLabel lblNewLabel_4_1;
	private JTextField tfInserttitle;
	private JButton btnNoticeInsert;
	private JButton btnNoticeDelete;
	private JLabel lblNewLabel_4_1_1;
	private JScrollPane scroll_2;
	private JTextArea tfInserttext;
	private JButton btnNoticeCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminNoticeWrite dialog = new AdminNoticeWrite();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public AdminNoticeWrite() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				scroll_2.getVerticalScrollBar().setValue(scroll_2.getVerticalScrollBar().getMinimum());
					if(ShareVar.noticeMenuClick == 1) {
					getBtnNoticeDelete().setVisible(false);
					getBtnNoticeCancel().setVisible(false);
					}
					if(ShareVar.noticeMenuClick == 0) {
						getBtnNoticeDelete().setVisible(false);
					}
			}
		});
		setBounds(100, 100, 450, 352);
		getContentPane().setLayout(null);
		getContentPane().add(getLblNewLabel_4_1());
		getContentPane().add(getTfInserttitle());
		getContentPane().add(getBtnNoticeInsert());
		getContentPane().add(getBtnNoticeDelete());
		getContentPane().add(getLblNewLabel_4_1_1());
		getContentPane().add(getScroll_2());
		getContentPane().add(getBtnNoticeCancel());
		setLocationRelativeTo(null);
		setTitle("copyright © 1조 - 김태현, 박경미, 송예진, 최지석 all rights reserved.");

	}

	private JLabel getLblNewLabel_4_1() {
		if (lblNewLabel_4_1 == null) {
			lblNewLabel_4_1 = new JLabel("제     목");
			lblNewLabel_4_1.setBounds(27, 56, 61, 16);
		}
		return lblNewLabel_4_1;
	}
	private JTextField getTfInserttitle() {
		if (tfInserttitle == null) {
			tfInserttitle = new JTextField();
			tfInserttitle.setColumns(10);
			tfInserttitle.setBounds(78, 51, 348, 26);
		}
		return tfInserttitle;
	}
	
	
	private JLabel getLblNewLabel_4_1_1() {
		if (lblNewLabel_4_1_1 == null) {
			lblNewLabel_4_1_1 = new JLabel("공 지 사 항");
			lblNewLabel_4_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 17));
			lblNewLabel_4_1_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_4_1_1.setBounds(172, 13, 106, 26);
		}
		return lblNewLabel_4_1_1;
	}
	
	private JButton getBtnNoticeInsert() {
		if (btnNoticeInsert == null) {
			btnNoticeInsert = new JButton("완 료");
			btnNoticeInsert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					check();
				}
			});
			btnNoticeInsert.setBounds(337, 295, 89, 29);
		}
		return btnNoticeInsert;
	}
	
	
	private JButton getBtnNoticeDelete() {
		if (btnNoticeDelete == null) {
			btnNoticeDelete = new JButton("삭제");
			btnNoticeDelete.setForeground(Color.RED);
			btnNoticeDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?","Confirmation", JOptionPane.YES_NO_OPTION) ==0) {
					MainMenu mainMenu = new MainMenu();
					mainMenu.noticeDelete();
					AdminNoticeWrite.this.dispose();
					}
				}
			});
			btnNoticeDelete.setBounds(166, 295, 89, 29);
		}
		return btnNoticeDelete;
	}
			
	//-------------------------------------		
	// 공지사항 글쓰기
	public void noticeInsertAction() {
		String noticeTitle = tfInserttitle.getText().trim();
		String noticeText = tfInserttext.getText().trim();	
		
		DBadminNoitceAction dbAction = new DBadminNoitceAction(noticeTitle, noticeText);
		
		boolean msg = dbAction.noticeInsertAction();

			if(msg ==true){
				JOptionPane.showMessageDialog(this, "입력이 완료되었습니다.");
			}else {
				JOptionPane.showMessageDialog(this, "입력을 다시해주세요.");
				
			}
			
	
		
	}
	
	//-------------------------------------
	// 공지사항 미입력 항목 check
	private int insertFieldCheckNotice(){
		int i = 0;
		if(tfInserttitle.getText().length() == 0){
			i++;
			tfInserttitle.requestFocus();
		}
		
		if(tfInserttext.getText().length() == 0){
			i++;
			tfInserttext.requestFocus();
		}
		
		return i;
	}

	
	//-------------------------------------
	// 공지사항 table 클릭 시 해당 내용 출력
	public void clickPrintAction() {
		tfInserttext.setEditable(false);
		tfInserttitle.setEditable(false);
		tfInserttitle.setText(ShareVar.noticeTitle);
		tfInserttext.setText(ShareVar.noticeText);
		
	}
	
	
	
	//-------------------------------------
	// recipe list에서 버튼 클릭 시 저장된 내용 출력 기능
	public void updatePrintAction() {
		DBadminNoitceAction dBadminNoitceAction = new DBadminNoitceAction();
		BeanAdminNotice bean = dBadminNoitceAction.updateNotice();
		
		tfInserttext.setEditable(false);
		tfInserttitle.setEditable(false);
		tfInserttitle.setText(bean.getNoticetitle());
		tfInserttext.setText(bean.getNoticetext());
	       
		} 
	
	
	public void check() {
		switch(ShareVar.noticeMenuClick) {
		case 0:
			getBtnNoticeDelete().setVisible(true);
			int i_chk = insertFieldCheckNotice();
			if(i_chk == 0){
				noticeInsertAction();
				MainMenu recipeList = new MainMenu();
				recipeList.NoticeTableInit();
				recipeList.noticeSearchAction();
				AdminNoticeWrite.this.dispose();
				

			}else {
				JOptionPane.showMessageDialog(null, " 한글자 이상 입력하세요 !");
			}	
			break;
			
		case 1:
				AdminNoticeWrite.this.dispose();
				break;
		case 2:
				noticeClickUpdate();
				AdminNoticeWrite.this.dispose();
		break;
		
		}
	}
	
	//-------------------------------------
	// 관리자 메뉴 공지사항 내용 수정 시 해당 내용 출력
	public void adminPrintAction() {
		tfInserttext.setEditable(true);
		tfInserttitle.setEditable(true);
		tfInserttitle.setText(ShareVar.noticeTitle);
		tfInserttext.setText(ShareVar.noticeText);
		
	}
	
	//-------------------------------------
	// notice 목록 수정 method
	public void noticeClickUpdate() {
		
		String noticeTitle = tfInserttitle.getText().trim();
		String noticeText = tfInserttext.getText().trim();
		
		DBadminNoitceAction dbAction = new DBadminNoitceAction();
		
		boolean msg = dbAction.noticeUpdateAction(ShareVar.noticeTableClickNum, noticeTitle, noticeText);
		
		if(msg == true) {
			JOptionPane.showMessageDialog(this, "수정이 완료되었습니다.");
		} else {
			JOptionPane.showMessageDialog(null, "수정이 불가합니다.");
		}
		
		
	}
	
	
	private JScrollPane getScroll_2() {
		if (scroll_2 == null) {
			scroll_2 = new JScrollPane();
			scroll_2.setBounds(37, 89, 372, 195);
			scroll_2.setViewportView(getTfInserttext());


		}
		return scroll_2;
	}
	private JTextArea getTfInserttext() {
		if (tfInserttext == null) {
			tfInserttext = new JTextArea();
		}
		return tfInserttext;
	}
	private JButton getBtnNoticeCancel() {
		if (btnNoticeCancel == null) {
			btnNoticeCancel = new JButton("취 소");
			btnNoticeCancel.setForeground(Color.BLUE);
			btnNoticeCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AdminNoticeWrite.this.dispose();
				}
			});
			btnNoticeCancel.setBounds(251, 295, 89, 29);
		}
		return btnNoticeCancel;
	}
}
