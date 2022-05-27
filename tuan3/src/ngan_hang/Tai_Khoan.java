package ngan_hang;

public class Tai_Khoan {
	  private String ten;
	  private float so_du;
	  
	  public void setDulieu(String ten, float so_du) {
		  this.ten =  ten;
		  this.so_du = so_du;
		  
	  }
	  
	  public void napTien(float tien) {
		 this.so_du += tien;
	 }
	 
	 public boolean rutTien(float tien) {
		 if(so_du<tien) {
			 return false;
		 }
		 else 
			 so_du -= tien;
		 return true;
	 }
	 
	  public void inSoDu() {
		 System.out.println("Ten tai khoan: " + ten );
		 System.out.println("So tien nap cua ban la: " + so_du + " dong" );
		 
	 }
	
}
 	
