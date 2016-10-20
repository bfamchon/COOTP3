import java.sql.SQLException;
import java.util.List;

import com.cf.domain.Bureau;
import com.cf.persistence.BureauMapper;
import com.cf.persistence.InterfaceMapper;
import com.cf.persistence.gestionnaireconnexion.DBConfig;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InterfaceMapper<Bureau> bMapper = new BureauMapper();
		try {
			List<Bureau> listBureau = bMapper.find();
			Bureau b = bMapper.findById(0);
			
			System.out.println("fin");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConfig.getInstance().fermerConnexion();
	}

}
