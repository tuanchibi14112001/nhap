package ngan_hang;

public class Main {
	public static void main(String[] args) {
		Tai_Khoan tk1 = new Tai_Khoan();
		boolean ktraTk = false;
		tk1.setDulieu("Minh Nam", 100000.0f);
		tk1.inSoDu();
		//nap tien
		tk1.napTien(20000.0f);
		System.out.println("Tai khoan sau khi nap");
		tk1.inSoDu();
		//rut tien
		ktraTk = tk1.rutTien(30000.0f);
		if(!ktraTk) {
		 System.out.println("So du tai khoan khong du");
		}
		else {
			System.out.println("Tai khoan sau khi rut la");
			tk1.inSoDu();
		}
			
	}
}
                                                                                                       