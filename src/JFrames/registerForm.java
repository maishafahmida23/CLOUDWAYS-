/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrames;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import javax.swing.JOptionPane;


public class registerForm extends javax.swing.JFrame {

    public registerForm() {
	initComponents();
    }

    
    String UserIDz, Namez, Passwordz, Emailz, Questionz, Answerz, Phonez, UserIDCheckz;
    
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        homeCloseButton = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        signupButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txt_userid = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_confirmPassword = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        txt_answer = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        redirectLogin = new javax.swing.JLabel();
        txt_question = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(300, 155));
        setMinimumSize(new java.awt.Dimension(1270, 730));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeCloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/close.png"))); // NOI18N
        homeCloseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeCloseButtonMouseClicked(evt);
            }
        });
        jPanel1.add(homeCloseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1480, 10, -1, -1));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setText("Create Account");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 80, -1, -1));

        signupButton.setBackground(new java.awt.Color(0, 51, 255));
        signupButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        signupButton.setForeground(new java.awt.Color(255, 255, 255));
        signupButton.setText("Sign Up");
        signupButton.setBorder(null);
        signupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupButtonActionPerformed(evt);
            }
        });
        jPanel1.add(signupButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 670, 382, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/backRegister.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-270, 0, 1150, 940));

        txt_userid.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jPanel1.add(txt_userid, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 170, 320, 35));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("User ID :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 180, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setText("Password :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 300, -1, -1));
        jPanel1.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 290, 320, 35));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel5.setText("Name :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 240, -1, -1));

        txt_username.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jPanel1.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 230, 320, 35));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setText("Confirm Password :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 360, -1, -1));
        jPanel1.add(txt_confirmPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 350, 320, 35));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setText("Email :");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 420, -1, -1));

        txt_answer.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jPanel1.add(txt_answer, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 530, 320, 35));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setText("Answer :");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 540, -1, -1));

        txt_phone.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jPanel1.add(txt_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 590, 320, 35));

        redirectLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        redirectLogin.setText("Already Memeber? Login Now!");
        redirectLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                redirectLoginMouseClicked(evt);
            }
        });
        jPanel1.add(redirectLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 720, 220, 36));

        txt_question.setFont(new java.awt.Font("Nirmala UI", 1, 14)); // NOI18N
        txt_question.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Question", "What is the name of your first pet?", "In which city were you born?", "What is your favorite color?", "Who was your childhood best friend?" }));
        txt_question.setToolTipText("");
        jPanel1.add(txt_question, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 470, 320, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel9.setText("Phone :");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 600, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel10.setText("Security Question :");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 480, -1, -1));

        txt_email.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jPanel1.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 410, 320, 35));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1550, 830));

        setSize(new java.awt.Dimension(1523, 828));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void homeCloseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeCloseButtonMouseClicked
        System.exit(0);
    }//GEN-LAST:event_homeCloseButtonMouseClicked

    //Row Count if it is grater than 2 or not!
     public boolean RowCountF() {
	 boolean Countz = false;
	 
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("select count(ID) as 'RowCountz' from [User]");

	    if (rs.next()) {
		int Cnt = rs.getInt("RowCountz");
		if(Cnt < 3){
		  Countz = true;
		}
	    } 

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return Countz;
    }
     
        public boolean validateF() {
	
	boolean validZ = false;

	if (txt_userid.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter User ID");
	}
	else if (txt_username.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Name");
	}
	else if (txt_password.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Password");
	}
	else if (txt_confirmPassword.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Confirm Password");
	}
	else if (!txt_confirmPassword.getText().equals(txt_password.getText())) {
	    JOptionPane.showMessageDialog(this, "Password Do not Match!");
	}
	else if (txt_email.getText().equals("") || !txt_email.getText().matches("^.+@.+\\..+$")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Email Address");
	} else if (txt_question.getSelectedItem().toString().equals("Select Question")) {
	    JOptionPane.showMessageDialog(this, "Please Choose a Question");
	}
	else if (txt_answer.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Answer the Question");
	}
	else if (txt_phone.getText().equals("") || !txt_phone.getText().matches("\\d{11}")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid 11 digit Phone Number");
	} 
	else {
	    validZ = true;
	}
	return validZ;

    }
	
	
	
	public boolean UserIDF(){
	    
          boolean uf = false;
	  
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    UserIDCheckz = txt_userid.getText();
	    String sql = "select UserID from [User] where UserID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setString(1, UserIDCheckz);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
		uf = true;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
         return uf;
    }
	
	public void ClearF() {
	txt_userid.setText("");
	txt_username.setText("");
	txt_password.setText("");
	txt_confirmPassword.setText("");
	txt_email.setText("");
	txt_question.setSelectedItem("Select Question");
	txt_answer.setText("");
	txt_userid.setText("");
	txt_phone.setText("");
	

    }
    
    
    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupButtonActionPerformed
  
	     
	UserIDz = txt_userid.getText();
	Namez = txt_username.getText();
	Passwordz = txt_password.getText();
	Emailz = txt_email.getText();
	Questionz = txt_question.getSelectedItem().toString();
	Answerz = txt_answer.getText();
	Phonez = txt_phone.getText();
	
	if (RowCountF()== false){
	     JOptionPane.showMessageDialog(this, "Maximum User Exist!");
	}
	else if(validateF() == true)
	{
	       if(UserIDF() == true){
		   JOptionPane.showMessageDialog(this, "User ID Already Exist!");
	       }
	       else{
		   
		   try {
		       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		       Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		       String sql = "insert into [User] values(?,?,?,?,?,?,?)";
		       PreparedStatement pst = con.prepareStatement(sql);
		       pst.setString(1, UserIDz);
		       pst.setString(2, Namez);
		       pst.setString(3, Passwordz);
		       pst.setString(4, Emailz);
		       pst.setString(5, Questionz);
		       pst.setString(6, Answerz);
		       pst.setString(7, Phonez);

		       int rowCount = pst.executeUpdate();

		       if (rowCount > 0) {
			   JOptionPane.showMessageDialog(this, "User Registration Completed!");
			   ClearF();
		       } else {
			   JOptionPane.showMessageDialog(this, "Couldn't Connect to Server!");
		       }

		   } catch (Exception e) {
		       e.printStackTrace();
		   }
		   
	       }
	}
	    
	
    }//GEN-LAST:event_signupButtonActionPerformed

    private void redirectLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_redirectLoginMouseClicked
        Login obj = new Login();
	obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_redirectLoginMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
	/* Set the Nimbus look and feel */
	//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
	/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
	 */
	try {
	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException ex) {
	    java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>
	//</editor-fold>

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new registerForm().setVisible(true);
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel homeCloseButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel redirectLogin;
    private javax.swing.JButton signupButton;
    private javax.swing.JTextField txt_answer;
    private javax.swing.JPasswordField txt_confirmPassword;
    private javax.swing.JTextField txt_email;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_phone;
    private javax.swing.JComboBox<String> txt_question;
    private javax.swing.JTextField txt_userid;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
