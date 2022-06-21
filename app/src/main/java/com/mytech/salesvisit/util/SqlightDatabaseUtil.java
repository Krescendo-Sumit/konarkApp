package com.mytech.salesvisit.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


public class SqlightDatabaseUtil extends SQLiteOpenHelper {

	final static String DBName="konark";
	final static int version=7;
	long count=0;

	public SqlightDatabaseUtil(Context context) {
		super(context, DBName, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String tbl_order_details="CREATE TABLE order_details(id INTEGER PRIMARY KEY AUTOINCREMENT,productid text,qty text,uom text,rate text,total text,specification text,productname text,uomname text,itemid int)";
		db.execSQL(tbl_order_details);

		String tbl_order_terms=" CREATE TABLE tbl_order_terms(TermId intenger,\n" +
				"      SrNo integer,\n" +
				"      OrderId integer,\n" +
				"      ParticularId integer,\n" +
				"      Condition text,\n" +
				"      IsRemoved text,name text) ";

		db.execSQL(tbl_order_terms);

		String tbl_order_details_local="CREATE TABLE tbl_order_local(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"Details TEXT,\n" +
				"Status integer, customername text,createddate text) ";

		db.execSQL(tbl_order_details_local);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
        droptable(db,"order_details");
        droptable(db,"tbl_order_terms");
		droptable(db,"tbl_order_local");
        onCreate(db);
	}

	private void droptable(SQLiteDatabase db, String s) {
		try{

			String tbl_order_terms="drop table "+s;
			db.execSQL(tbl_order_terms);
			Log.i("Table Drop :",s);

		}catch (Exception e)
		{
			Log.i("Error is :",e.getMessage());
		}
	}

	public boolean addProduct(String productid, String rate, String qty, String uom, String specification,String total,String productname,String uomname,int itemid)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "insert into order_details(productid,qty,uom,rate,total,specification,productname,uomname,itemid) values" +
					"('"+productid+"','"+qty+"','"+uom+"','"+rate+"','"+total+"','"+specification+"','"+productname+"','"+uomname+"',"+itemid+")";
			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {
			Log.i("Error is Product Added ",""+e.getMessage());
			return false;
		} finally {
			mydb.close();
		}

	}


	public boolean addOrderLocal(String id, String details,int status,String cname)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "insert into tbl_order_local(Details,Status,customername,createddate) values" +
					"('"+details+"',"+status+",'"+cname+"',datetime('now'))";
			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {
			Log.i("Error is  Added ","Order Details : "+e.getMessage());
			return false;
		} finally {
			mydb.close();
		}

	}
	public boolean updateLocalOrerStatus(String id,int status)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "update  tbl_order_local set Status="+status+" where id="+id;
			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {
			Log.i("Error is  Added ","Order Details : "+e.getMessage());
			return false;
		} finally {
			mydb.close();
		}

	}




	public boolean clearProductList()
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "delete from order_details";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {
			Log.i("Error is Clear List",""+e.getMessage());
			return false;
		} finally {
			mydb.close();
		}

	}

	public boolean clearProductList(int id)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "delete from order_details where productid="+id+"";
			//String q = "delete from tbl_customersatyam";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {
			Log.i("Error is Clear List",""+e.getMessage());
			return false;
		} finally {
			mydb.close();
		}

	}


	public boolean clearTermsList()
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "delete from tbl_order_terms";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {
			Log.i("Error is Clear List",""+e.getMessage());
			return false;
		} finally {
			mydb.close();
		}

	}
public boolean clearTermsList(int id)
	{

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "delete from tbl_order_terms where ParticularId='"+id+"'";

			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {
			Log.i("Error is Clear List",""+e.getMessage());
			return false;
		} finally {
			mydb.close();
		}

	}


	public Vector getProductDetailsById(String id) {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v;
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM tbl_cstatus where id="+id;

			Cursor c = mydb.rawQuery(q, null);
			v=new Vector();
			if (c.moveToNext()) {
				//v[i]=new Vector();

				v.addElement(c.getInt(0));
				v.addElement(c.getString(1));
				v.addElement(c.getString(2));
				v.addElement(c.getString(3));
				v.addElement(c.getString(4));
				v.addElement(c.getString(5));
				v.addElement(c.getString(6));


				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}
	public Vector[] getAllProducts() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v[];
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM order_details";

			Cursor c = mydb.rawQuery(q, null);
			v=new Vector[c.getCount()];
			while (c.moveToNext()) {
				v[i]=new Vector();

				v[i].addElement(c.getInt(0)); //id
				v[i].addElement(c.getString(1));//productid
				v[i].addElement(c.getInt(2));
				v[i].addElement(c.getString(3));
				v[i].addElement(c.getString(4));
				v[i].addElement(c.getString(5));
				v[i].addElement(c.getString(6));
				v[i].addElement(c.getString(7));
				v[i].addElement(c.getString(8));
				v[i].addElement(c.getString(9));

				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}

	public boolean addTerms(int termId, int srNo, int orderId, int particularId, String condition, boolean isRemoved,String name) {

		SQLiteDatabase mydb = null;
		try {
			mydb = this.getReadableDatabase();
			String q = "insert into tbl_order_terms(TermId,SrNo,OrderId,ParticularId,Condition,IsRemoved,name) values" +
					"("+termId+","+srNo+","+orderId+","+particularId+",'"+condition+"','"+isRemoved+"','"+name+"')";
			Log.i("Query is -------> ",""+q);
			mydb.execSQL(q);
			return true;
		} catch (Exception e) {
			Log.i("Error is Product Added ",""+e.getMessage());
			return false;
		} finally {
			mydb.close();
		}


	}
	public Vector[] getAllTerms() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v[];
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM tbl_order_terms";

			Cursor c = mydb.rawQuery(q, null);
			v=new Vector[c.getCount()];
			while (c.moveToNext()) {
				v[i]=new Vector();

				v[i].addElement(c.getInt(0)); //id
				v[i].addElement(c.getInt(1));//productid
				v[i].addElement(c.getInt(2));
				v[i].addElement(c.getInt(3));
				v[i].addElement(c.getString(4));
				v[i].addElement(c.getString(5));
				v[i].addElement(c.getString(6));

				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}public Vector[] getAllTermsForAdd() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v[];
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM tbl_order_terms";

			Cursor c = mydb.rawQuery(q, null);
			v=new Vector[c.getCount()];
			while (c.moveToNext()) {
				v[i]=new Vector();

				v[i].addElement(c.getInt(0)); //id
				v[i].addElement(c.getInt(1));//productid
				v[i].addElement(c.getInt(2));
				v[i].addElement(c.getInt(3));
				v[i].addElement(c.getString(4));
				v[i].addElement(c.getString(5));


				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}
	public Vector[] getAllOfflineOrders() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v[];
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM tbl_order_local where status=0";

			Cursor c = mydb.rawQuery(q, null);
			v=new Vector[c.getCount()];
			while (c.moveToNext()) {
				v[i]=new Vector();

				v[i].addElement(c.getInt(0)); //id
				v[i].addElement(c.getString(1));//productid
				v[i].addElement(c.getInt(2));
				v[i].addElement(c.getString(3));
				v[i].addElement(c.getString(4));
				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}


	public Vector getAllOfflineOrdersById(int id) {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v=new Vector();;
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  * FROM tbl_order_local where id="+id+" and status=0";

			Cursor c = mydb.rawQuery(q, null);

			if (c.moveToNext()) {


				v.addElement(c.getInt(0)); //id
				v.addElement(c.getString(1).replace("\\\"",""));//productid
				v.addElement(c.getInt(2));
				v.addElement(c.getString(3));
				v.addElement(c.getString(4));
				i++;
			}

			return v;
		} catch (Exception e) {

			return null;
		} finally {
			mydb.close();
		}
	}

	public int getMaxOfflineOrderID() {
		SQLiteDatabase mydb = null;
		String k = "";
		Vector v=new Vector();;
		int i = 0;
		try {
			mydb = this.getReadableDatabase();
			String q = "SELECT  ifnull(max(id),0)+1 FROM tbl_order_local";

			Cursor c = mydb.rawQuery(q, null);

			if (c.moveToNext()) {
				i=c.getInt(0); //id
			}

			return i;
		} catch (Exception e) {

			return 0;
		} finally {
			mydb.close();
		}
	}



}
