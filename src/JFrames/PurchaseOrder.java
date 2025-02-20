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
public class PurchaseOrder extends javax.swing.JFrame {

    String PaymentTermZ, PaymentStatusZ, Adminz;
    float TotalPriceZ, PaidAmountZ, DueAmountZ;
    int PurchaseOrderIDZ, SupplierIDZ, ProductIDZ, QuantityZ;
    Date OrderDateZ, DeliveryDateZ;
    DefaultTableModel model;
    
    public PurchaseOrder(String Adminz) {
	initComponents();
	this.Adminz = Adminz;
	setPurchaseOrderDetailsTable();
    }
    
    

    public PurchaseOrder() {
	initComponents();
	setPurchaseOrderDetailsTable();
    }

    //Clear Table Before Showing New Data
    public void clearTableData() {

	DefaultTableModel model = (DefaultTableModel) PurchaseOrderTbl.getModel();
	model.setRowCount(0);

    }
    
    public void PaidDueF(){
	     if(!txt_TotalPrice.getText().equals("") && txt_PaymentStatus.getSelectedItem().toString().equals("Paid")){
	        txt_PaidAmount.setText(txt_TotalPrice.getText());
		txt_DueAmount.setText("");
	    }
	    else{
		txt_PaidAmount.setText("");
	    }
    }

    //Check Supplier ID Abailability
    public void getSupplierIDF() {

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    SupplierIDZ = Integer.parseInt(txt_SupplierID.getText());
	    String sql = "select SupplierID from Supplier where SupplierID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setInt(1, SupplierIDZ);
	    ResultSet rs = pst.executeQuery();

	    if (rs.next()) {
		SupplierValid.setText("Right");
		SupplierValid.setForeground(Color.GREEN);
	    } else {
		JOptionPane.showMessageDialog(this, "Invalid Supplier ID!");//or you can use a lable to show the messege !!
		SupplierValid.setText("Wrong");
		SupplierValid.setForeground(Color.RED);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    //Check Product ID Abailability
    public void getProductIDF() {

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    ProductIDZ = Integer.parseInt(txt_ProductID.getText());
	    String sql = "select ProductID from Product where ProductID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setInt(1, ProductIDZ);
	    ResultSet rs = pst.executeQuery();

	    if (rs.next()) {
		ProductValid.setText("Right");
		ProductValid.setForeground(Color.GREEN);
	    } else {
		JOptionPane.showMessageDialog(this, "Invalid Product ID!"); //or you can use a lable to show the messege !!
		ProductValid.setText("Wrong");
		ProductValid.setForeground(Color.RED);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    //Total Price Auto Set Using Quantity.
    public void TotalPriceF() {

	float price, Quant, TotalPriceZ;
	Quant = Float.parseFloat(txt_Quantity.getText());
	String STotalPrice;

	if (ProductValid.getText().equals("Right") && !txt_Quantity.getText().equals("")) {

	    try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		ProductIDZ = Integer.parseInt(txt_ProductID.getText());
		String sql = "select ProductPrice from Product where ProductID = ?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, ProductIDZ);
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
		    price = rs.getFloat("ProductPrice");
		    TotalPriceZ = Quant * price;
		    STotalPrice = Float.toString(TotalPriceZ);
		    txt_TotalPrice.setText(STotalPrice);

		} else {
		    JOptionPane.showMessageDialog(this, "Invalid Quantity or Product ID!"); //or you can use a lable to show the messege !!
		}

	    } catch (Exception e) {
		e.printStackTrace();
	    }

	}

    }

    //validate Inputs
    public boolean validateF() {

	boolean validZ = false;

	if (txt_SupplierID.getText().equals("") && !txt_SupplierID.getText().matches("\\d+")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Supplier ID");
	} else if (txt_ProductID.getText().equals("") && !txt_ProductID.getText().matches("\\d+")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Product ID");
	} else if (txt_Quantity.getText().equals("") && !txt_Quantity.getText().matches("\\d+")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Quantity");
	} else if (txt_OrderDate.getDatoFecha()==null) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Order Date");
	} else if (txt_DeliveryDate.getDatoFecha()==null) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Delivery Date");
	} else if (txt_TotalPrice.getText().equals("") && !txt_TotalPrice.getText().matches("^\\d+(\\.\\d+)?$")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Total Price");
	} else if (txt_PaymentTerm.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Payment Term");
	} else if (txt_PaidAmount.getText().equals("") && !txt_PaidAmount.getText().matches("^\\d+(\\.\\d+)?$")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Paid Amount");
	} else if (txt_PaidAmount.getText().equals("") && !txt_PaidAmount.getText().matches("^\\d+(\\.\\d+)?$")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Valid Due Amount");
	} else {
	    validZ = true;
	}
	return validZ;
    }

    //set Purchase Order Table Details
    public void setPurchaseOrderDetailsTable() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");
	    
	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("SELECT * FROM PurchaseOrder");

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	    while (rs.next()) {
		int PurchaseOrderID = rs.getInt("PurchaseOrderID");
		int SupplierID = rs.getInt("SupplierID");
		int ProductID = rs.getInt("ProductID");
		int Quantity = rs.getInt("PurchaseQuantity");
		Date OrderDate = rs.getDate("PurchaseOrderDate");
		float TotalPrice = rs.getFloat("PurchaseTotalPrice");
		String PaymentTerm = rs.getString("PurchasePaymentTerm");
		String PaymentStatus = rs.getString("PurchasePaymentStatus");
		Date DeliveryDate = rs.getDate("PurchaseDeliveryDate");
		float PaidAmount = rs.getFloat("PaidAmount");
		float DueAmount = rs.getFloat("DueAmount");

		// Adding 2 days to OrderDate and DeliveryDate
		Calendar cal = Calendar.getInstance();
		cal.setTime(OrderDate);
		cal.add(Calendar.DAY_OF_MONTH, 2);
		Date UpdatedOrderDate = cal.getTime();

		cal.setTime(DeliveryDate);
		cal.add(Calendar.DAY_OF_MONTH, 2);
		Date UpdatedDeliveryDate = cal.getTime();

		String formattedOrderDate = dateFormat.format(UpdatedOrderDate);
		String formattedDeliveryDate = dateFormat.format(UpdatedDeliveryDate);

		Object[] obj = {
		    PurchaseOrderID, SupplierID, ProductID, Quantity, formattedOrderDate, TotalPrice,
		    PaymentTerm, PaymentStatus, formattedDeliveryDate, PaidAmount, DueAmount
		};

		model = (DefaultTableModel) PurchaseOrderTbl.getModel();
		model.addRow(obj);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    //Add Purchase Order In Database
    public boolean AddPurchaseOrder() {
	boolean IsAdded = false;

	if(!txt_SupplierID.getText().equals("")){
	SupplierIDZ = Integer.parseInt(txt_SupplierID.getText());
	}
	if(!txt_ProductID.getText().equals("")){
	ProductIDZ = Integer.parseInt(txt_ProductID.getText());
	}
	if(!txt_Quantity.getText().equals("")){
	QuantityZ = Integer.parseInt(txt_Quantity.getText());
	}
	OrderDateZ = txt_OrderDate.getDatoFecha();
	if(!txt_TotalPrice.getText().equals("")){
	TotalPriceZ = Float.parseFloat(txt_TotalPrice.getText());
	}
	PaymentTermZ = txt_PaymentTerm.getText();
	PaymentStatusZ = txt_PaymentStatus.getSelectedItem().toString();
	DeliveryDateZ = txt_DeliveryDate.getDatoFecha();
	if(!txt_PaidAmount.getText().equals("")){
	PaidAmountZ = Float.parseFloat(txt_PaidAmount.getText());
	}
	if(!txt_DueAmount.getText().equals("")){
	DueAmountZ = Float.parseFloat(txt_DueAmount.getText());
	}

	//Coverting util Date to sql Date time...........
	Long l1 = null;
	Long l2 = null;

	Date orderDate = txt_OrderDate.getDatoFecha();
	Date deliveryDate = txt_DeliveryDate.getDatoFecha();

	if (orderDate != null) {
	    l1 = orderDate.getTime();
	}

	if (deliveryDate != null) {
	    l2 = deliveryDate.getTime();
	}

	java.sql.Date sOrderDate = (l1 != null) ? new java.sql.Date(l1) : null;
	java.sql.Date sDeliveryDate = (l2 != null) ? new java.sql.Date(l2) : null;


//..................................	
    if (validateF() == true) {
	if (SupplierValid.getText().equals("Right") && ProductValid.getText().equals("Right")) {
		try {
		    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		    String sql = "insert into PurchaseOrder values(?,?,?,?,?,?,?,?,?,?)";
		    PreparedStatement pst = con.prepareStatement(sql);
		    pst.setInt(1, SupplierIDZ);
		    pst.setInt(2, ProductIDZ);
		    pst.setInt(3, QuantityZ);
		    pst.setDate(4, sOrderDate);
		    pst.setFloat(5, TotalPriceZ);
		    pst.setString(6, PaymentTermZ);
		    pst.setString(7, PaymentStatusZ);
		    pst.setDate(8, sDeliveryDate);
		    pst.setFloat(9, PaidAmountZ);
		    pst.setFloat(10, DueAmountZ);

		    int rowCount = pst.executeUpdate();

		    if (rowCount > 0) {
			IsAdded = true;
		    } else {
			IsAdded = false;
		    }

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    
	} else if (SupplierValid.getText().equals("Wrong") && ProductValid.getText().equals("Wrong")) {
	    JOptionPane.showMessageDialog(this, "Both Supplier ID and Product ID is Invalid ");
	} else if (SupplierValid.getText().equals("Wrong")) {
	    JOptionPane.showMessageDialog(this, "Invalid Supplier ID ");
	} else if (ProductValid.getText().equals("Wrong")) {

	    JOptionPane.showMessageDialog(this, "Invalid Product ID ");
	}
    }

	return IsAdded;
    }

    //Update Purchase Order Table Details   
    public boolean UpdatePurchaseOrder() {

	boolean IsUpdated = false;
        
	if(!txt_PurchaseOrderID.getText().equals("")){
	PurchaseOrderIDZ = Integer.parseInt(txt_PurchaseOrderID.getText());
	}
	if(!txt_SupplierID.getText().equals("")){
	SupplierIDZ = Integer.parseInt(txt_SupplierID.getText());
	}
	if(!txt_ProductID.getText().equals("")){
	ProductIDZ = Integer.parseInt(txt_ProductID.getText());
	}
	if(!txt_Quantity.getText().equals("")){
	QuantityZ = Integer.parseInt(txt_Quantity.getText());
	}
	OrderDateZ = txt_OrderDate.getDatoFecha();
	if(!txt_TotalPrice.getText().equals("")){
	TotalPriceZ = Float.parseFloat(txt_TotalPrice.getText());
	}
	PaymentTermZ = txt_PaymentTerm.getText();
	PaymentStatusZ = txt_PaymentStatus.getSelectedItem().toString();
	DeliveryDateZ = txt_DeliveryDate.getDatoFecha();
	if(!txt_PaidAmount.getText().equals("")){
	PaidAmountZ = Float.parseFloat(txt_PaidAmount.getText());
	}
	if(!txt_DueAmount.getText().equals("")){
	DueAmountZ = Float.parseFloat(txt_DueAmount.getText());
	}

	//Coverting util Date to sql Date time...........
	Long l1 = null;
	Long l2 = null;

	Date orderDate = txt_OrderDate.getDatoFecha();
	Date deliveryDate = txt_DeliveryDate.getDatoFecha();

	if (orderDate != null) {
	    l1 = orderDate.getTime();
	}

	if (deliveryDate != null) {
	    l2 = deliveryDate.getTime();
	}

	java.sql.Date sOrderDate = (l1 != null) ? new java.sql.Date(l1) : null;
	java.sql.Date sDeliveryDate = (l2 != null) ? new java.sql.Date(l2) : null;

	if (validateF() == true) {
	    try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		String sql = "Update PurchaseOrder set SupplierID = ?, ProductID = ?, PurchaseQuantity = ?, PurchaseOrderDate = ?, PurchaseTotalPrice = ?, PurchasePaymentTerm = ?, PurchasePaymentStatus = ?,PurchaseDeliveryDate = ?, PaidAmount = ?, DueAmount = ? where PurchaseOrderID = ?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, SupplierIDZ);
		pst.setInt(2, ProductIDZ);
		pst.setInt(3, QuantityZ);
		pst.setDate(4, sOrderDate);
		pst.setFloat(5, TotalPriceZ);
		pst.setString(6, PaymentTermZ);
		pst.setString(7, PaymentStatusZ);
		pst.setDate(8, sDeliveryDate);
		pst.setFloat(9, PaidAmountZ);
		pst.setFloat(10, DueAmountZ);
		pst.setInt(11, PurchaseOrderIDZ);

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

    //Delete Purchase Order Details
    public boolean DetelePurchaseOrder() {

	boolean IsDeleted = false;

	PurchaseOrderIDZ = Integer.parseInt(txt_PurchaseOrderID.getText());

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    String sql = "Delete from PurchaseOrder where PurchaseOrderID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);

	    pst.setInt(1, PurchaseOrderIDZ);

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

	txt_PurchaseOrderID.setText("");
	txt_SupplierID.setText("");
	txt_ProductID.setText("");
	txt_Quantity.setText("");
	txt_OrderDate.setDatoFecha(null);
	txt_TotalPrice.setText("");
	txt_PaymentTerm.setText("");
	txt_DeliveryDate.setDatoFecha(null);
	txt_PaidAmount.setText("");
	txt_DueAmount.setText("");
	SupplierValid.setText("");
	ProductValid.setText("");

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
        PurchaseOrderTbl = new rojeru_san.complementos.RSTableMetro();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_PurchaseOrderID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_SupplierID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_ProductID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_Quantity = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        AddBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        DeleteBtn = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();
        ClearBtn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txt_PaidAmount = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_PaymentTerm = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_TotalPrice = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_DueAmount = new javax.swing.JTextField();
        txt_PaymentStatus = new javax.swing.JComboBox<>();
        txt_DeliveryDate = new rojeru_san.componentes.RSDateChooser();
        txt_OrderDate = new rojeru_san.componentes.RSDateChooser();
        SupplierValid = new javax.swing.JLabel();
        ProductValid = new javax.swing.JLabel();

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

        PurchaseOrderTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Purchase Order ID", "Supplier ID", "Product ID", "Quantity", "Order Date", "Total Price", "Payment Term", "Payment Status", "Delivery Date", "Paid Amount", "Due Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PurchaseOrderTbl.setColorBackgoundHead(new java.awt.Color(31, 110, 140));
        PurchaseOrderTbl.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        PurchaseOrderTbl.setColorFilasForeground2(new java.awt.Color(12, 12, 12));
        PurchaseOrderTbl.setColorSelBackgound(new java.awt.Color(255, 133, 81));
        PurchaseOrderTbl.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        PurchaseOrderTbl.setFuenteFilas(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        PurchaseOrderTbl.setFuenteHead(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PurchaseOrderTbl.setIntercellSpacing(new java.awt.Dimension(0, 0));
        PurchaseOrderTbl.setRowHeight(28);
        PurchaseOrderTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PurchaseOrderTblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(PurchaseOrderTbl);

        jPanel3.setBackground(new java.awt.Color(31, 110, 140));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Purchase Order");

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

        txt_PurchaseOrderID.setEditable(false);
        txt_PurchaseOrderID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_PurchaseOrderID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PurchaseOrderIDActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Purchase Order ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Supplier ID");

        txt_SupplierID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_SupplierID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_SupplierIDFocusLost(evt);
            }
        });
        txt_SupplierID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SupplierIDActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Product ID");

        txt_ProductID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_ProductID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_ProductIDFocusLost(evt);
            }
        });
        txt_ProductID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ProductIDActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Quantity");

        txt_Quantity.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_Quantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_QuantityFocusLost(evt);
            }
        });
        txt_Quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_QuantityActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Order Date");

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
        jLabel11.setText("Paid Amount");

        txt_PaidAmount.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_PaidAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_PaidAmountFocusLost(evt);
            }
        });
        txt_PaidAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PaidAmountActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Delivery Date");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Payment Status");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Payment Term");

        txt_PaymentTerm.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_PaymentTerm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PaymentTermActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Total Price");

        txt_TotalPrice.setEditable(false);
        txt_TotalPrice.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_TotalPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TotalPriceActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("Due Amount");

        txt_DueAmount.setEditable(false);
        txt_DueAmount.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_DueAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DueAmountActionPerformed(evt);
            }
        });

        txt_PaymentStatus.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_PaymentStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Paid", "Due" }));
        txt_PaymentStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txt_PaymentStatusItemStateChanged(evt);
            }
        });
        txt_PaymentStatus.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_PaymentStatusFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_PaymentStatusFocusLost(evt);
            }
        });
        txt_PaymentStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_PaymentStatusMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_PaymentStatusMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt_PaymentStatusMousePressed(evt);
            }
        });

        txt_DeliveryDate.setColorBackground(new java.awt.Color(31, 110, 140));
        txt_DeliveryDate.setColorForeground(new java.awt.Color(0, 0, 0));
        txt_DeliveryDate.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txt_DeliveryDate.setFormatoFecha("dd/MM/yyyy");
        txt_DeliveryDate.setFuente(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txt_DeliveryDate.setPlaceholder("Select Delivery Date");

        txt_OrderDate.setColorBackground(new java.awt.Color(31, 110, 140));
        txt_OrderDate.setColorForeground(new java.awt.Color(0, 0, 0));
        txt_OrderDate.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txt_OrderDate.setFormatoFecha("dd/MM/yyyy");
        txt_OrderDate.setFuente(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txt_OrderDate.setPlaceholder("Select Order Date");

        SupplierValid.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        SupplierValid.setToolTipText("");

        ProductValid.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

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
                        .addGap(76, 76, 76)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_SupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_PurchaseOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(102, 102, 102)
                                .addComponent(txt_OrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_DeliveryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SupplierValid, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(ProductValid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15)
                            .addComponent(jLabel11)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_DueAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_PaymentStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_PaidAmount)
                                .addComponent(txt_PaymentTerm)
                                .addComponent(txt_TotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(97, 97, 97)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(129, 129, 129))))
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
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(143, 143, 143)
                            .addComponent(ProductValid, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(27, 27, 27)
                            .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(ClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(140, 140, 140)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(193, 193, 193)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txt_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txt_PurchaseOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txt_SupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3))
                                        .addGap(19, 19, 19)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(txt_ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_OrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel6)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(jLabel12))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(txt_DeliveryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(SupplierValid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txt_TotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txt_PaymentTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txt_PaymentStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_PaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_DueAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))))
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

    private void txt_PurchaseOrderIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PurchaseOrderIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_PurchaseOrderIDActionPerformed

    private void txt_SupplierIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SupplierIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_SupplierIDActionPerformed

    private void txt_ProductIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ProductIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_ProductIDActionPerformed

    private void txt_QuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_QuantityActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_QuantityActionPerformed

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
	if (AddPurchaseOrder() == true) {
	    JOptionPane.showMessageDialog(this, "Purchase Order Added Successfully!");
	    clearTableData();
	    setPurchaseOrderDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Purchase Order Addition Failed!");
	}

    }//GEN-LAST:event_AddBtnActionPerformed

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
	if (DetelePurchaseOrder() == true) {
	    JOptionPane.showMessageDialog(this, "Purchase Order Deleted Successfully!");
	    clearTableData();
	    setPurchaseOrderDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Purchase Order Delete Failed!");
	}
    }//GEN-LAST:event_DeleteBtnActionPerformed

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed
	if (UpdatePurchaseOrder() == true) {
	    JOptionPane.showMessageDialog(this, "Purchase Order Updated Successfully!");
	    clearTableData();
	    setPurchaseOrderDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Purchase Order Update Failed!");
	}
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void ClearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearBtnActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_ClearBtnActionPerformed

    private void txt_PaidAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PaidAmountActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_PaidAmountActionPerformed

    private void txt_PaymentTermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PaymentTermActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_PaymentTermActionPerformed

    private void txt_TotalPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TotalPriceActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_TotalPriceActionPerformed

    private void PurchaseOrderTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PurchaseOrderTblMouseClicked
	int rowNo = PurchaseOrderTbl.getSelectedRow();
	TableModel model = PurchaseOrderTbl.getModel();

	txt_PurchaseOrderID.setText(model.getValueAt(rowNo, 0).toString());
	txt_SupplierID.setText(model.getValueAt(rowNo, 1).toString());
	txt_ProductID.setText(model.getValueAt(rowNo, 2).toString());
	txt_Quantity.setText(model.getValueAt(rowNo, 3).toString());

	// Parse and format the date from the table model
	try {
	    String orderDateString = model.getValueAt(rowNo, 4).toString();
	    Date orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(orderDateString);
	    txt_OrderDate.setDatoFecha(orderDate);
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	txt_TotalPrice.setText(model.getValueAt(rowNo, 5).toString());
	txt_PaymentTerm.setText(model.getValueAt(rowNo, 6).toString());
	txt_PaymentStatus.setSelectedItem(model.getValueAt(rowNo, 7).toString());

	// Parse and format the date from the table model
	try {
	    String deliveryDateString = model.getValueAt(rowNo, 8).toString();
	    Date deliveryDate = new SimpleDateFormat("yyyy-MM-dd").parse(deliveryDateString);
	    txt_DeliveryDate.setDatoFecha(deliveryDate);
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	txt_PaidAmount.setText(model.getValueAt(rowNo, 9).toString());
	txt_DueAmount.setText(model.getValueAt(rowNo, 10).toString());

    }//GEN-LAST:event_PurchaseOrderTblMouseClicked

    private void ClearBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearBtnMouseClicked
	ClearF();

    }//GEN-LAST:event_ClearBtnMouseClicked

    private void txt_DueAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DueAmountActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_DueAmountActionPerformed

    private void txt_SupplierIDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_SupplierIDFocusLost
	if (!txt_SupplierID.getText().equals("")) {
	    getSupplierIDF();
	} else {
	    SupplierValid.setText("");
	}
    }//GEN-LAST:event_txt_SupplierIDFocusLost

    private void txt_ProductIDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_ProductIDFocusLost
	if (!txt_ProductID.getText().equals("")) {
	    getProductIDF();
	} else {
	    ProductValid.setText("");
	}
    }//GEN-LAST:event_txt_ProductIDFocusLost

    private void txt_QuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_QuantityFocusLost
	if (!txt_Quantity.getText().equals("")) {
	    TotalPriceF();
	    PaidDueF();
	} else {
	    txt_TotalPrice.setText("");
	}
    }//GEN-LAST:event_txt_QuantityFocusLost

    private void txt_PaidAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PaidAmountFocusLost

	if (!txt_TotalPrice.getText().equals("") && !txt_PaidAmount.getText().equals("")) {
	    float TotalP = Float.parseFloat(txt_TotalPrice.getText());
	    float PaidP = Float.parseFloat(txt_PaidAmount.getText());

	    float DueP = TotalP - PaidP;
	    String DueAmnt = Float.toString(DueP);
	    txt_DueAmount.setText(DueAmnt);
	} else {
	    JOptionPane.showMessageDialog(this, "TotalPrice or Paid Amount is Invalid or Empty!");
	}


    }//GEN-LAST:event_txt_PaidAmountFocusLost

    private void txt_PaymentStatusFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PaymentStatusFocusLost
         
    }//GEN-LAST:event_txt_PaymentStatusFocusLost

    private void txt_PaymentStatusFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PaymentStatusFocusGained
        
    }//GEN-LAST:event_txt_PaymentStatusFocusGained

    private void txt_PaymentStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_PaymentStatusMouseClicked
         
    }//GEN-LAST:event_txt_PaymentStatusMouseClicked

    private void txt_PaymentStatusMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_PaymentStatusMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_PaymentStatusMousePressed

    private void txt_PaymentStatusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_PaymentStatusMouseExited
       
    }//GEN-LAST:event_txt_PaymentStatusMouseExited

    private void txt_PaymentStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txt_PaymentStatusItemStateChanged
        PaidDueF();
    }//GEN-LAST:event_txt_PaymentStatusItemStateChanged

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
	    java.util.logging.Logger.getLogger(PurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(PurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(PurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(PurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>
	//</editor-fold>

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new PurchaseOrder().setVisible(true);
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton ClearBtn;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JLabel ProductValid;
    private rojeru_san.complementos.RSTableMetro PurchaseOrderTbl;
    private javax.swing.JLabel SupplierValid;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JLabel backButton;
    private javax.swing.JLabel homeCloseButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private rojeru_san.componentes.RSDateChooser txt_DeliveryDate;
    private javax.swing.JTextField txt_DueAmount;
    private rojeru_san.componentes.RSDateChooser txt_OrderDate;
    private javax.swing.JTextField txt_PaidAmount;
    private javax.swing.JComboBox<String> txt_PaymentStatus;
    private javax.swing.JTextField txt_PaymentTerm;
    private javax.swing.JTextField txt_ProductID;
    private javax.swing.JTextField txt_PurchaseOrderID;
    private javax.swing.JTextField txt_Quantity;
    private javax.swing.JTextField txt_SupplierID;
    private javax.swing.JTextField txt_TotalPrice;
    // End of variables declaration//GEN-END:variables
}
