package vttp_paf.day24;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp_paf.day24.models.Item;
import vttp_paf.day24.models.PurchaseOrder;
import vttp_paf.day24.services.OrderException;
import vttp_paf.day24.services.OrderService;

@SpringBootApplication
public class Day24Application implements CommandLineRunner {

	public static String[] DESC = {"apple", "pear", "grapes"};
	public static Integer[] QTY = {3, 5, 10};


	@Autowired
	OrderService orderSvc;

	public static void main(String[] args) {
		SpringApplication.run(Day24Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		PurchaseOrder po = new PurchaseOrder();
		po.setName("fred");
		po.setOrderDate(new Date());

		for (int i = 0; i < DESC.length; i++) {
			po.addItems(new Item(DESC[i], QTY[i]));
		}

		// Create the purchase order
		try {
			orderSvc.insertOrder(po);
		} catch (OrderException ex) {
			System.out.println(">>>>> " + ex.getMessage());
		}
	}
}
