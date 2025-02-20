/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFrames;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author User
 */
public class Documentation extends javax.swing.JFrame {

    String DocumentTypeZ, BillOfLadingZ, CertificateOfOriginZ, PackingListZ, CommercialInvoiceZ, HealthCertificateZ, TestCertificateZ, Adminz;
    int DocumentIDZ, ShipmentIDZ;
   
    DefaultTableModel model;
    
    public Documentation(String Adminz) {
	initComponents();
	this.Adminz = Adminz;
	setDocumentationDetailsTable();
    }

    public Documentation() {
	initComponents();
	setDocumentationDetailsTable();
    }

    //Clear Table Before Showing New Data
    public void clearTableData() {

	DefaultTableModel model = (DefaultTableModel) DocumentationTbl.getModel();
	model.setRowCount(0);

    }

    //Check Shipment ID Abailability
    public void getShipmentIDF() {

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    ShipmentIDZ = Integer.parseInt(txt_ShipmentID.getText());
	    String sql = "select ShipmentID from Shipment where ShipmentID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setInt(1, ShipmentIDZ);
	    ResultSet rs = pst.executeQuery();

	    if (rs.next()) {
		ShipmentValid.setText("Right");
		ShipmentValid.setForeground(Color.GREEN);
	    } else {
		JOptionPane.showMessageDialog(this, "Invalid Shipment ID!");//or you can use a lable to show the messege !!
		ShipmentValid.setText("Wrong");
		ShipmentValid.setForeground(Color.RED);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }
    
    
    
    //validate Inputs
    public boolean validateF() {

	boolean validZ = false;

	if (txt_ShipmentID.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Shipment ID");
	} else if(txt_BL.getText().equals("")){
	    JOptionPane.showMessageDialog(this, "Please Enter ( Unique ) Bill of Lading");
	}
	else {
	    validZ = true;
	}
	return validZ;

    }

  
    //set Documentation Table Details
    public void setDocumentationDetailsTable() {

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("SELECT * FROM Documentation");

	    while (rs.next()) {
		int DocumentationID = rs.getInt("DocumentID");
		int ShipmentID = rs.getInt("ShipmentID");
		String DCType = rs.getString("DocumentType");
		String BL = rs.getString("BillOfLading");
		String CO = rs.getString("CertificateOfOrigin");
		String PL = rs.getString("PackingList");
		String CI = rs.getString("CommercialInvoice");
		String HC = rs.getString("HealthCertificate");
		String TC = rs.getString("TestCertificate");
		
		Object[] obj = {DocumentationID, ShipmentID, DCType, BL, CO, PL, CI, HC, TC};

		model = (DefaultTableModel) DocumentationTbl.getModel();
		model.addRow(obj);

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    //Add Documentation In Database
    public boolean AddDocumentation() {
	boolean IsAdded = false;

	if(!txt_ShipmentID.getText().equals("")){
           ShipmentIDZ = Integer.parseInt(txt_ShipmentID.getText());
	}
	DocumentTypeZ = txt_DocumentType.getSelectedItem().toString();
	BillOfLadingZ = txt_BL.getText();
	CertificateOfOriginZ = txt_CO.getText();
	PackingListZ = txt_PL.getText();
	CommercialInvoiceZ = txt_CI.getText();
	HealthCertificateZ = txt_HC.getText();
	TestCertificateZ = txt_TC.getText();
	
//..................................//	
if (validateF() == true) {
	if (ShipmentValid.getText().equals("Right")) {
	    try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		String sql = "insert into Documentation values(?,?,?,?,?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, ShipmentIDZ);
		pst.setString(2, DocumentTypeZ);
		pst.setString(3, BillOfLadingZ);
		pst.setString(4, CertificateOfOriginZ);
		pst.setString(5, PackingListZ);
		pst.setString(6, CommercialInvoiceZ);
		pst.setString(7, HealthCertificateZ);
		pst.setString(8, TestCertificateZ);
		

		int rowCount = pst.executeUpdate();

		if (rowCount > 0) {
		    IsAdded = true;
		} else {
		    IsAdded = false;
		}

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	
	else if (ShipmentValid.getText().equals("Wrong")) {
	    JOptionPane.showMessageDialog(this, "Invalid Shipment ID ");
	} 
	
    }

	return IsAdded;
    }

    //Update Documentation Table Details   
    public boolean UpdateDocumentation() {

	boolean IsUpdated = false;
        
	if(!txt_DocumentID.getText().equals("")){
           DocumentIDZ = Integer.parseInt(txt_DocumentID.getText());
	}
	if(!txt_ShipmentID.getText().equals("")){
           ShipmentIDZ = Integer.parseInt(txt_ShipmentID.getText());
	}
	DocumentTypeZ = txt_DocumentType.getSelectedItem().toString();
	BillOfLadingZ = txt_BL.getText();
	CertificateOfOriginZ = txt_CO.getText();
	PackingListZ = txt_PL.getText();
	CommercialInvoiceZ = txt_CI.getText();
	HealthCertificateZ = txt_HC.getText();
	TestCertificateZ = txt_TC.getText();

	if (validateF() == true) {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    String sql = "Update Documentation set ShipmentID = ?, DocumentType = ?, BillOfLading = ?, CertificateOfOrigin = ?, PackingList = ?, CommercialInvoice = ?, HealthCertificate = ?, TestCertificate = ? where DocumentID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);
	        pst.setInt(1, ShipmentIDZ);
		pst.setString(2, DocumentTypeZ);
		pst.setString(3, BillOfLadingZ);
		pst.setString(4, CertificateOfOriginZ);
		pst.setString(5, PackingListZ);
		pst.setString(6, CommercialInvoiceZ);
		pst.setString(7, HealthCertificateZ);
		pst.setString(8, TestCertificateZ);
		pst.setInt(9, DocumentIDZ);

	    int rowCount = pst.executeUpdate();

	    if (rowCount > 0) {
		IsUpdated = true;
	    } else {
		IsUpdated = false;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	}

	return IsUpdated;
    }

    //Delete Documentation Details
    public boolean DeteleDocumentation() {

	boolean IsDeleted = false;

	DocumentIDZ = Integer.parseInt(txt_DocumentID.getText());

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    String sql = "Delete from Documentation where DocumentID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);

	    pst.setInt(1, DocumentIDZ);

	    int rowCount = pst.executeUpdate();

	    if (rowCount > 0) {
		IsDeleted = true;
	    } else {
		IsDeleted = false;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return IsDeleted;
    }
    
    public void ClearF(){
	txt_DocumentID.setText("");
	txt_ShipmentID.setText("");
	txt_BL.setText("");
	txt_CO.setText("");
	txt_PL.setText("");
	txt_CI.setText("");
	txt_HC.setText("");
	txt_TC.setText("");
	ShipmentValid.setText("");	
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        backButton = new javax.swing.JLabel();
        homeCloseButton = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DocumentationTbl = new rojeru_san.complementos.RSTableMetro();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_DocumentID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_ShipmentID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_BL = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_CO = new javax.swing.JTextField();
        AddBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        DeleteBtn = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();
        ClearBtn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txt_HC = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_CI = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_PL = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_TC = new javax.swing.JTextField();
        txt_DocumentType = new javax.swing.JComboBox<>();
        ShipmentValid = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/BackIcon.png"))); // NOI18N
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backButtonMouseClicked(evt);
            }
        });

        homeCloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/close.png"))); // NOI18N
        homeCloseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeCloseButtonMouseClicked(evt);
            }
        });

        DocumentationTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Document ID", "Shipment ID", "Document Type", "Bill of Lading", "Certificate Of Origin", "Packing List", "Commercial Invoice", "Health Certificate", "Test Certificate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        DocumentationTbl.setColorBackgoundHead(new java.awt.Color(31, 110, 140));
        DocumentationTbl.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        DocumentationTbl.setColorFilasForeground2(new java.awt.Color(12, 12, 12));
        DocumentationTbl.setColorSelBackgound(new java.awt.Color(255, 133, 81));
        DocumentationTbl.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        DocumentationTbl.setFuenteFilas(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        DocumentationTbl.setFuenteHead(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DocumentationTbl.setIntercellSpacing(new java.awt.Dimension(0, 0));
        DocumentationTbl.setRowHeight(28);
        DocumentationTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DocumentationTblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(DocumentationTbl);

        jPanel3.setBackground(new java.awt.Color(31, 110, 140));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Documentation");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jLabel1)
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        txt_DocumentID.setEditable(false);
        txt_DocumentID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_DocumentID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DocumentIDActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Document ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Shipment ID");

        txt_ShipmentID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_ShipmentID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_ShipmentIDFocusLost(evt);
            }
        });
        txt_ShipmentID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ShipmentIDActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Bill Of Lading");

        txt_BL.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_BL.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_BLFocusLost(evt);
            }
        });
        txt_BL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_BLActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Certificate Of Origin");

        txt_CO.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_CO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_COFocusLost(evt);
            }
        });
        txt_CO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_COActionPerformed(evt);
            }
        });

        AddBtn.setBackground(new java.awt.Color(242, 151, 39));
        AddBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        AddBtn.setForeground(new java.awt.Color(255, 255, 255));
        AddBtn.setText("ADD");
        AddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(14, 41, 84));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 740, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(14, 41, 84));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        DeleteBtn.setBackground(new java.awt.Color(242, 151, 39));
        DeleteBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        DeleteBtn.setForeground(new java.awt.Color(255, 255, 255));
        DeleteBtn.setText("DELETE");
        DeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBtnActionPerformed(evt);
            }
        });

        UpdateBtn.setBackground(new java.awt.Color(255, 133, 81));
        UpdateBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        UpdateBtn.setForeground(new java.awt.Color(255, 255, 255));
        UpdateBtn.setText("UPDATE");
        UpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateBtnActionPerformed(evt);
            }
        });

        ClearBtn.setBackground(new java.awt.Color(255, 133, 81));
        ClearBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ClearBtn.setForeground(new java.awt.Color(255, 255, 255));
        ClearBtn.setText("Clear");
        ClearBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearBtnMouseClicked(evt);
            }
        });
        ClearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearBtnActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Health Certificate");

        txt_HC.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_HC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_HCFocusLost(evt);
            }
        });
        txt_HC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_HCActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Document Type");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Commercial Invoice");

        txt_CI.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_CI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CIActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Packing List");

        txt_PL.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_PL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PLActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("Test Certificate");

        txt_TC.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_TC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TCActionPerformed(evt);
            }
        });

        txt_DocumentType.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_DocumentType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Export", "Import" }));

        ShipmentValid.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ShipmentValid.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1471, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 8, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(452, 452, 452)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(homeCloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel13)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_ShipmentID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_DocumentID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_DocumentType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_BL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_CO, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ShipmentValid, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel11))
                                .addGap(60, 60, 60)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_TC)
                                    .addComponent(txt_HC, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_PL, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_CI, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(73, 73, 73)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(backButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(homeCloseButton)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_DocumentID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_ShipmentID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(ShipmentValid))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_DocumentType, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_BL, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(265, 265, 265)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txt_CO, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_PL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_CI, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_HC, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txt_TC, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1530, 828));

        setSize(new java.awt.Dimension(1523, 828));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseClicked
	if("Admin".equals(Adminz)){
	HomeFrame ob = new HomeFrame(Adminz);
	ob.setVisible(true);
	this.dispose();    
	}
	else{
	HomeFrame ob = new HomeFrame();
	ob.setVisible(true);
	this.dispose();
	}


    }//GEN-LAST:event_backButtonMouseClicked

    private void homeCloseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeCloseButtonMouseClicked
	System.exit(0);
    }//GEN-LAST:event_homeCloseButtonMouseClicked

    private void txt_DocumentIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DocumentIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_DocumentIDActionPerformed

    private void txt_ShipmentIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ShipmentIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_ShipmentIDActionPerformed

    private void txt_BLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_BLActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_BLActionPerformed

    private void txt_COActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_COActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_COActionPerformed

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
	if (AddDocumentation() == true) {
	    JOptionPane.showMessageDialog(this, "Document Added Successfully!");
	    clearTableData();
	    setDocumentationDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Document Addition Failed!");
	}

    }//GEN-LAST:event_AddBtnActionPerformed

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
	if (DeteleDocumentation() == true) {
	    JOptionPane.showMessageDialog(this, "Document Deleted Successfully!");
	    clearTableData();
	    setDocumentationDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Document Delete Failed!");
	}
    }//GEN-LAST:event_DeleteBtnActionPerformed

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed
	if (UpdateDocumentation() == true) {
	    JOptionPane.showMessageDialog(this, "Document Updated Successfully!");
	    clearTableData();
	    setDocumentationDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Document Update Failed!");
	}
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void ClearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearBtnActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_ClearBtnActionPerformed

    private void txt_HCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_HCActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_HCActionPerformed

    private void txt_CIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CIActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_CIActionPerformed

    private void txt_PLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PLActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_PLActionPerformed

    private void DocumentationTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DocumentationTblMouseClicked
	int rowNo = DocumentationTbl.getSelectedRow();
	TableModel model = DocumentationTbl.getModel();

	txt_DocumentID.setText(model.getValueAt(rowNo, 0).toString());
	txt_ShipmentID.setText(model.getValueAt(rowNo, 1).toString());
	txt_DocumentType.setSelectedItem(model.getValueAt(rowNo, 2).toString());
	txt_BL.setText(model.getValueAt(rowNo, 3).toString());
	txt_CO.setText(model.getValueAt(rowNo, 4).toString());
	txt_PL.setText(model.getValueAt(rowNo, 5).toString());
	txt_CI.setText(model.getValueAt(rowNo, 6).toString());
	txt_HC.setText(model.getValueAt(rowNo, 7).toString());
	txt_TC.setText(model.getValueAt(rowNo, 8).toString());

    }//GEN-LAST:event_DocumentationTblMouseClicked

    private void ClearBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearBtnMouseClicked
	ClearF();

    }//GEN-LAST:event_ClearBtnMouseClicked

    private void txt_TCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TCActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_TCActionPerformed

    private void txt_ShipmentIDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_ShipmentIDFocusLost
	if (!txt_ShipmentID.getText().equals("")) {
	    getShipmentIDF();
	}
	else{
	  ShipmentValid.setText("");
	}
    }//GEN-LAST:event_txt_ShipmentIDFocusLost

    private void txt_BLFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_BLFocusLost
	
    }//GEN-LAST:event_txt_BLFocusLost

    private void txt_COFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_COFocusLost
     
    }//GEN-LAST:event_txt_COFocusLost

    private void txt_HCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_HCFocusLost
        	
	
    }//GEN-LAST:event_txt_HCFocusLost

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
	    java.util.logging.Logger.getLogger(Documentation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(Documentation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(Documentation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(Documentation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>
	//</editor-fold>
	//</editor-fold>
	//</editor-fold>
	//</editor-fold>
	//</editor-fold>
	//</editor-fold>
	//</editor-fold>

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new Documentation().setVisible(true);
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton ClearBtn;
    private javax.swing.JButton DeleteBtn;
    private rojeru_san.complementos.RSTableMetro DocumentationTbl;
    private javax.swing.JLabel ShipmentValid;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JLabel backButton;
    private javax.swing.JLabel homeCloseButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_BL;
    private javax.swing.JTextField txt_CI;
    private javax.swing.JTextField txt_CO;
    private javax.swing.JTextField txt_DocumentID;
    private javax.swing.JComboBox<String> txt_DocumentType;
    private javax.swing.JTextField txt_HC;
    private javax.swing.JTextField txt_PL;
    private javax.swing.JTextField txt_ShipmentID;
    private javax.swing.JTextField txt_TC;
    // End of variables declaration//GEN-END:variables
}
