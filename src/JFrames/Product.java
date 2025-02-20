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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author User
 */
public class Product extends javax.swing.JFrame {

    String ProductNameZ, ProductSizeZ, ThicknessZ, TanningZ, GradeZ, ColorZ, CuttingTypeZ, HSCodeZ, Adminz;
    float ProductPriceZ = 0.0f;
    int ProductIDZ;
    DefaultTableModel model;
    
    public Product(String Adminz) {
	initComponents();
	this.Adminz = Adminz;
	setProductDetailsTable();
	
    }

    public Product() {
	initComponents();
	setProductDetailsTable();
    }

    //Clear Table Before Showing New Data
    public void clearTableData() {

	DefaultTableModel model = (DefaultTableModel) productTbl.getModel();
	model.setRowCount(0);

    }

    //validate Inputs
    public boolean validateF() {

	boolean validZ = false;

	if (txt_ProductName.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Product Name");
	} else if (txt_ProductPrice.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Product Price Correctly!");
	}
	else if (txt_ProductHSCode.getText().equals("")) {
	    JOptionPane.showMessageDialog(this, "Please Enter Product HS Code");
	} else {
	    validZ = true;
	}
	return validZ;
    }

    //PuchaseOrder Product ID to ( Delete )
    public boolean getPurchaseProductF() {

	ProductIDZ = Integer.parseInt(txt_ProductID.getText());
	boolean found = false;

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    String sql = "select ProductID from PurchaseOrder where ProductID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);
	    pst.setInt(1, ProductIDZ);
	    ResultSet rs = pst.executeQuery();

	    if (rs.next()) {
		String sql2 = "Delete from PurchaseOrder where ProductID = ?";
		PreparedStatement pst2 = con.prepareStatement(sql2);

		pst2.setInt(1, ProductIDZ);
		pst2.executeUpdate();
		found = true;
	    } else {
		found = false;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return found;

    }

    //set product Table Details
    public void setProductDetailsTable() {

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery("SELECT * FROM Product");

	    while (rs.next()) {
		int ProductID = rs.getInt("ProductID");
		float ProductPrice = rs.getFloat("ProductPrice");
		String ProductName = rs.getString("ProductName");
		String Productsize = rs.getString("ProductSize");
		String Thickness = rs.getString("Thickness");
		String Tanning = rs.getString("Tanning");
		String Grade = rs.getString("Grade");
		String Color = rs.getString("Color");
		String CuttingType = rs.getString("CuttingType");
		String HSCode = rs.getString("ProductHSCode");

		Object[] obj = {ProductID, ProductName, Productsize, Thickness, Tanning, Grade, Color, CuttingType, ProductPrice, HSCode};

		model = (DefaultTableModel) productTbl.getModel();
		model.addRow(obj);

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    //Add Product In Database
    public boolean AddProduct() {
	boolean IsAdded = false;
	ProductNameZ = txt_ProductName.getText();
	ProductSizeZ = txt_ProductSize.getText();
	ThicknessZ = txt_Thickness.getText();
	TanningZ = txt_Tanning.getText();
	GradeZ = txt_Grade.getText();
	ColorZ = txt_Color.getText();
	CuttingTypeZ = txt_CuttingType.getText();
	if (!txt_ProductPrice.getText().equals("")) {
	    ProductPriceZ = Float.parseFloat(txt_ProductPrice.getText());
	}
	HSCodeZ = txt_ProductHSCode.getText();

	if (validateF() == true) {
	    try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		String sql = "insert into Product(ProductName, ProductSize, Thickness, Tanning, Grade, Color, CuttingType, ProductPrice, ProductHSCode) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, ProductNameZ);
		pst.setString(2, ProductSizeZ);
		pst.setString(3, ThicknessZ);
		pst.setString(4, TanningZ);
		pst.setString(5, GradeZ);
		pst.setString(6, ColorZ);
		pst.setString(7, CuttingTypeZ);
		pst.setFloat(8, ProductPriceZ);
		pst.setString(9, HSCodeZ);

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

    //Update Product Table Details   
    public boolean UpdateProduct() {

	boolean IsUpdated = false;

	ProductIDZ = Integer.parseInt(txt_ProductID.getText());
	ProductNameZ = txt_ProductName.getText();
	ProductSizeZ = txt_ProductSize.getText();
	ThicknessZ = txt_Thickness.getText();
	TanningZ = txt_Tanning.getText();
	GradeZ = txt_Grade.getText();
	ColorZ = txt_Color.getText();
	CuttingTypeZ = txt_CuttingType.getText();
	ProductPriceZ = Float.parseFloat(txt_ProductPrice.getText());
	HSCodeZ = txt_ProductHSCode.getText();
	if (validateF() == true) {
	    try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

		String sql = "Update Product set ProductName = ?, ProductSize = ?, Thickness = ?, Tanning = ?, Grade = ?, Color = ?, CuttingType = ?,ProductPrice = ?, ProductHSCode = ? where ProductID = ?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setString(1, ProductNameZ);
		pst.setString(2, ProductSizeZ);
		pst.setString(3, ThicknessZ);
		pst.setString(4, TanningZ);
		pst.setString(5, GradeZ);
		pst.setString(6, ColorZ);
		pst.setString(7, CuttingTypeZ);
		pst.setFloat(8, ProductPriceZ);
		pst.setString(9, HSCodeZ);
		pst.setInt(10, ProductIDZ);

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

    //Delete Product Details
    public boolean DeteleProduct() {

	boolean IsDeleted = false;

	ProductIDZ = Integer.parseInt(txt_ProductID.getText());

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    String sql = "Delete from Product where ProductID = ?";
	    PreparedStatement pst = con.prepareStatement(sql);

	    pst.setInt(1, ProductIDZ);

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
	txt_ProductID.setText("");
	txt_ProductName.setText("");
	txt_ProductSize.setText("");
	txt_Thickness.setText("");
	txt_Tanning.setText("");
	txt_Grade.setText("");
	txt_Color.setText("");
	txt_CuttingType.setText("");
	txt_ProductPrice.setText("");
	txt_ProductHSCode.setText("");

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
        productTbl = new rojeru_san.complementos.RSTableMetro();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_ProductID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_ProductName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_ProductSize = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_Thickness = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_Tanning = new javax.swing.JTextField();
        AddBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        DeleteBtn = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();
        ClearBtn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txt_ProductHSCode = new javax.swing.JTextField();
        txt_ProductPrice = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_CuttingType = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_Color = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_Grade = new javax.swing.JTextField();

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

        productTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProductID", "ProductName", "ProductSize", "Thickness", "Tanning", "Grade", "Color", "CuttingType", "ProductPrice", "ProductHSCode"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productTbl.setColorBackgoundHead(new java.awt.Color(31, 110, 140));
        productTbl.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        productTbl.setColorFilasForeground2(new java.awt.Color(12, 12, 12));
        productTbl.setColorSelBackgound(new java.awt.Color(255, 133, 81));
        productTbl.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        productTbl.setFuenteFilas(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        productTbl.setFuenteHead(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        productTbl.setIntercellSpacing(new java.awt.Dimension(0, 0));
        productTbl.setRowHeight(28);
        productTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(productTbl);
        if (productTbl.getColumnModel().getColumnCount() > 0) {
            productTbl.getColumnModel().getColumn(5).setMaxWidth(400);
            productTbl.getColumnModel().getColumn(6).setMaxWidth(400);
        }

        jPanel3.setBackground(new java.awt.Color(31, 110, 140));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Products");

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

        txt_ProductID.setEditable(false);
        txt_ProductID.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_ProductID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ProductIDActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Product ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Product Name");

        txt_ProductName.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_ProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ProductNameActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Product Size");

        txt_ProductSize.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_ProductSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ProductSizeActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Thickness");

        txt_Thickness.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_Thickness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ThicknessActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Tanning");

        txt_Tanning.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_Tanning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TanningActionPerformed(evt);
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
        jLabel11.setText("Product HS Code");

        txt_ProductHSCode.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_ProductHSCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ProductHSCodeActionPerformed(evt);
            }
        });

        txt_ProductPrice.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_ProductPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ProductPriceActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Product Price");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Cutting Type");

        txt_CuttingType.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_CuttingType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CuttingTypeActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Color");

        txt_Color.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_Color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ColorActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Grade");

        txt_Grade.setFont(new java.awt.Font("Nirmala UI", 1, 20)); // NOI18N
        txt_Grade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_GradeActionPerformed(evt);
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
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_Tanning, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_Thickness, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_ProductSize, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(12, 12, 12)))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_ProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_Grade, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_ProductPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_CuttingType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txt_ProductHSCode, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_Color, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(195, 195, 195)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UpdateBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DeleteBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClearBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(123, 123, 123))
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
                .addGap(84, 84, 84)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(ClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(34, 34, 34))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_ProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_ProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_ProductSize, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_Thickness, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_Tanning, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Grade, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Color, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_CuttingType, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_ProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_ProductHSCode, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
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
	HomeFrame ob = new HomeFrame("Admin");
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

    private void txt_ProductIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ProductIDActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_ProductIDActionPerformed

    private void txt_ProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ProductNameActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_ProductNameActionPerformed

    private void txt_ProductSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ProductSizeActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_ProductSizeActionPerformed

    private void txt_ThicknessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ThicknessActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_ThicknessActionPerformed

    private void txt_TanningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TanningActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_TanningActionPerformed

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed

	if (AddProduct() == true) {
	    JOptionPane.showMessageDialog(this, "Product Added Successfully!");
	    clearTableData();  
	    setProductDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Product Addition Failed!");

	}

    }//GEN-LAST:event_AddBtnActionPerformed

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
	getPurchaseProductF();
	if (DeteleProduct() == true) {
	    JOptionPane.showMessageDialog(this, "Product Deleted Successfully!");
	    clearTableData();
	    setProductDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Product Delete Failed!");
	}
    }//GEN-LAST:event_DeleteBtnActionPerformed

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed
	if (UpdateProduct() == true) {
	    JOptionPane.showMessageDialog(this, "Product Updated Successfully!");
	    clearTableData();
	    setProductDetailsTable();
	    ClearF();
	} else {
	    JOptionPane.showMessageDialog(this, "Product Update Failed!");
	}
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void ClearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearBtnActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_ClearBtnActionPerformed

    private void txt_ProductHSCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ProductHSCodeActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_ProductHSCodeActionPerformed

    private void txt_ProductPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ProductPriceActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_ProductPriceActionPerformed

    private void txt_CuttingTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CuttingTypeActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_CuttingTypeActionPerformed

    private void txt_ColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ColorActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_ColorActionPerformed

    private void txt_GradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_GradeActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_txt_GradeActionPerformed

    private void productTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productTblMouseClicked
	int rowNo = productTbl.getSelectedRow();
	TableModel model = productTbl.getModel();

	txt_ProductID.setText(model.getValueAt(rowNo, 0).toString());
	txt_ProductName.setText(model.getValueAt(rowNo, 1).toString());
	txt_ProductSize.setText(model.getValueAt(rowNo, 2).toString());
	txt_Thickness.setText(model.getValueAt(rowNo, 3).toString());
	txt_Tanning.setText(model.getValueAt(rowNo, 4).toString());
	txt_Grade.setText(model.getValueAt(rowNo, 5).toString());
	txt_Color.setText(model.getValueAt(rowNo, 6).toString());
	txt_CuttingType.setText(model.getValueAt(rowNo, 7).toString());
	txt_ProductPrice.setText(model.getValueAt(rowNo, 8).toString());
	txt_ProductHSCode.setText(model.getValueAt(rowNo, 9).toString());
    }//GEN-LAST:event_productTblMouseClicked

    private void ClearBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearBtnMouseClicked
	ClearF();

    }//GEN-LAST:event_ClearBtnMouseClicked

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
	    java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>

	/* Create and display the form */
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new Product().setVisible(true);
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton ClearBtn;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JLabel backButton;
    private javax.swing.JLabel homeCloseButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private rojeru_san.complementos.RSTableMetro productTbl;
    private javax.swing.JTextField txt_Color;
    private javax.swing.JTextField txt_CuttingType;
    private javax.swing.JTextField txt_Grade;
    private javax.swing.JTextField txt_ProductHSCode;
    private javax.swing.JTextField txt_ProductID;
    private javax.swing.JTextField txt_ProductName;
    private javax.swing.JTextField txt_ProductPrice;
    private javax.swing.JTextField txt_ProductSize;
    private javax.swing.JTextField txt_Tanning;
    private javax.swing.JTextField txt_Thickness;
    // End of variables declaration//GEN-END:variables
}
