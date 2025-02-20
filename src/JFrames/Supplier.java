package JFrames;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author User
 */
public class Supplier extends javax.swing.JFrame {

    String SupplierNameZ, EmailZ, PhoneZ, MessengerTypeZ, MessengerIDZ, AddressZ, CountryZ, Adminz;
    int SupplierIDZ , ZipZ;
    DefaultTableModel model;

    public Supplier(String Adminz) {
	initComponents();
	this.Adminz = Adminz;
	setSupplierDetailsTable();
    }
    
    public Supplier() {
	initComponents();
	setSupplierDetailsTable();
    }

    //Clear Table Before Showing New Data
    public void clearTableData() {

	DefaultTableModel model = (DefaultTableModel) SupplierTbl.getModel();
	model.setRowCount(0);

    }

    //validate Inputs
    public boolean validateF() {

	boolean validZ = false;

	if (txt_SupplierName.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Supplier Name");
	} else if (!txt_Email.getText().equals("") && !txt_Email.getText().matches("^.+@.+\\..+$")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Email Address");
	} else if (!txt_Phone.getText().equals("") && !txt_Phone.getText().matches("\\d+")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Phone Number");
	} else if (txt_Address.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Supplier Address");
	} else if (txt_country.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Country");
	}
	else if (txt_zip.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Zip Code");
	}
	else {
	    validZ = true;
	}
	return validZ;

    }

    //set Supplier Table Details
    public void setSupplierDetailsTable() {

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("SELECT * FROM Supplier");

	    while (rs.next()) {
		int SupplierID = rs.getInt("SupplierID");
		String SupplierName = rs.getString("SupplierName");
		String Email = rs.getString("SupplierEmail");
		String Phone = rs.getString("SupplierPhone");
		String MesssengerType = rs.getString("SupplierInstantMessengerType");
		String MesssengerID = rs.getString("SupplierInstantMessengerValue");
		String Address = rs.getString("SupplierAddress");
		String Country = rs.getString("Country");
		int Zips = rs.getInt("Zip");

		Object[] obj = {SupplierID, SupplierName, Email, Phone, MesssengerType, MesssengerID, Address,  Country , Zips};

		model = (DefaultTableModel) SupplierTbl.getModel();
		model.addRow(obj);

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    //Add Supplier In Database
    public boolean AddSupplier() {
	boolean IsAdded = false;

	SupplierNameZ = txt_SupplierName.getText();
	EmailZ = txt_Email.getText();
	PhoneZ = txt_Phone.getText();
	MessengerTypeZ = txt_MessengerType.getSelectedItem().toString();
	MessengerIDZ = txt_MessengerID.getText();
	AddressZ = txt_Address.getText();
	CountryZ = txt_country.getText();
	if (!txt_zip.getText().equals("")){
	ZipZ = Integer.parseInt(txt_zip.getText());
	}

	if (validateF() == true) {
	    try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		String sql = "insert into Supplier values(?,?,?,?,?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, SupplierNameZ);
		pst.setString(2, EmailZ);
		pst.setString(3, PhoneZ);
		pst.setString(4, MessengerTypeZ);
		pst.setString(5, MessengerIDZ);
		pst.setString(6, AddressZ);
		pst.setString(7, CountryZ);
		pst.setInt(8, ZipZ);

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
	return IsAdded;
    }

    //Update Supplier Table Details   
    public boolean UpdateSupplier() {

	boolean IsUpdated = false;

	SupplierIDZ = Integer.parseInt(txt_SupplierID.getText());
	SupplierNameZ = txt_SupplierName.getText();
	EmailZ = txt_Email.getText();
	PhoneZ = txt_Phone.getText();
	MessengerTypeZ = txt_MessengerType.getSelectedItem().toString();
	MessengerIDZ = txt_MessengerID.getText();
	AddressZ = txt_Address.getText();
	CountryZ = txt_country.getText();
	if (!txt_zip.getText().equals("")){
	ZipZ = Integer.parseInt(txt_zip.getText());
	}
	if (validateF() == true) {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    String sql = "Update Supplier set SupplierName = ?, SupplierEmail = ?, SupplierPhone = ?, SupplierInstantMessengerType = ?, SupplierInstantMessengerValue = ?, SupplierAddress = ?, Country = ?, Zip = ? where SupplierID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setString(1, SupplierNameZ);
	    pst.setString(2, EmailZ);
	    pst.setString(3, PhoneZ);
	    pst.setString(4, MessengerTypeZ);
	    pst.setString(5, MessengerIDZ);
	    pst.setString(6, AddressZ);
	    pst.setString(7, CountryZ);
	    pst.setInt(8, ZipZ);
	    pst.setInt(9, SupplierIDZ);

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

    //Delete Supplier Details
    public boolean DeteleSupplier() {

	boolean IsDeleted = false;

	SupplierIDZ = Integer.parseInt(txt_SupplierID.getText());

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    String sql = "Delete from Supplier where SupplierID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);

	    pst.setInt(1, SupplierIDZ);

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

    public void ClearF() {

	txt_SupplierID.setText("");
	txt_SupplierName.setText("");
	txt_Email.setText("");
	txt_Phone.setText("");
	txt_MessengerID.setText("");
	txt_Address.setText("");
	txt_country.setText("");
	txt_zip.setText("");

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
        SupplierTbl = new rojeru_san.complementos.RSTableMetro();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_SupplierID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_SupplierName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_Phone = new javax.swing.JTextField();
        AddBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        DeleteBtn = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();
        ClearBtn = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txt_MessengerID = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_Address = new javax.swing.JTextField();
        txt_MessengerType = new javax.swing.JComboBox<>();
        txt_country = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_zip = new javax.swing.JTextField();

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

        SupplierTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Supplier Name", "Email", "Phone", "Messenger Type", "Messenger ID", "Address", "Country", "Zip"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SupplierTbl.setColorBackgoundHead(new java.awt.Color(31, 110, 140));
        SupplierTbl.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        SupplierTbl.setColorFilasForeground2(new java.awt.Color(12, 12, 12));
        SupplierTbl.setColorSelBackgound(new java.awt.Color(255, 133, 81));
        SupplierTbl.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        SupplierTbl.setFuenteFilas(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        SupplierTbl.setFuenteHead(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        SupplierTbl.setIntercellSpacing(new java.awt.Dimension(0, 0));
        SupplierTbl.setRowHeight(28);
        SupplierTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SupplierTblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(SupplierTbl);

        jPanel3.setBackground(new java.awt.Color(31, 110, 140));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Supplier");

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

        txt_SupplierID.setEditable(false);
        txt_SupplierID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_SupplierID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SupplierIDActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Supplier ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Supplier Name");

        txt_SupplierName.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_SupplierName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SupplierNameActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Email");

        txt_Email.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_Email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_EmailActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Phone");

        txt_Phone.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_Phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PhoneActionPerformed(evt);
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

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Instant Messenger ID");

        txt_MessengerID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_MessengerID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MessengerIDActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Instant Messenger Type");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Address");

        txt_Address.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_Address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AddressActionPerformed(evt);
            }
        });

        txt_MessengerType.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_MessengerType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "WhatsApp", "Skype", "WeChat" }));

        txt_country.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_country.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_countryActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("Country");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("Zip Code");

        txt_zip.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_zip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_zipActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                                .addComponent(txt_Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_SupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_SupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_zip, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_Address, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addComponent(txt_MessengerID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(txt_MessengerType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_country, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(142, 142, 142)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UpdateBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DeleteBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClearBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(131, 131, 131))
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
                                        .addGap(524, 524, 524)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(homeCloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(23, 23, 23))))
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_MessengerType, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_SupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_SupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel13)
                            .addComponent(txt_MessengerID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel15))
                            .addComponent(txt_Address))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel16))
                            .addComponent(txt_country))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_zip, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 88, Short.MAX_VALUE)
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

    private void txt_SupplierIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SupplierIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_SupplierIDActionPerformed

    private void txt_SupplierNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SupplierNameActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_SupplierNameActionPerformed

    private void txt_EmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_EmailActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_EmailActionPerformed

    private void txt_PhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PhoneActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_PhoneActionPerformed

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
	if (AddSupplier() == true) {
	    JOptionPane.showMessageDialog(this, "Supplier Added Successfully!");
	    clearTableData();
	    setSupplierDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Supplier Addition Failed!");
	}

    }//GEN-LAST:event_AddBtnActionPerformed

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
	if (DeteleSupplier() == true) {
	    JOptionPane.showMessageDialog(this, "Supplier Deleted Successfully!");
	    clearTableData();
	    setSupplierDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Supplier Delete Failed!");
	}
    }//GEN-LAST:event_DeleteBtnActionPerformed

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed
	if (UpdateSupplier() == true) {
	    JOptionPane.showMessageDialog(this, "Supplier Updated Successfully!");
	    clearTableData();
	    setSupplierDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Supplier Update Failed!");
	}
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void ClearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearBtnActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_ClearBtnActionPerformed

    private void txt_MessengerIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MessengerIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_MessengerIDActionPerformed

    private void txt_AddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AddressActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_AddressActionPerformed

    private void SupplierTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SupplierTblMouseClicked
	int rowNo = SupplierTbl.getSelectedRow();
	TableModel model = SupplierTbl.getModel();

	txt_SupplierID.setText(model.getValueAt(rowNo, 0).toString());
	txt_SupplierName.setText(model.getValueAt(rowNo, 1).toString());
	txt_Email.setText(model.getValueAt(rowNo, 2).toString());
	txt_Phone.setText(model.getValueAt(rowNo, 3).toString());
	txt_MessengerType.setSelectedItem(model.getValueAt(rowNo, 4).toString());
	txt_MessengerID.setText(model.getValueAt(rowNo, 5).toString());
	txt_Address.setText(model.getValueAt(rowNo, 6).toString());
	txt_country.setText(model.getValueAt(rowNo, 7).toString());
	txt_zip.setText(model.getValueAt(rowNo, 8).toString());


    }//GEN-LAST:event_SupplierTblMouseClicked

    private void ClearBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearBtnMouseClicked
	ClearF();
    }//GEN-LAST:event_ClearBtnMouseClicked

    private void txt_countryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_countryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_countryActionPerformed

    private void txt_zipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_zipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_zipActionPerformed

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
	    java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>
	//</editor-fold>
	//</editor-fold>
	//</editor-fold>

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new Supplier().setVisible(true);
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton ClearBtn;
    private javax.swing.JButton DeleteBtn;
    private rojeru_san.complementos.RSTableMetro SupplierTbl;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JLabel backButton;
    private javax.swing.JLabel homeCloseButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_Address;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_MessengerID;
    private javax.swing.JComboBox<String> txt_MessengerType;
    private javax.swing.JTextField txt_Phone;
    private javax.swing.JTextField txt_SupplierID;
    private javax.swing.JTextField txt_SupplierName;
    private javax.swing.JTextField txt_country;
    private javax.swing.JTextField txt_zip;
    // End of variables declaration//GEN-END:variables
}
