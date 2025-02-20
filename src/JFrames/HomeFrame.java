package JFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

import org.jfree.data.general.DefaultPieDataset;

public class HomeFrame extends javax.swing.JFrame {
    
    String Adminz;

    public HomeFrame(String Admin) {
	initComponents();
	this.Adminz = Admin;
	recordLebel.setVisible(false);
	RecordPanel.setVisible(false);	
	TotalProduct();
	showPieChart();
	TotalCustomer();
	TotalSupplier();
	TotalPurchaseOrder();
	TotalSalesOrder();
	TotalSalesAmount();
	TotalPurchaseAmount();
	PendingPurchaseOrder();
	PendingSalesOrder();
	TotalProfit();
    }
       HomeFrame() {
	initComponents();
	TotalProduct();
	showPieChart();
	TotalCustomer();
	TotalSupplier();
	TotalPurchaseOrder();
	TotalSalesOrder();
	TotalSalesAmount();
	TotalPurchaseAmount();
	PendingPurchaseOrder();
	PendingSalesOrder();
	TotalProfit();
    }

    Color mouseEnterColor = new Color(255, 133, 81);
    Color mouseExitColor = new Color(0, 28, 48);

     float TSCountz;
     float TPCountz;
    
        public void TotalProduct() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("select COUNT(ProductID) AS 'TotalProduct' from Product");

	    if (rs.next()) {
		int Cnt = rs.getInt("TotalProduct");
		String Count = Integer.toString(Cnt);
		TotalProduct.setText(Count);
	    } else {
		TotalProduct.setText("Error!");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
	
    //TotalCustomer
    public void TotalCustomer() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("select COUNT(CustomerID) AS 'TotalCustomer' from Customer");

	    if (rs.next()) {
		int Cnt = rs.getInt("TotalCustomer");
		String Count = Integer.toString(Cnt);
		TotalCustomer.setText(Count);
	    } else {
		TotalCustomer.setText("Error!");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    //Total Supplier
    public void TotalSupplier() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("select COUNT(SupplierID) AS 'TotalSupplier' from Supplier");

	    if (rs.next()) {
		int Cnt = rs.getInt("TotalSupplier");
		String Count = Integer.toString(Cnt);
		TotalSupplier.setText(Count);
	    } else {
		TotalSupplier.setText("Error!");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    //Total PurchaseOrder
    public void TotalPurchaseOrder() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("select COUNT(PurchaseOrderID) AS 'TotalPurchaseOrder' from PurchaseOrder");

	    if (rs.next()) {
		int Cnt = rs.getInt("TotalPurchaseOrder");
		String Count = Integer.toString(Cnt);
		TotalPurchaseOrder.setText(Count);
	    } else {
		TotalPurchaseOrder.setText("Error!");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    //Total SalesOrder
    public void TotalSalesOrder() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("select COUNT(SalesOrderID) AS 'TotalSalesOrder' from SalesOrder");

	    if (rs.next()) {
		int Cnt = rs.getInt("TotalSalesOrder");
		String Count = Integer.toString(Cnt);
		TotalSalesOrder.setText(Count);
	    } else {
		TotalSalesOrder.setText("Error!");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public float TotalSalesAmount() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");
		    
	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("select sum(PaidAmount) as 'TotalSalesAmount' from SalesOrder");

	    if (rs.next()) {
		TSCountz = rs.getFloat("TotalSalesAmount");
		String Count = Float.toString(TSCountz);
		TotalSalesAmount.setText(Count);
	    } else {
		TotalSalesAmount.setText("Error!");
	    }
	   
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return TSCountz;
    }

    public float TotalPurchaseAmount() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("select sum(PaidAmount) as 'TotalPurchaseAmount' from PurchaseOrder");

	    if (rs.next()) {
		TPCountz = rs.getFloat("TotalPurchaseAmount");
		String Count = Float.toString(TPCountz);
		TotalPurchaseAmount.setText(Count);
	    } else {
		TotalPurchaseAmount.setText("Error!");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return TPCountz;
    }
    
    public void TotalProfit(){
	
	float TS, TP, profitz; 
	
	TS = TotalSalesAmount();
	TP = TotalPurchaseAmount();
	profitz = TS - TP;
	String ProftCount = Float.toString(profitz);
	TotalProfit.setText(ProftCount);
    }
    
    

    public void PendingPurchaseOrder() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("select count(PurchaseOrderID) as 'PendingPurchaseOrders' from PurchaseOrder where PurchasePaymentStatus = 'Due'");

	    if (rs.next()) {
		int Cnt = rs.getInt("PendingPurchaseOrders");
		String Count = Integer.toString(Cnt);
		txt_PendingPurchaseOrder.setText(Count);
	    } else {
		txt_PendingPurchaseOrder.setText("Error!");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void PendingSalesOrder() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("select count(SalesOrderID) as 'PendingSalesOrders' from SalesOrder where SalePaymentStatus = 'Due'");

	    if (rs.next()) {
		int Cnt = rs.getInt("PendingSalesOrders");
		String Count = Integer.toString(Cnt);
		txt_PendingSalesOrder.setText(Count);
	    } else {
		txt_PendingSalesOrder.setText("Error!");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BigPanel = new javax.swing.JPanel();
        homeCloseButton = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        TotalSalesOrder = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        TotalSupplier = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        TotalProfit = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        TotalPurchaseOrder = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        TotalProduct = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txt_PendingSalesOrder = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txt_PendingPurchaseOrder = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        TotalCustomer = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        TotalPurchaseAmount = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        TotalSalesAmount = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        TotalSalesAmount3 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        sidePanel = new javax.swing.JPanel();
        ProductPanel = new javax.swing.JPanel();
        ProductSideLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        homePanel2 = new javax.swing.JPanel();
        homeLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        homePanel1 = new javax.swing.JPanel();
        dasboardSideLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        CustomerPanel = new javax.swing.JPanel();
        CustomerSideLevel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        homePanel4 = new javax.swing.JPanel();
        homeLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        PurchasePanel = new javax.swing.JPanel();
        purchaseLebel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        homePanel8 = new javax.swing.JPanel();
        homeLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        SalesPanel = new javax.swing.JPanel();
        SalesLebel = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        homePanel10 = new javax.swing.JPanel();
        homeLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        ShipmentPanel = new javax.swing.JPanel();
        ShipmentLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        homePanel12 = new javax.swing.JPanel();
        homeLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        DocumentPanel = new javax.swing.JPanel();
        DocumentLebel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        homePanel14 = new javax.swing.JPanel();
        homeLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        RecordPanel = new javax.swing.JPanel();
        recordLebel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        homePanel16 = new javax.swing.JPanel();
        homeLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        SupplierPanel = new javax.swing.JPanel();
        SupplierSideLebel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        homePanel6 = new javax.swing.JPanel();
        homeLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        logoutPanel = new javax.swing.JPanel();
        homeLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        homePanel17 = new javax.swing.JPanel();
        homeLabel18 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BigPanel.setBackground(new java.awt.Color(255, 255, 255));
        BigPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeCloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/close.png"))); // NOI18N
        homeCloseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeCloseButtonMouseClicked(evt);
            }
        });
        BigPanel.add(homeCloseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 10, 30, 30));

        jPanel2.setBackground(new java.awt.Color(46, 138, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(242, 151, 39)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TotalSalesOrder.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        TotalSalesOrder.setForeground(new java.awt.Color(255, 255, 255));
        TotalSalesOrder.setText("100000");
        jPanel2.add(TotalSalesOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 150, 60));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/SalesOrderIcon.png"))); // NOI18N
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel21.setFont(new java.awt.Font("Corbel", 1, 22)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Total Sales Order");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 7, -1, -1));

        BigPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 240, 140));

        jPanel3.setBackground(new java.awt.Color(46, 138, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(242, 151, 39)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TotalSupplier.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        TotalSupplier.setForeground(new java.awt.Color(255, 255, 255));
        TotalSupplier.setText("100000");
        jPanel3.add(TotalSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 150, 60));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/SupplierIcon.png"))); // NOI18N
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel23.setFont(new java.awt.Font("Corbel", 1, 22)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Total Suppliers");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 7, -1, -1));

        BigPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 130, 240, 140));

        jPanel4.setBackground(new java.awt.Color(31, 110, 140));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(242, 151, 39)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TotalProfit.setFont(new java.awt.Font("Gadugi", 1, 32)); // NOI18N
        TotalProfit.setForeground(new java.awt.Color(255, 255, 255));
        TotalProfit.setText("100000");
        jPanel4.add(TotalProfit, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 410, 60));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/RecordIcon.png"))); // NOI18N
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel25.setFont(new java.awt.Font("Corbel", 1, 22)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Total Profit / Loss");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 7, -1, -1));

        BigPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 490, 520, 310));

        jPanel5.setBackground(new java.awt.Color(46, 138, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(255, 133, 81)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TotalPurchaseOrder.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        TotalPurchaseOrder.setForeground(new java.awt.Color(255, 255, 255));
        TotalPurchaseOrder.setText("100000");
        jPanel5.add(TotalPurchaseOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 150, 60));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/purchaseOrderIcon.png"))); // NOI18N
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel27.setFont(new java.awt.Font("Corbel", 1, 22)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Total Purchase Order");
        jPanel5.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 7, -1, -1));

        BigPanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 130, 240, 140));

        jPanel6.setBackground(new java.awt.Color(46, 138, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(242, 151, 39)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TotalProduct.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        TotalProduct.setForeground(new java.awt.Color(255, 255, 255));
        TotalProduct.setText("100000");
        jPanel6.add(TotalProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 150, 60));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/CustomerIcon.png"))); // NOI18N
        jPanel6.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel29.setFont(new java.awt.Font("Corbel", 1, 22)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Total Products");
        jPanel6.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 7, -1, -1));

        BigPanel.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 240, 140));

        jPanel7.setBackground(new java.awt.Color(46, 138, 153));
        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(242, 151, 39)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_PendingSalesOrder.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        txt_PendingSalesOrder.setForeground(new java.awt.Color(255, 255, 255));
        txt_PendingSalesOrder.setText("100000");
        jPanel7.add(txt_PendingSalesOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 150, 60));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/PendingIcon.png"))); // NOI18N
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel31.setFont(new java.awt.Font("Corbel", 1, 20)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Pending Sales Orders");
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 7, -1, -1));

        BigPanel.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 310, 520, 140));

        jPanel9.setBackground(new java.awt.Color(46, 138, 153));
        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(255, 133, 81)));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_PendingPurchaseOrder.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        txt_PendingPurchaseOrder.setForeground(new java.awt.Color(255, 255, 255));
        txt_PendingPurchaseOrder.setText("100000");
        jPanel9.add(txt_PendingPurchaseOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 150, 60));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/PendingIcon.png"))); // NOI18N
        jPanel9.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel35.setFont(new java.awt.Font("Corbel", 1, 20)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Pending Purchase Orders");
        jPanel9.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, -1, -1));

        BigPanel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, 240, 140));

        jPanel10.setBackground(new java.awt.Color(46, 138, 153));
        jPanel10.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(255, 133, 81)));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TotalCustomer.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        TotalCustomer.setForeground(new java.awt.Color(255, 255, 255));
        TotalCustomer.setText("100000");
        jPanel10.add(TotalCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 150, 60));

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/CustomerIcon.png"))); // NOI18N
        jPanel10.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel37.setFont(new java.awt.Font("Corbel", 1, 22)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Total Customers");
        jPanel10.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 7, -1, -1));

        BigPanel.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 240, 140));

        jPanel11.setBackground(new java.awt.Color(31, 110, 140));
        jPanel11.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(242, 151, 39)));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TotalPurchaseAmount.setFont(new java.awt.Font("Gadugi", 1, 32)); // NOI18N
        TotalPurchaseAmount.setForeground(new java.awt.Color(255, 255, 255));
        TotalPurchaseAmount.setText("100000");
        jPanel11.add(TotalPurchaseAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 410, 60));

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/purchaseOrderIcon.png"))); // NOI18N
        jPanel11.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel39.setFont(new java.awt.Font("Corbel", 1, 22)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Total Purchase Amount");
        jPanel11.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 7, -1, -1));

        BigPanel.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 660, 520, 140));

        jPanel13.setBackground(new java.awt.Color(31, 110, 140));
        jPanel13.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(255, 133, 81)));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TotalSalesAmount.setFont(new java.awt.Font("Gadugi", 1, 32)); // NOI18N
        TotalSalesAmount.setForeground(new java.awt.Color(255, 255, 255));
        TotalSalesAmount.setText("100000");
        jPanel13.add(TotalSalesAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 410, 60));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/SalesOrderIcon.png"))); // NOI18N
        jPanel13.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel43.setFont(new java.awt.Font("Corbel", 1, 22)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Total Sales Amount");
        jPanel13.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 7, -1, -1));

        jPanel14.setBackground(new java.awt.Color(31, 110, 140));
        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(255, 133, 81)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TotalSalesAmount3.setFont(new java.awt.Font("Gadugi", 1, 32)); // NOI18N
        TotalSalesAmount3.setForeground(new java.awt.Color(255, 255, 255));
        TotalSalesAmount3.setText("100000");
        jPanel14.add(TotalSalesAmount3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 410, 60));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/SalesOrderIcon.png"))); // NOI18N
        jPanel14.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel45.setFont(new java.awt.Font("Corbel", 1, 22)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Total Sales Amount");
        jPanel14.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 7, -1, -1));

        jPanel13.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 520, 140));

        BigPanel.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, 520, 140));

        jPanel8.setBackground(new java.awt.Color(0, 0, 0));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 133, 81));
        jLabel40.setText("Hide Hive");
        jPanel8.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        BigPanel.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 1080, 100));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/HomeLogo.png"))); // NOI18N
        BigPanel.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, -1));

        getContentPane().add(BigPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 1285, 830));

        sidePanel.setBackground(new java.awt.Color(0, 28, 48));
        sidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ProductPanel.setBackground(new java.awt.Color(0, 28, 48));
        ProductPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ProductSideLabel.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        ProductSideLabel.setForeground(new java.awt.Color(255, 255, 255));
        ProductSideLabel.setText(" Product");
        ProductSideLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductSideLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ProductSideLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ProductSideLabelMouseExited(evt);
            }
        });
        ProductPanel.add(ProductSideLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 15, 180, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/ProductIcon.png"))); // NOI18N
        ProductPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        homePanel2.setBackground(new java.awt.Color(255, 133, 81));
        homePanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel2.setFont(new java.awt.Font("Corbel", 1, 27)); // NOI18N
        homeLabel2.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel2.setText(" Dashboard");
        homePanel2.add(homeLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, 40));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DashboardIcon.png"))); // NOI18N
        homePanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 24, -1, -1));

        ProductPanel.add(homePanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 70));

        sidePanel.add(ProductPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 240, 60));

        homePanel1.setBackground(new java.awt.Color(255, 133, 81));
        homePanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dasboardSideLabel.setFont(new java.awt.Font("Corbel", 1, 27)); // NOI18N
        dasboardSideLabel.setForeground(new java.awt.Color(255, 255, 255));
        dasboardSideLabel.setText(" Dashboard");
        dasboardSideLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dasboardSideLabelMouseClicked(evt);
            }
        });
        homePanel1.add(dasboardSideLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DashboardIcon.png"))); // NOI18N
        homePanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 24, -1, -1));

        sidePanel.add(homePanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 240, 70));

        jLabel4.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Features");
        sidePanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        sidePanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 210, 10));

        CustomerPanel.setBackground(new java.awt.Color(0, 28, 48));
        CustomerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        CustomerSideLevel.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        CustomerSideLevel.setForeground(new java.awt.Color(255, 255, 255));
        CustomerSideLevel.setText(" Customer");
        CustomerSideLevel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CustomerSideLevelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CustomerSideLevelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                CustomerSideLevelMouseExited(evt);
            }
        });
        CustomerPanel.add(CustomerSideLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 16, 180, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/CustomerIcon.png"))); // NOI18N
        CustomerPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        homePanel4.setBackground(new java.awt.Color(255, 133, 81));
        homePanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel4.setFont(new java.awt.Font("Corbel", 1, 27)); // NOI18N
        homeLabel4.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel4.setText(" Dashboard");
        homePanel4.add(homeLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, 40));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DashboardIcon.png"))); // NOI18N
        homePanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 24, -1, -1));

        CustomerPanel.add(homePanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 70));

        sidePanel.add(CustomerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 240, 60));

        PurchasePanel.setBackground(new java.awt.Color(0, 28, 48));
        PurchasePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        purchaseLebel.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        purchaseLebel.setForeground(new java.awt.Color(255, 255, 255));
        purchaseLebel.setText("Purchase Order");
        purchaseLebel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                purchaseLebelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                purchaseLebelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                purchaseLebelMouseExited(evt);
            }
        });
        PurchasePanel.add(purchaseLebel, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 15, 170, 40));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/purchaseOrderIcon.png"))); // NOI18N
        PurchasePanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 20, -1, -1));

        homePanel8.setBackground(new java.awt.Color(255, 133, 81));
        homePanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel8.setFont(new java.awt.Font("Corbel", 1, 27)); // NOI18N
        homeLabel8.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel8.setText(" Dashboard");
        homePanel8.add(homeLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, 40));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DashboardIcon.png"))); // NOI18N
        homePanel8.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 24, -1, -1));

        PurchasePanel.add(homePanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 70));

        sidePanel.add(PurchasePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 240, 60));

        SalesPanel.setBackground(new java.awt.Color(0, 28, 48));
        SalesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SalesLebel.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        SalesLebel.setForeground(new java.awt.Color(255, 255, 255));
        SalesLebel.setText("Sales Order");
        SalesLebel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SalesLebelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SalesLebelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SalesLebelMouseExited(evt);
            }
        });
        SalesPanel.add(SalesLebel, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 15, 170, 40));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/SalesOrderIcon.png"))); // NOI18N
        SalesPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        homePanel10.setBackground(new java.awt.Color(255, 133, 81));
        homePanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel10.setFont(new java.awt.Font("Corbel", 1, 27)); // NOI18N
        homeLabel10.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel10.setText(" Dashboard");
        homePanel10.add(homeLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, 40));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DashboardIcon.png"))); // NOI18N
        homePanel10.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 24, -1, -1));

        SalesPanel.add(homePanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 70));

        sidePanel.add(SalesPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 240, 60));

        ShipmentPanel.setBackground(new java.awt.Color(0, 28, 48));
        ShipmentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ShipmentLabel.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        ShipmentLabel.setForeground(new java.awt.Color(255, 255, 255));
        ShipmentLabel.setText("Shipment");
        ShipmentLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ShipmentLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ShipmentLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ShipmentLabelMouseExited(evt);
            }
        });
        ShipmentPanel.add(ShipmentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 15, 170, 40));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/ShipmentIcon.png"))); // NOI18N
        ShipmentPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        homePanel12.setBackground(new java.awt.Color(255, 133, 81));
        homePanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel12.setFont(new java.awt.Font("Corbel", 1, 27)); // NOI18N
        homeLabel12.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel12.setText(" Dashboard");
        homePanel12.add(homeLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, 40));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DashboardIcon.png"))); // NOI18N
        homePanel12.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 24, -1, -1));

        ShipmentPanel.add(homePanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 70));

        sidePanel.add(ShipmentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 240, 60));

        DocumentPanel.setBackground(new java.awt.Color(0, 28, 48));
        DocumentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DocumentLebel.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        DocumentLebel.setForeground(new java.awt.Color(255, 255, 255));
        DocumentLebel.setText("Documentation");
        DocumentLebel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DocumentLebelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DocumentLebelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DocumentLebelMouseExited(evt);
            }
        });
        DocumentPanel.add(DocumentLebel, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 15, 170, 40));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DocumentIcon.png"))); // NOI18N
        DocumentPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        homePanel14.setBackground(new java.awt.Color(255, 133, 81));
        homePanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel14.setFont(new java.awt.Font("Corbel", 1, 27)); // NOI18N
        homeLabel14.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel14.setText(" Dashboard");
        homePanel14.add(homeLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, 40));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DashboardIcon.png"))); // NOI18N
        homePanel14.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 24, -1, -1));

        DocumentPanel.add(homePanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 70));

        sidePanel.add(DocumentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 240, 60));

        RecordPanel.setBackground(new java.awt.Color(0, 28, 48));
        RecordPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        recordLebel.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        recordLebel.setForeground(new java.awt.Color(255, 255, 255));
        recordLebel.setText("Records");
        recordLebel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recordLebelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                recordLebelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                recordLebelMouseExited(evt);
            }
        });
        RecordPanel.add(recordLebel, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 15, 170, 40));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/RecordIcon.png"))); // NOI18N
        RecordPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 20, -1, -1));

        homePanel16.setBackground(new java.awt.Color(255, 133, 81));
        homePanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel16.setFont(new java.awt.Font("Corbel", 1, 27)); // NOI18N
        homeLabel16.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel16.setText(" Dashboard");
        homePanel16.add(homeLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, 40));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DashboardIcon.png"))); // NOI18N
        homePanel16.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 24, -1, -1));

        RecordPanel.add(homePanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 70));

        sidePanel.add(RecordPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 240, 60));

        SupplierPanel.setBackground(new java.awt.Color(0, 28, 48));
        SupplierPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SupplierSideLebel.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        SupplierSideLebel.setForeground(new java.awt.Color(255, 255, 255));
        SupplierSideLebel.setText(" Supplier");
        SupplierSideLebel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SupplierSideLebelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SupplierSideLebelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SupplierSideLebelMouseExited(evt);
            }
        });
        SupplierPanel.add(SupplierSideLebel, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 17, 180, 40));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/SupplierIcon.png"))); // NOI18N
        SupplierPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        homePanel6.setBackground(new java.awt.Color(255, 133, 81));
        homePanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel6.setFont(new java.awt.Font("Corbel", 1, 27)); // NOI18N
        homeLabel6.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel6.setText(" Dashboard");
        homePanel6.add(homeLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, 40));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DashboardIcon.png"))); // NOI18N
        homePanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 24, -1, -1));

        SupplierPanel.add(homePanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 70));

        sidePanel.add(SupplierPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 240, 60));

        logoutPanel.setBackground(new java.awt.Color(0, 28, 48));
        logoutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutPanelMouseExited(evt);
            }
        });
        logoutPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel17.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        homeLabel17.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel17.setText("Logout");
        homeLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeLabel17MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeLabel17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeLabel17MouseExited(evt);
            }
        });
        logoutPanel.add(homeLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 170, 40));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Exit_26px.png"))); // NOI18N
        logoutPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, -1, -1));

        homePanel17.setBackground(new java.awt.Color(255, 133, 81));
        homePanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel18.setFont(new java.awt.Font("Corbel", 1, 27)); // NOI18N
        homeLabel18.setForeground(new java.awt.Color(255, 255, 255));
        homeLabel18.setText(" Dashboard");
        homePanel17.add(homeLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 150, 40));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/DashboardIcon.png"))); // NOI18N
        homePanel17.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 24, -1, -1));

        logoutPanel.add(homePanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 70));

        sidePanel.add(logoutPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 760, -1, 60));

        getContentPane().add(sidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 250, 840));

        setSize(new java.awt.Dimension(1523, 828));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void homeCloseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeCloseButtonMouseClicked
	System.exit(0);
    }//GEN-LAST:event_homeCloseButtonMouseClicked

    private void ProductSideLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductSideLabelMouseClicked
	if ("Admin".equals(Adminz)) {
        Product ob = new Product("Admin");
        ob.setVisible(true);
        this.dispose();
    } else {
        Product ob = new Product();
        ob.setVisible(true);
        this.dispose();
    }
	
    }//GEN-LAST:event_ProductSideLabelMouseClicked

    private void dasboardSideLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dasboardSideLabelMouseClicked
     if ("Admin".equals(Adminz)) {
	    HomeFrame obj1 = new HomeFrame(Adminz);
	    obj1.setVisible(true);
	    this.dispose();
	} else {
	    HomeFrame obj1 = new HomeFrame();
	    obj1.setVisible(true);
	    this.dispose();
	}


    }//GEN-LAST:event_dasboardSideLabelMouseClicked

    private void ProductSideLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductSideLabelMouseEntered
	ProductPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_ProductSideLabelMouseEntered

    private void ProductSideLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductSideLabelMouseExited
	ProductPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_ProductSideLabelMouseExited

    private void CustomerSideLevelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CustomerSideLevelMouseEntered
	CustomerPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_CustomerSideLevelMouseEntered

    private void CustomerSideLevelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CustomerSideLevelMouseExited
	CustomerPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_CustomerSideLevelMouseExited

    private void SupplierSideLebelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SupplierSideLebelMouseEntered
	SupplierPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_SupplierSideLebelMouseEntered

    private void SupplierSideLebelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SupplierSideLebelMouseExited
	SupplierPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_SupplierSideLebelMouseExited

    private void purchaseLebelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchaseLebelMouseEntered
	PurchasePanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_purchaseLebelMouseEntered

    private void purchaseLebelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchaseLebelMouseExited
	PurchasePanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_purchaseLebelMouseExited

    private void SalesLebelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalesLebelMouseEntered
	SalesPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_SalesLebelMouseEntered

    private void SalesLebelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalesLebelMouseExited
	SalesPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_SalesLebelMouseExited

    private void ShipmentLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ShipmentLabelMouseEntered
	ShipmentPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_ShipmentLabelMouseEntered

    private void ShipmentLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ShipmentLabelMouseExited
	ShipmentPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_ShipmentLabelMouseExited

    private void DocumentLebelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DocumentLebelMouseEntered
	DocumentPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_DocumentLebelMouseEntered

    private void DocumentLebelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DocumentLebelMouseExited
	DocumentPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_DocumentLebelMouseExited

    private void recordLebelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordLebelMouseEntered
	RecordPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_recordLebelMouseEntered

    private void recordLebelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordLebelMouseExited
	RecordPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_recordLebelMouseExited

    private void CustomerSideLevelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CustomerSideLevelMouseClicked
	
	if ("Admin".equals(Adminz)) {
	    Customer obj = new Customer("Admin");
	    obj.setVisible(true);
	    this.dispose();
	} else {
	    Customer obj = new Customer();
	    obj.setVisible(true);
	    this.dispose();
	}
    }//GEN-LAST:event_CustomerSideLevelMouseClicked

    private void SupplierSideLebelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SupplierSideLebelMouseClicked
	
	if ("Admin".equals(Adminz)) {
	    Supplier obj = new Supplier("Admin");
	    obj.setVisible(true);
	    this.dispose();
	} else {
	    Supplier obj = new Supplier();
	    obj.setVisible(true);
	    this.dispose();
	}
	
    }//GEN-LAST:event_SupplierSideLebelMouseClicked

    private void purchaseLebelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchaseLebelMouseClicked
	
	if ("Admin".equals(Adminz)) {
	    PurchaseOrder obj = new PurchaseOrder("Admin");
	    obj.setVisible(true);
	    this.dispose();
	} else {
	    PurchaseOrder obj = new PurchaseOrder();
	    obj.setVisible(true);
	    this.dispose();
	}
	
    }//GEN-LAST:event_purchaseLebelMouseClicked

    private void SalesLebelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalesLebelMouseClicked
	
	if ("Admin".equals(Adminz)) {
	    SalesOrder obj = new SalesOrder("Admin");
	    obj.setVisible(true);
	    this.dispose();
	} else {
	    SalesOrder obj = new SalesOrder();
	    obj.setVisible(true);
	    this.dispose();
	}
	
    }//GEN-LAST:event_SalesLebelMouseClicked

    private void ShipmentLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ShipmentLabelMouseClicked
	
	if ("Admin".equals(Adminz)) {
	    Shipment obj = new Shipment("Admin");
	    obj.setVisible(true);
	    this.dispose();
	} else {
	    Shipment obj = new Shipment();
	    obj.setVisible(true);
	    this.dispose();
	}
	
    }//GEN-LAST:event_ShipmentLabelMouseClicked

    private void DocumentLebelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DocumentLebelMouseClicked
	
	if ("Admin".equals(Adminz)) {
	    Documentation obj = new Documentation("Admin");
	    obj.setVisible(true);
	    this.dispose();
	} else {
	    Documentation obj = new Documentation();
	    obj.setVisible(true);
	    this.dispose();
	}
    }//GEN-LAST:event_DocumentLebelMouseClicked

    private void recordLebelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordLebelMouseClicked
        Record obj = new Record();
	obj.setVisible(true);
	this.dispose();
    }//GEN-LAST:event_recordLebelMouseClicked

    private void homeLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeLabel17MouseClicked
         Login obj = new Login();
	obj.setVisible(true);
	this.dispose();
    }//GEN-LAST:event_homeLabel17MouseClicked

    private void homeLabel17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeLabel17MouseEntered
      logoutPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_homeLabel17MouseEntered

    private void homeLabel17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeLabel17MouseExited
         logoutPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_homeLabel17MouseExited

    private void logoutPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPanelMouseEntered
      //  logoutPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_logoutPanelMouseEntered

    private void logoutPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPanelMouseExited
     //  logoutPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_logoutPanelMouseExited

    private void logoutPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPanelMouseClicked
        Login obj = new Login();
	obj.setVisible(true);
	this.dispose();
    }//GEN-LAST:event_logoutPanelMouseClicked

    public void showPieChart() {

	//create dataset
	DefaultPieDataset barDataset = new DefaultPieDataset();
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("Select Product.ProductName + ' (' + (select cast(Product.ProductID as varchar(20))) + ')' as 'Pnamez', (Select count(*) from SalesOrder where Product.ProductID = SalesOrder.ProductID) as 'CountP' From Product Join (Select Top 4 ProductID, Count(SalesOrderID) as CountS from SalesOrder Group by ProductID Order by CountS desc) as SP on Product.ProductID = SP.ProductID");

	    while (rs.next()) {

		barDataset.setValue(rs.getString("Pnamez"), new Double(rs.getDouble("CountP")));
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	/*create chart
	JFreeChart piechart = ChartFactory.createPieChart("Leather Export & Import", barDataset, true, true, false);

	PiePlot piePlot = (PiePlot) piechart.getPlot();

	//changing pie chart blocks colors
	piePlot.setSectionPaint("", new Color(255, 255, 102));
	piePlot.setSectionPaint("", new Color(102, 255, 102));
	piePlot.setSectionPaint("", new Color(255, 102, 153));
	piePlot.setSectionPaint("", new Color(0, 204, 204));

	piePlot.setBackgroundPaint(Color.white);

	//create chartPanel to display chart(graph)
	ChartPanel barChartPanel = new ChartPanel(piechart);
	PieChartPanel.removeAll();
	PieChartPanel.add(barChartPanel, BorderLayout.CENTER);
	PieChartPanel.validate();*/
    }

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
	    java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(HomeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new HomeFrame().setVisible(true);
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BigPanel;
    private javax.swing.JPanel CustomerPanel;
    private javax.swing.JLabel CustomerSideLevel;
    private javax.swing.JLabel DocumentLebel;
    private javax.swing.JPanel DocumentPanel;
    private javax.swing.JPanel ProductPanel;
    private javax.swing.JLabel ProductSideLabel;
    private javax.swing.JPanel PurchasePanel;
    private javax.swing.JPanel RecordPanel;
    private javax.swing.JLabel SalesLebel;
    private javax.swing.JPanel SalesPanel;
    private javax.swing.JLabel ShipmentLabel;
    private javax.swing.JPanel ShipmentPanel;
    private javax.swing.JPanel SupplierPanel;
    private javax.swing.JLabel SupplierSideLebel;
    private javax.swing.JLabel TotalCustomer;
    private javax.swing.JLabel TotalProduct;
    private javax.swing.JLabel TotalProfit;
    private javax.swing.JLabel TotalPurchaseAmount;
    private javax.swing.JLabel TotalPurchaseOrder;
    private javax.swing.JLabel TotalSalesAmount;
    private javax.swing.JLabel TotalSalesAmount3;
    private javax.swing.JLabel TotalSalesOrder;
    private javax.swing.JLabel TotalSupplier;
    private javax.swing.JLabel dasboardSideLabel;
    private javax.swing.JLabel homeCloseButton;
    private javax.swing.JLabel homeLabel10;
    private javax.swing.JLabel homeLabel12;
    private javax.swing.JLabel homeLabel14;
    private javax.swing.JLabel homeLabel16;
    private javax.swing.JLabel homeLabel17;
    private javax.swing.JLabel homeLabel18;
    private javax.swing.JLabel homeLabel2;
    private javax.swing.JLabel homeLabel4;
    private javax.swing.JLabel homeLabel6;
    private javax.swing.JLabel homeLabel8;
    private javax.swing.JPanel homePanel1;
    private javax.swing.JPanel homePanel10;
    private javax.swing.JPanel homePanel12;
    private javax.swing.JPanel homePanel14;
    private javax.swing.JPanel homePanel16;
    private javax.swing.JPanel homePanel17;
    private javax.swing.JPanel homePanel2;
    private javax.swing.JPanel homePanel4;
    private javax.swing.JPanel homePanel6;
    private javax.swing.JPanel homePanel8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel logoutPanel;
    private javax.swing.JLabel purchaseLebel;
    private javax.swing.JLabel recordLebel;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel txt_PendingPurchaseOrder;
    private javax.swing.JLabel txt_PendingSalesOrder;
    // End of variables declaration//GEN-END:variables
}
