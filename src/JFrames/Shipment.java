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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author User
 */
public class Shipment extends javax.swing.JFrame {

    String OriginZ, DestinationPortZ, TransPortationModeZ, Adminz;
    int ShipmentIDZ, SalesOrderIDZ, PurchaseOrderIDZ;
    Date EstimatedArrivalDateZ;
    DefaultTableModel model;
    
     public Shipment(String Adminz) {
	initComponents();
	this.Adminz = Adminz;
	setShipmentDetailsTable();
    }

    public Shipment() {
	initComponents();
	setShipmentDetailsTable();
    }

    //Clear Table Before Showing New Data
    public void clearTableData() {

	DefaultTableModel model = (DefaultTableModel) ShipmentTbl.getModel();
	model.setRowCount(0);

    }

    //Check Sales  Order ID Abailability
    public void getSalesOrderIDF() {

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    SalesOrderIDZ = Integer.parseInt(txt_SalesOrderID.getText());
	    String sql = "select SalesOrderID from SalesOrder where SalesOrderID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setInt(1, SalesOrderIDZ);
	    ResultSet rs = pst.executeQuery();

	    if (rs.next()) {
		SalesIDValid.setText("Right");
		SalesIDValid.setForeground(Color.GREEN);
	    } else {
		JOptionPane.showMessageDialog(this, "Invalid Sales Order ID!");//or you can use a lable to show the messege !!
		SalesIDValid.setText("Wrong");
		SalesIDValid.setForeground(Color.RED);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    //Check Purchase Order ID Abailability
    public void getPurchaseOrderIDF() {

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    PurchaseOrderIDZ = Integer.parseInt(txt_PurchaseOrderID.getText());
	    String sql = "select PurchaseOrderID from PurchaseOrder where PurchaseOrderID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setInt(1, PurchaseOrderIDZ);
	    ResultSet rs = pst.executeQuery();

	    if (rs.next()) {
		PurchaseIDValid.setText("Right");
		PurchaseIDValid.setForeground(Color.GREEN);
	    } else {
		JOptionPane.showMessageDialog(this, "Invalid Purchase Order ID!"); //or you can use a lable to show the messege !!
		PurchaseIDValid.setText("Wrong");
		PurchaseIDValid.setForeground(Color.RED);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    //validate Inputs
    public boolean validateF() {

	boolean validZ = false;

	if (txt_SalesOrderID.getText().equals("") && txt_PurchaseOrderID.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid SalesOrder or PurchaseOrder ID");
	} else if (txt_Origin.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Origin");
	} else if (txt_DestinationPort.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Destination Port");
	} else if (txt_EstimatedArrivalDate.getDatoFecha() == null) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Estimated Arrival Date");
	} else {
	    validZ = true;
	}
	return validZ;

    }

    //set Shipment Table Details
    public void setShipmentDetailsTable() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("SELECT * FROM Shipment");

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	    while (rs.next()) {
		int ShipmentID = rs.getInt("ShipmentID");
		int SalesOrderID = rs.getInt("SalesOrderID");
		int PurchaseOrderID = rs.getInt("PurchaseOrderID");
		String Origin = rs.getString("Origin");
		String DestinationPort = rs.getString("DestinationPort");
		String TransportationMode = rs.getString("TransportationMode");
		Date EstimatedArrivalDate = rs.getDate("EstimatedArrivalDate");

		// Adding 2 days to EstimatedArrivalDate
		Calendar cal = Calendar.getInstance();
		cal.setTime(EstimatedArrivalDate);
		cal.add(Calendar.DAY_OF_MONTH, 2);
		Date UpdatedEstimatedArrivalDate = cal.getTime();

		String formattedEstimatedArrivalDate = dateFormat.format(UpdatedEstimatedArrivalDate);

		Object[] obj = {ShipmentID, SalesOrderID, PurchaseOrderID, Origin, DestinationPort, TransportationMode, formattedEstimatedArrivalDate};

		model = (DefaultTableModel) ShipmentTbl.getModel();
		model.addRow(obj);

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    //Add Shipment In Database
    public boolean AddShipment() {
	boolean IsAdded = false;

	if (validateF() == true) {
	    if (PurchaseIDValid.getText().equals("Right") && SalesIDValid.getText().equals("Right")) {
		JOptionPane.showMessageDialog(this, "Both Purchase & Sales Order ID is not Accepted!");
	    } //..................................//	
	    else if (SalesIDValid.getText().equals("Right") && (PurchaseIDValid.getText().equals("Wrong") || PurchaseIDValid.getText().equals(""))) {

		if (!txt_SalesOrderID.getText().equals("")) {
		    SalesOrderIDZ = Integer.parseInt(txt_SalesOrderID.getText());
		}
		OriginZ = txt_Origin.getText();
		DestinationPortZ = txt_DestinationPort.getText();
		TransPortationModeZ = txt_TransportationMode.getSelectedItem().toString();
		EstimatedArrivalDateZ = txt_EstimatedArrivalDate.getDatoFecha();

		//Coverting util Date to sql Date time...........
		Long l1 = null;

		if (EstimatedArrivalDateZ != null) {
		    l1 = EstimatedArrivalDateZ.getTime();
		}

		java.sql.Date sEstimatedArrivalDate = (l1 != null) ? new java.sql.Date(l1) : null;

		try {
		    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		    String sql = "insert into Shipment values(?,?,?,?,?,?)";
		    PreparedStatement pst = con.prepareStatement(sql);
		    pst.setInt(1, SalesOrderIDZ);
		    pst.setNull(2, java.sql.Types.INTEGER);
		    pst.setString(3, OriginZ);
		    pst.setString(4, DestinationPortZ);
		    pst.setString(5, TransPortationModeZ);
		    pst.setDate(6, sEstimatedArrivalDate);

		    int rowCount = pst.executeUpdate();

		    if (rowCount > 0) {
			IsAdded = true;
		    } else {
			IsAdded = false;
		    }

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    } else if (PurchaseIDValid.getText().equals("Right") && (SalesIDValid.getText().equals("Wrong") || SalesIDValid.getText().equals(""))) {

		if (!txt_PurchaseOrderID.getText().equals("")) {
		    PurchaseOrderIDZ = Integer.parseInt(txt_PurchaseOrderID.getText());
		}
		OriginZ = txt_Origin.getText();
		DestinationPortZ = txt_DestinationPort.getText();
		TransPortationModeZ = txt_TransportationMode.getSelectedItem().toString();
		EstimatedArrivalDateZ = txt_EstimatedArrivalDate.getDatoFecha();

		//Coverting util Date to sql Date time...........
		Long l1 = null;

		if (EstimatedArrivalDateZ != null) {
		    l1 = EstimatedArrivalDateZ.getTime();
		}
		java.sql.Date sEstimatedArrivalDate = (l1 != null) ? new java.sql.Date(l1) : null;

		try {
		    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		    String sql = "insert into Shipment values(?,?,?,?,?,?)";
		    PreparedStatement pst = con.prepareStatement(sql);
		    pst.setNull(1, java.sql.Types.INTEGER);
		    pst.setInt(2, PurchaseOrderIDZ);
		    pst.setString(3, OriginZ);
		    pst.setString(4, DestinationPortZ);
		    pst.setString(5, TransPortationModeZ);
		    pst.setDate(6, sEstimatedArrivalDate);

		    int rowCount = pst.executeUpdate();

		    if (rowCount > 0) {
			IsAdded = true;
		    } else {
			IsAdded = false;
		    }

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    } else if (SalesIDValid.getText().equals("Wrong")) {
		JOptionPane.showMessageDialog(this, "Invalid Sales Order ID ");
	    } else if (PurchaseIDValid.getText().equals("Wrong")) {
		JOptionPane.showMessageDialog(this, "Invalid Purchase Order ID ");
	    }
	}

	return IsAdded;
    }

    //Update Shipment Table Details   
    public boolean UpdateShipment() {

	boolean IsUpdated = false;

	if (validateF() == true) {
	    if (txt_SalesOrderID.getText().equals("") && txt_PurchaseOrderID.getText().equals("") || txt_SalesOrderID.getText().equals("0") && txt_PurchaseOrderID.getText().equals("0")) {
		JOptionPane.showMessageDialog(this, "Both Purchase & Sales Order ID is Empty!");
	    } else if (!txt_SalesOrderID.getText().equals("") && SalesIDValid.getText().equals("Right") && (txt_PurchaseOrderID.getText().equals("0") || txt_PurchaseOrderID.getText().equals(""))) {

		if (!txt_ShipmentID.getText().equals("")) {
		    ShipmentIDZ = Integer.parseInt(txt_ShipmentID.getText());
		}
		if (!txt_SalesOrderID.getText().equals("")) {
		    SalesOrderIDZ = Integer.parseInt(txt_SalesOrderID.getText());
		}
		OriginZ = txt_Origin.getText();
		DestinationPortZ = txt_DestinationPort.getText();
		TransPortationModeZ = txt_TransportationMode.getSelectedItem().toString();
		EstimatedArrivalDateZ = txt_EstimatedArrivalDate.getDatoFecha();

		//Coverting util Date to sql Date time...........
		Long l1 = null;

		if (EstimatedArrivalDateZ != null) {
		    l1 = EstimatedArrivalDateZ.getTime();
		}
		java.sql.Date sEstimatedArrivalDate = (l1 != null) ? new java.sql.Date(l1) : null;

		try {
		    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		    String sql = "Update Shipment set SalesOrderID = ?,PurchaseOrderID = ?, Origin = ?, DestinationPort = ?, TransportationMode = ?, EstimatedArrivalDate = ? where ShipmentID = ?";
		    PreparedStatement pst = con.prepareStatement(sql);

		    pst.setInt(1, SalesOrderIDZ);
		    pst.setNull(2, java.sql.Types.INTEGER);
		    pst.setString(3, OriginZ);
		    pst.setString(4, DestinationPortZ);
		    pst.setString(5, TransPortationModeZ);
		    pst.setDate(6, sEstimatedArrivalDate);
		    pst.setInt(7, ShipmentIDZ);

		    int rowCount = pst.executeUpdate();

		    if (rowCount > 0) {
			IsUpdated = true;
		    } else {
			IsUpdated = false;
		    }

		} catch (Exception e) {
		    e.printStackTrace();
		}

	    } else if (!txt_PurchaseOrderID.getText().equals("") && PurchaseIDValid.getText().equals("Right") && (txt_SalesOrderID.getText().equals("0") || txt_SalesOrderID.getText().equals(""))) {

		if (!txt_ShipmentID.getText().equals("")) {
		    ShipmentIDZ = Integer.parseInt(txt_ShipmentID.getText());
		}
		if (!txt_PurchaseOrderID.getText().equals("")) {
		    PurchaseOrderIDZ = Integer.parseInt(txt_PurchaseOrderID.getText());
		}
		OriginZ = txt_Origin.getText();
		DestinationPortZ = txt_DestinationPort.getText();
		TransPortationModeZ = txt_TransportationMode.getSelectedItem().toString();
		EstimatedArrivalDateZ = txt_EstimatedArrivalDate.getDatoFecha();

		//Coverting util Date to sql Date time...........
		Long l1 = null;

		if (EstimatedArrivalDateZ != null) {
		    l1 = EstimatedArrivalDateZ.getTime();
		}
		java.sql.Date sEstimatedArrivalDate = (l1 != null) ? new java.sql.Date(l1) : null;

		try {
		    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		    String sql = "Update Shipment set SalesOrderID = ?,PurchaseOrderID = ?, Origin = ?, DestinationPort = ?, TransportationMode = ?, EstimatedArrivalDate = ? where ShipmentID = ?";
		    PreparedStatement pst = con.prepareStatement(sql);

		    pst.setNull(1, java.sql.Types.INTEGER);
		    pst.setInt(2, PurchaseOrderIDZ);
		    pst.setString(3, OriginZ);
		    pst.setString(4, DestinationPortZ);
		    pst.setString(5, TransPortationModeZ);
		    pst.setDate(6, sEstimatedArrivalDate);
		    pst.setInt(7, ShipmentIDZ);

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
	}

	return IsUpdated;
    }

    //Delete Shipment Details
    public boolean DeteleShipment() {

	boolean IsDeleted = false;

	ShipmentIDZ = Integer.parseInt(txt_ShipmentID.getText());

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    String sql = "Delete from Shipment where ShipmentID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);

	    pst.setInt(1, ShipmentIDZ);

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
	txt_ShipmentID.setText("");
	txt_SalesOrderID.setText("");
	txt_PurchaseOrderID.setText("");
	txt_Origin.setText("");
	txt_EstimatedArrivalDate.setDatoFecha(null);
	txt_DestinationPort.setText("");
	PurchaseIDValid.setText("");
	SalesIDValid.setText("");
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
        ShipmentTbl = new rojeru_san.complementos.RSTableMetro();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_ShipmentID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_SalesOrderID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_PurchaseOrderID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_Origin = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        AddBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        DeleteBtn = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();
        ClearBtn = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_DestinationPort = new javax.swing.JTextField();
        txt_TransportationMode = new javax.swing.JComboBox<>();
        txt_EstimatedArrivalDate = new rojeru_san.componentes.RSDateChooser();
        PurchaseIDValid = new javax.swing.JLabel();
        SalesIDValid = new javax.swing.JLabel();

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

        ShipmentTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Shipment ID", "Sales Order ID", "Purchase Order ID", "Origin", "Destination Port", "Transportation Mode", "Estimated Arrival Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ShipmentTbl.setColorBackgoundHead(new java.awt.Color(31, 110, 140));
        ShipmentTbl.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        ShipmentTbl.setColorFilasForeground2(new java.awt.Color(12, 12, 12));
        ShipmentTbl.setColorSelBackgound(new java.awt.Color(255, 133, 81));
        ShipmentTbl.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        ShipmentTbl.setFuenteFilas(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ShipmentTbl.setFuenteHead(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ShipmentTbl.setIntercellSpacing(new java.awt.Dimension(0, 0));
        ShipmentTbl.setRowHeight(28);
        ShipmentTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ShipmentTblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(ShipmentTbl);

        jPanel3.setBackground(new java.awt.Color(31, 110, 140));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Shipment");

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

        txt_ShipmentID.setEditable(false);
        txt_ShipmentID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_ShipmentID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ShipmentIDActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Shipment ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Sales Order ID");

        txt_SalesOrderID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_SalesOrderID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_SalesOrderIDFocusLost(evt);
            }
        });
        txt_SalesOrderID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SalesOrderIDActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Purchase Order ID");

        txt_PurchaseOrderID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_PurchaseOrderID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_PurchaseOrderIDFocusLost(evt);
            }
        });
        txt_PurchaseOrderID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PurchaseOrderIDActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Origin");

        txt_Origin.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_Origin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_OriginFocusLost(evt);
            }
        });
        txt_Origin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_OriginActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Estimated Arrival Date");

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
        jLabel13.setText("Transportation Mode");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Destination Port");

        txt_DestinationPort.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_DestinationPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DestinationPortActionPerformed(evt);
            }
        });

        txt_TransportationMode.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_TransportationMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "By Sea", "By Air", "By Road" }));

        txt_EstimatedArrivalDate.setColorBackground(new java.awt.Color(31, 110, 140));
        txt_EstimatedArrivalDate.setColorForeground(new java.awt.Color(0, 0, 0));
        txt_EstimatedArrivalDate.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txt_EstimatedArrivalDate.setFormatoFecha("dd/MM/yyyy");
        txt_EstimatedArrivalDate.setFuente(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txt_EstimatedArrivalDate.setPlaceholder("Select Est. Arrival Date");

        PurchaseIDValid.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        SalesIDValid.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

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
                        .addGap(63, 63, 63)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(56, 56, 56))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_PurchaseOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_Origin, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_SalesOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_ShipmentID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PurchaseIDValid, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SalesIDValid, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_DestinationPort, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_TransportationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_EstimatedArrivalDate, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(62, 62, 62)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DeleteBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(UpdateBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(AddBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(130, 130, 130))))
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
                        .addGap(92, 92, 92)
                        .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(231, 231, 231)
                            .addComponent(jLabel6))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(73, 73, 73)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_DestinationPort, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_TransportationMode, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13))
                                    .addGap(35, 35, 35)
                                    .addComponent(txt_EstimatedArrivalDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_ShipmentID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_SalesOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(SalesIDValid))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_PurchaseOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(PurchaseIDValid))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_Origin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txt_DestinationPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DestinationPortActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_DestinationPortActionPerformed

    private void ClearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearBtnActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_ClearBtnActionPerformed

    private void ClearBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearBtnMouseClicked
	ClearF();

    }//GEN-LAST:event_ClearBtnMouseClicked

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed
	if (UpdateShipment() == true) {
	    JOptionPane.showMessageDialog(this, "Shipment Updated Successfully!");
	    clearTableData();
	    setShipmentDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Shipment Update Failed!");
	}
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
	if (DeteleShipment() == true) {
	    JOptionPane.showMessageDialog(this, "Shipment Deleted Successfully!");
	    clearTableData();
	    setShipmentDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Shipment Delete Failed!");
	}
    }//GEN-LAST:event_DeleteBtnActionPerformed

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
	if (AddShipment() == true) {
	    JOptionPane.showMessageDialog(this, "Shipment Added Successfully!");
	    clearTableData();
	    setShipmentDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Shipment Addition Failed!");
	}
    }//GEN-LAST:event_AddBtnActionPerformed

    private void txt_OriginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_OriginActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_OriginActionPerformed

    private void txt_OriginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_OriginFocusLost

    }//GEN-LAST:event_txt_OriginFocusLost

    private void txt_PurchaseOrderIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PurchaseOrderIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_PurchaseOrderIDActionPerformed

    private void txt_PurchaseOrderIDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PurchaseOrderIDFocusLost
	if (!txt_PurchaseOrderID.getText().equals("")) {
	    getPurchaseOrderIDF();
	} else {
	    PurchaseIDValid.setText("");
	}
    }//GEN-LAST:event_txt_PurchaseOrderIDFocusLost

    private void txt_SalesOrderIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SalesOrderIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_SalesOrderIDActionPerformed

    private void txt_SalesOrderIDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SalesOrderIDFocusLost
	if (!txt_SalesOrderID.getText().equals("")) {
	    getSalesOrderIDF();
	} else {
	    SalesIDValid.setText("");
	}
    }//GEN-LAST:event_txt_SalesOrderIDFocusLost

    private void txt_ShipmentIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ShipmentIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_ShipmentIDActionPerformed

    private void ShipmentTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ShipmentTblMouseClicked
	int rowNo = ShipmentTbl.getSelectedRow();
	TableModel model = ShipmentTbl.getModel();

	txt_ShipmentID.setText(model.getValueAt(rowNo, 0).toString());
	txt_SalesOrderID.setText(model.getValueAt(rowNo, 1).toString());
	txt_PurchaseOrderID.setText(model.getValueAt(rowNo, 2).toString());
	txt_Origin.setText(model.getValueAt(rowNo, 3).toString());
	txt_DestinationPort.setText(model.getValueAt(rowNo, 4).toString());
	txt_TransportationMode.setSelectedItem(model.getValueAt(rowNo, 5).toString());

	// Parse and format the date from the table model
	try {
	    String estimatedArrivalDateString = model.getValueAt(rowNo, 6).toString();
	    Date estimatedArrivalDate = new SimpleDateFormat("yyyy-MM-dd").parse(estimatedArrivalDateString);
	    txt_EstimatedArrivalDate.setDatoFecha(estimatedArrivalDate);
	} catch (ParseException e) {
	    e.printStackTrace();
	}

    }//GEN-LAST:event_ShipmentTblMouseClicked

    private void homeCloseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeCloseButtonMouseClicked
	System.exit(0);
    }//GEN-LAST:event_homeCloseButtonMouseClicked

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
	    java.util.logging.Logger.getLogger(Shipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(Shipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(Shipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(Shipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
		new Shipment().setVisible(true);
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton ClearBtn;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JLabel PurchaseIDValid;
    private javax.swing.JLabel SalesIDValid;
    private rojeru_san.complementos.RSTableMetro ShipmentTbl;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JLabel backButton;
    private javax.swing.JLabel homeCloseButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_DestinationPort;
    private rojeru_san.componentes.RSDateChooser txt_EstimatedArrivalDate;
    private javax.swing.JTextField txt_Origin;
    private javax.swing.JTextField txt_PurchaseOrderID;
    private javax.swing.JTextField txt_SalesOrderID;
    private javax.swing.JTextField txt_ShipmentID;
    private javax.swing.JComboBox<String> txt_TransportationMode;
    // End of variables declaration//GEN-END:variables
}
